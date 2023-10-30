package com.galaxy.novelit.workspace.service;

public interface WorkspaceService {
    void createWorkspace(String workSpaceUUID, String user_uuid, String title);
    void modifyWorkspaceName(String workSpaceUUID, String title);

    void deleteWorkspace(String workSpaceUUID);
}
