package com.n26.codingchallange.controller.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class RequestTransaction {
    @Valid
    @NotNull
    private String amount;
    @Valid
    @NotNull
    private String timestamp;
}
