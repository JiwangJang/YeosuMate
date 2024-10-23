package com.yeosu_mate.backend.model.process;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProcessResult {
    private boolean isSuccess;
    private String msg;
}
