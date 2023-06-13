package com.samsung.project.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorHttpResponse {
    private HttpStatus httpStatus;
    private int statusCode;
    private String message;
    private LocalDateTime dateTime;
}
