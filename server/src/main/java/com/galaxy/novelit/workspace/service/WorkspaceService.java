package com.galaxy.novelit.workspace.service;

import java.util.List;

public interface WorkspaceService {
    void createWorkspace(String workSpaceUUID, String user_uuid, String title);
    void modifyWorkspaceName(String workSpaceUUID, String title);

    void deleteWorkspace(String workSpaceUUID);

    List<?> getWorkspaces(String testUUID);
}
