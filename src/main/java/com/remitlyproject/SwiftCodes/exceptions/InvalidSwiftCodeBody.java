package com.remitlyproject.SwiftCodes.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class InvalidSwiftCodeBody {
    private final String message;
    private final HttpStatus status;
    private final ZonedDateTime timestamp;



}
