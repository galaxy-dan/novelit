package com.galaxy.novelit.share.service;

import com.galaxy.novelit.share.dto.request.EditableReqDTO;
import com.galaxy.novelit.share.dto.response.ShareTokenResDTO;
import com.galaxy.novelit.share.dto.response.ShareTokenValidationResDTO;

public interface ShareService {

    ShareTokenResDTO generateToken(String directoryUUID, String userUUID);
    void updateEditable(EditableReqDTO dto, String userUUID);
    ShareTokenValidationResDTO validateToken(String token);
}
