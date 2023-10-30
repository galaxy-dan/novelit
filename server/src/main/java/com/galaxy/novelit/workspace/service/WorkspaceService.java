package com.galaxy.novelit.workspace.service;

import com.galaxy.novelit.workspace.dto.response.WorkSpaceInfoResDTO;
import java.util.List;

public interface WorkspaceService {
    void createWorkspace(String workSpaceUUID, String user_uuid, String title);
    void modifyWorkspaceName(String workSpaceUUID, String title);

    void deleteWorkspace(String workSpaceUUID);

    WorkSpaceInfoResDTO getWorkspaceInfo(String workSpaceUUID);
    List<?> getWorkspaces(String testUUID);

}
