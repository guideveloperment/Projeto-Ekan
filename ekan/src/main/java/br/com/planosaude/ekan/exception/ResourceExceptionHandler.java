package br.com.planosaude.ekan.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    /*400*/ public ResponseEntity<StandardErrorObject> dataIntegrityViolationException(DataIntegrityViolationException ex,
                                                                               HttpServletRequest request) {
        var error = new StandardErrorObject(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Data violations",
                ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    /*400*/ public ResponseEntity<ValidationErrorObject> validationErrors(MethodArgumentNotValidException ex,
                                                                  HttpServletRequest request) {
        var errors = new ValidationErrorObject(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro do bean validation",
                "Erro no campo de validação!",
                request.getRequestURI());

        ex.getBindingResult().getFieldErrors().forEach(fe -> errors.setErrors(fe.getField(), fe.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    /*400*/ public ResponseEntity<StandardErrorObject> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                                HttpServletRequest request) {
        var error = new StandardErrorObject(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad request",
                "Tipo do parâmetro inválido: " + ex.getName(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(TipoDocumentoInvalidoException.class)
    /*400*/ public ResponseEntity<StandardErrorObject> tipoDocumentoInvalidoException(TipoDocumentoInvalidoException ex,
                                                                                      HttpServletRequest request) {
        var error = new StandardErrorObject(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Tipo de documento inválido",
                ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DocumentoAssociadoException.class)
    /*400*/ public ResponseEntity<StandardErrorObject> documentoAssociadoException(DocumentoAssociadoException ex,
                                                                                   HttpServletRequest request) {
        var error = new StandardErrorObject(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Documento associado",
                ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(FormularioNaoSuportadoException.class)
    /*400*/ public ResponseEntity<StandardErrorObject> formularioNaoSuportadoException(FormularioNaoSuportadoException ex,
                                                                                       HttpServletRequest request) {
        var error = new StandardErrorObject(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Formulário não suportado",
                ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    /*404*/ public ResponseEntity<StandardErrorObject> objectNotFoundException(ObjectNotFoundException ex,
                                                                               HttpServletRequest request) {
        var error = new StandardErrorObject(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Objeto não encontrado",
                ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DocumentoDuplicadoException.class)
    /*409*/ public ResponseEntity<StandardErrorObject> documentoDuplicadoException(DocumentoDuplicadoException ex,
                                                                                   HttpServletRequest request) {
        var error = new StandardErrorObject(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Documento duplicado",
                ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(Exception.class)
    /*500*/ public ResponseEntity<StandardErrorObject> handleGenericException(Exception ex, HttpServletRequest request) {
        var error = new StandardErrorObject(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal server error",
                ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
