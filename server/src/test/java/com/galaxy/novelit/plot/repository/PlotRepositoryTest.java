package com.galaxy.novelit.plot.repository;


import com.galaxy.novelit.common.exception.custom.CustomException;
import com.galaxy.novelit.common.exception.custom.ErrorCode;
import com.galaxy.novelit.config.QuerydslConfig;
import com.galaxy.novelit.config.redis.RedisConfig;
import com.galaxy.novelit.plot.entity.Plot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
class PlotRepositoryTest {

    @Autowired
    private PlotRepository plotRepository;

    @Test
    @DisplayName("키워드 검색")
    public void findByKeyword() throws Exception{
        String workspaceUUID = "workspace_uuid1";
        String keyword = "hasungkim";

        List<Plot> plots = plotRepository.findByKeyword(workspaceUUID, keyword)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_PLOT));

        System.out.println();
        plots.forEach(plot -> System.out.println(plot.getPlotId() + " " + plot.getPlotTitle()));
    }


}
