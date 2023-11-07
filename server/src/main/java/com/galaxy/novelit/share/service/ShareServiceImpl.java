package com.galaxy.novelit.share.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.galaxy.novelit.auth.util.JwtUtils;
import com.galaxy.novelit.common.exception.NoSuchDirectoryException;
import com.galaxy.novelit.directory.domain.Directory;
import com.galaxy.novelit.directory.repository.DirectoryRepository;
import com.galaxy.novelit.share.dto.response.ShareTokenResDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShareServiceImpl implements ShareService{

    private final DirectoryRepository directoryRepository;
    @Value("{jwt.share-token-expire}")
    private long shareTokenExpiration;
    private final JwtUtils jwtUtils;

    @Override
    public ShareTokenResDTO generateToken(String directoryUUID) {
        Directory directory = directoryRepository.findDirectoryByUuid(directoryUUID)
            .orElseThrow(NoSuchDirectoryException::new);

        String token = jwtUtils.generateShareToken(directoryUUID, shareTokenExpiration);

        return new ShareTokenResDTO(token);
    }
}
