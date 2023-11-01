package com.galaxy.novelit.plot.repository;

import com.galaxy.novelit.plot.entity.PlotEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlotRepository extends JpaRepository <PlotEntity, Long> {

    Optional<List<PlotEntity>> findAllByWorkspaceUuid(String workspaceUuid);
    Optional<PlotEntity> findPlotEntityByPlotUuid(String plotUuid);
    Optional<PlotEntity> deletePlotEntitiesByPlotUuid(String plotUuid);
}
