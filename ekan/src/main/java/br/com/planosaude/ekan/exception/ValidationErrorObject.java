package br.com.planosaude.ekan.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationErrorObject extends StandardErrorObject {

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationErrorObject(LocalDateTime timestamp, Integer status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
    }

    public void setErrors(String fieldName, String message) {
        this.errors.add(new FieldMessage(fieldName, message));
    }
}
