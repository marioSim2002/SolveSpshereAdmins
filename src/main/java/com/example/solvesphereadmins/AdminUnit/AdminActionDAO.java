package com.example.solvesphereadmins.AdminUnit;

import java.util.List;

public interface AdminActionDAO {
    void logAdminAction(int adminId, String actionType, Integer targetId, String targetType, String description);
    List<AdminAction> getAllAdminActions();
    List<AdminAction> getActionsByAdminId(int adminId);
    void deleteAdminAction(int actionId);
}
