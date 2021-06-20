package com.n26.codingchallange.controller;

import com.n26.codingchallange.exception.InvalidJsonException;
import com.n26.codingchallange.exception.TransactionExpiredException;
import com.n26.codingchallange.service.TransactionService;
import com.n26.codingchallange.util.TransactionTestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@WebMvcTest(TransactionController.class)
@RunWith(SpringRunner.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void deleteTransaction_ReturnOk () throws Exception {
        MvcResult result  = mockMvc.perform(MockMvcRequestBuilders.delete("/transactions").accept(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void addTransaction_WhenInvalidJson_ReturnBadRequest () throws Exception {
        doThrow(InvalidJsonException.class).when(transactionService).executeTransaction(Mockito.anyString(),Mockito.anyString());

        MvcResult result  = mockMvc.perform(MockMvcRequestBuilders.post("/transactions").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void addTransaction_WhenTransactionIsOld_ReturnNoContent () throws Exception {
        doThrow(TransactionExpiredException.class).when(transactionService).executeTransaction(Mockito.anyString(),Mockito.anyString());

        MvcResult result  = mockMvc.perform(MockMvcRequestBuilders.post("/transactions").contentType(MediaType.APPLICATION_JSON).content(TransactionTestUtil.getTransactionRequest()).accept(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void addTransaction_ReturnCreated () throws Exception {

        doNothing().when(transactionService).executeTransaction(Mockito.anyString(),Mockito.anyString());

        MvcResult result  = mockMvc.perform(MockMvcRequestBuilders.post("/transactions").contentType(MediaType.APPLICATION_JSON).content(TransactionTestUtil.getTransactionRequest()).accept(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

}
