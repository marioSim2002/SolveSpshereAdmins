package com.example.solvesphereadmins;

import com.example.solvesphereadmins.AdminUnit.AdminAction;
import com.example.solvesphereadmins.AdminUnit.AdminActionDAO;
import com.example.solvesphereadmins.AdminUnit.AdminActionDAOImpl;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class FileExporter {
    public static void exportPdfFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file == null) return; //if user cancels, exit the method

        try {
            // init doc
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            // style font
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Paragraph title = new Paragraph("Admin Action Logs For SolveSphere App", titleFont);
            title.setAlignment(title.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            //create table
            PdfPTable table = new PdfPTable(6); // 6 columns
            table.setWidthPercentage(100);

            // table headers
            String[] headers = {"Admin ID", "Action Type", "Target ID", "Target Type", "Description", "Timestamp"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }
            AdminActionDAO adminActionDAO = new AdminActionDAOImpl();
            List<AdminAction> actions = adminActionDAO.getAllAdminActions();
            for (AdminAction action : actions) {
                table.addCell(String.valueOf(action.getAdminId()));
                table.addCell(action.getActionType());
                table.addCell(String.valueOf(action.getTargetId()));
                table.addCell(action.getTargetType());
                table.addCell(action.getDescription());
                table.addCell(action.getTimestamp().toString());
            }
            //add the table to the doc and clode
            document.add(table);
            document.close();

            AlertsUnit.successExAlert();

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            AlertsUnit.showErrorAlert("Error exporting\n"+e);
        }
    }
}
