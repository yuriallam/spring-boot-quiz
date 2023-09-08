package org.yuri.sales.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yuri.sales.exceptions.NotFoundException;
import org.yuri.sales.models.ErrorResponse;

@RestControllerAdvice(annotations = RestController.class)
public class ExceptionController {
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorResponse> notFoundExceptionHandler(NotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse("An internal error has occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
