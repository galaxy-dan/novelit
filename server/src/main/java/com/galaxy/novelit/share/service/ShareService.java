package com.galaxy.novelit.share.service;

import com.galaxy.novelit.share.dto.request.EditableReqDTO;
import com.galaxy.novelit.share.dto.response.ShareTokenResDTO;

public interface ShareService {

    ShareTokenResDTO generateToken(String directoryUUID, String userUUID);
    void updateEditable(EditableReqDTO dto, String userUUID);
}
