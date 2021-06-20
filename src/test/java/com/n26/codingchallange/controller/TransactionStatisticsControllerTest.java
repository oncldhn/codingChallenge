package com.n26.codingchallange.controller;

import com.jayway.jsonpath.JsonPath;
import com.n26.codingchallange.service.TransactionStatisticsService;
import com.n26.codingchallange.util.TransactionTestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;


@WebMvcTest(TransactionStatisticsController.class)
@RunWith(SpringRunner.class)
public class TransactionStatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionStatisticsService statisticsService;


    @Test
    public void getStatistics_ReturnResult () throws Exception {
        when(statisticsService.getStatistics()).thenReturn(TransactionTestUtil.getTransactionStatistics());

        MvcResult result  = mockMvc.perform(MockMvcRequestBuilders.get("/statistics").accept(MediaType.APPLICATION_JSON)).andReturn();

        String sum = JsonPath
                .parse(result.getResponse().getContentAsString())
                .read("$.sum"  );

        assertThat(sum).isEqualTo("10");
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

    }
}
