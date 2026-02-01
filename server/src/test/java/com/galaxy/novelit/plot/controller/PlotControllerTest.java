package com.galaxy.novelit.plot.controller;

import com.galaxy.novelit.auth.util.JwtUtils;
import com.galaxy.novelit.plot.response.PlotInfo;
import com.galaxy.novelit.plot.response.PlotListResponse;
import com.galaxy.novelit.plot.service.PlotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlotController.class)
public class PlotControllerTest {

    @MockBean
    private PlotService plotService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtils jwtUtils;

    @BeforeEach
    void setup(){

    }

    @Test
    @WithMockUser(username = "류현진")
    @DisplayName("전체 플롯 조회 : keyword가 존재")
    void getPlotListTest() throws Exception{

        // given
        String workspaceUUID = "asdf-asdf";
        String keyword = "김하성";

        List<PlotInfo> plotInfoList = new ArrayList<>();

        PlotInfo plotInfo = new PlotInfo("plot-uuid", "HARRY POTTER", "HARRY POTTER 1 EPISODE");

        plotInfoList.add(plotInfo);

        PlotListResponse plotListResponse = new PlotListResponse(plotInfoList);

        given(plotService.getPlotListByKeyword(workspaceUUID,keyword)).willReturn(plotListResponse);

        //when
        ResultActions perform = mockMvc.perform(get("/plot")
                        .param("workspaceUuid", "asdf-asdf")
                        .param("keyword", "김하성"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.plotInfoList[0].plotUuid").value(plotInfo.plotUuid()))
                .andExpect(jsonPath("$.plotInfoList[0].plotTitle").value(plotInfo.plotTitle()))
                .andExpect(jsonPath("$.plotInfoList[0].story").value(plotInfo.story()));


        //then
        verify(plotService, times(1)).getPlotListByKeyword(workspaceUUID,keyword);
        perform.andDo(print());
        //System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    @WithMockUser(username = "류현진")
    @DisplayName("전체 플롯 조회 : keyword가 없을때")
    void getPlotListWithoutKeywordTest() throws Exception{
        // given
        String workspaceUUID = "asdf-asdf";

        List<PlotInfo> plotInfoList = new ArrayList<>();

        PlotInfo plotInfo = new PlotInfo("plot-uuid", "HARRY POTTER", "HARRY POTTER 1 EPISODE");

        plotInfoList.add(plotInfo);

        PlotListResponse plotListResponse = new PlotListResponse(plotInfoList);

        given(plotService.getPlotList(workspaceUUID)).willReturn(plotListResponse);

        /*MvcResult result = mockMvc.perform(get("/plot")
                        .param("workspaceUuid", "asdf-asdf"))
                .andReturn();*/

        //when
        ResultActions perform = mockMvc.perform(get("/plot")
                        .param("workspaceUuid", "asdf-asdf"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.plotInfoList[0].plotUuid").value(plotInfo.plotUuid()))
                .andExpect(jsonPath("$.plotInfoList[0].plotTitle").value(plotInfo.plotTitle()))
                .andExpect(jsonPath("$.plotInfoList[0].story").value(plotInfo.story()));


        //then
        verify(plotService, times(1)).getPlotList(workspaceUUID);
        perform.andDo(print());
        //System.out.println(result.getResponse().getContentAsString());
    }
}
