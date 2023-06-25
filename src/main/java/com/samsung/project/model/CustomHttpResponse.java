package com.samsung.project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

@NoArgsConstructor
@Data
public class CustomHttpResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss", timezone = "America/New_York")
    private Date timeStamp;
    private int httpStatusCode; // 200, 201, 400, 500
    private HttpStatus httpStatus;
    private String reason;
    private String message;

    public CustomHttpResponse(int value, HttpStatus unauthorized, String toUpperCase, String accessDeniedMessage) {
        this.timeStamp=new Date();
        this.httpStatusCode=value;
        this.httpStatus=unauthorized;
        this.reason=toUpperCase;
        this.message=accessDeniedMessage;
    }
}
