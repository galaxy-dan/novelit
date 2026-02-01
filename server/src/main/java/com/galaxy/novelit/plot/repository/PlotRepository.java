package com.galaxy.novelit.plot.repository;

import com.galaxy.novelit.plot.entity.Plot;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlotRepository extends JpaRepository <Plot, Long> , PlotRepositoryCustom{

    Optional<List<Plot>> findAllByWorkspaceUuid(String workspaceUuid);
    Optional<Plot> findPlotByPlotUuid(String plotUuid);
    Optional<Plot> deletePlotByPlotUuid(String plotUuid);

    @Query(nativeQuery = true
            , value = "SELECT * FROM plot " +
                       "WHERE plot_title LIKE concat(:keyword, '%') " +
                         "AND workspace_uuid = :workspaceUUID " +
                       "UNION " +
                      "SELECT * FROM plot " +
                       "WHERE plot_title LIKE concat('%', :keyword, '%') " +
                         "AND workspace_uuid = :workspaceUUID")
    Optional<List<Plot>> findByKeyword(
              @Param("workspaceUUID") String workspaceUuid
            , @Param("keyword") String keyword);
}
