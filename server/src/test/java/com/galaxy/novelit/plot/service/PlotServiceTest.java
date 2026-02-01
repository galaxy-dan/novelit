package com.galaxy.novelit.plot.service;

import com.galaxy.novelit.plot.repository.PlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PlotServiceTest {

    @Mock
    private PlotRepository plotRepository;

    @InjectMocks
    private PlotService plotService;

    @BeforeEach
    void setup(){
        
    }
    
    @Test
    @WithMockUser(username = "류현진")
    @DisplayName("전체 플롯 조회: 키워드 포함 조회")
    public void getPlotListByKeyword() throws Exception{
        String workspaceUUID = "asdf-asdf";
        String keyword = "김하성";

        //given(plotRepository.findByKeyword(workspaceUUID, keyword)).will()


    }
}
