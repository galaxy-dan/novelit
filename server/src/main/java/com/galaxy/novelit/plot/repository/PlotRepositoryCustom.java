package com.galaxy.novelit.plot.repository;

import com.galaxy.novelit.plot.entity.PlotEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

public interface PlotRepositoryCustom {
    Optional<List<PlotEntity>> findByKeyword(String workspaceUuid, String keyword);
}
