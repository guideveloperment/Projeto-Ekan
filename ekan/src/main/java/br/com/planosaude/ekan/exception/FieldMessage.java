package br.com.planosaude.ekan.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FieldMessage {

    private String field;
    private String message;

}
