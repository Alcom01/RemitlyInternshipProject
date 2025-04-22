package com.remitlyproject.SwiftCodes.exceptions;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

// I was having problem to work with swagger ui then i ve checked by default spring docs were overriding generic responses
// so i set it to false to solve problem.
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {InvalidSwiftCodeException.class})
    public ResponseEntity<Object> handleInvalidSwiftCodeException(InvalidSwiftCodeException ex){
  // Creating a custom Payload
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
      InvalidSwiftCodeBody invalidSwiftCodeBody =  new InvalidSwiftCodeBody(
                ex.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );// return the response
        return new ResponseEntity<>(invalidSwiftCodeBody,badRequest);
    }
    @ExceptionHandler(value={InvalidCountryISO2Exception.class})
    public ResponseEntity<Object> handleCountryISO2Exception(InvalidCountryISO2Exception ex){
        // Creating a custom Payload
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
          InvalidCountryISO2Body invalidCountryISO2Body = new InvalidCountryISO2Body(
                ex.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );// return the response
        return new ResponseEntity<>(invalidCountryISO2Body,badRequest);
    }
    @ExceptionHandler(value={MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        //Map to store field errors and their corresponding messages.
        Map<String, String> errors = new HashMap<>();
        //Iterating through errors all validation errors,
        // putting field and error message to our map.
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        // returning a response with erros map and http status.
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


}

