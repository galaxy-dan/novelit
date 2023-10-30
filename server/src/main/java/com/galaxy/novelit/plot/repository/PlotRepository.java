package com.galaxy.novelit.plot.repository;

import com.galaxy.novelit.plot.entity.PlotEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlotRepository extends JpaRepository <PlotEntity, Long> {
    Optional<PlotEntity> findPlotEntityByPlotUuid(String plotUuid);
}
