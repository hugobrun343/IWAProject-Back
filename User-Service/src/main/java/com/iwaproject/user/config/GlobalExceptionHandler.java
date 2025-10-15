package com.iwaproject.user.config;



import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;



import java.time.LocalDateTime;

import java.util.HashMap;

import java.util.Map;



/**

 * Global exception handler for detailed error responses.

 * TEMPORARY - For debugging only.

 */

@Slf4j

@RestControllerAdvice

public class GlobalExceptionHandler {



    /**

     * Handle all exceptions with generic error response.

     *

     * @param ex the exception

     * @return generic error response

     */

    @ExceptionHandler(Exception.class)

    public ResponseEntity<Map<String, Object>> handleAllExceptions(

            final Exception ex) {



        log.error("Exception occurred: {}", ex.getMessage(), ex);



        Map<String, Object> error = new HashMap<>();

        error.put("error", "Internal Server Error");

        error.put("message", "An unexpected error occurred");

        error.put("timestamp", LocalDateTime.now().toString());



        return ResponseEntity

                .status(HttpStatus.INTERNAL_SERVER_ERROR)

                .body(error);

    }

}



