package br.com.planosaude.ekan.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class StandardErrorObject {

    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

}
