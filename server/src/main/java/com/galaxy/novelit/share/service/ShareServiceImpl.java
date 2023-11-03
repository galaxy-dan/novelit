package com.galaxy.novelit.share.service;

import com.galaxy.novelit.common.exception.NoSuchDirectoryException;
import com.galaxy.novelit.directory.domain.Directory;
import com.galaxy.novelit.directory.repository.DirectoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShareServiceImpl implements ShareService{

    private final DirectoryRepository directoryRepository;

    @Override
    public String getContent(String directoryUUID) {
        Directory directory = directoryRepository.findDirectoryByUuid(directoryUUID)
            .orElseThrow(() -> new NoSuchDirectoryException());

        return directory.getContent();
    }
}
