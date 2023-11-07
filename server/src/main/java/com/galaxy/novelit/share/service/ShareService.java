package com.galaxy.novelit.share.service;

import com.galaxy.novelit.share.dto.request.ShareFileReqDTO;
import com.galaxy.novelit.share.dto.response.ShareTokenResDTO;

public interface ShareService {

    ShareTokenResDTO generateToken(String directoryUUID);
    void checkToken(ShareFileReqDTO dto);
}
