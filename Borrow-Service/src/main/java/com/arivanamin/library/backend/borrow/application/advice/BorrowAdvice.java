package com.arivanamin.library.backend.borrow.application.advice;

import com.arivanamin.library.backend.borrow.domain.exception.*;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ProblemDetail.forStatusAndDetail;

@RestControllerAdvice
class BorrowAdvice {
    
    @ExceptionHandler (BookAlreadyBorrowedException.class)
    ProblemDetail handleBookNotFound (BookAlreadyBorrowedException exception) {
        ProblemDetail detail = forStatusAndDetail(NOT_FOUND, exception.getMessage());
        detail.setTitle("Bad Request, Book Already Borrowed");
        detail.setType(URI.create("https://docs.oracle.com/en/java/javase/21/docs/api/java" +
            ".base/java/lang/RuntimeException.html"));
        detail.setProperty("errorCategory", "Book Already Borrowed");
        detail.setProperty("timestamp", Instant.now());
        detail.setDetail(exception.getMessage());
        return detail;
    }
    
    @ExceptionHandler (BookAlreadyReturnedException.class)
    ProblemDetail handleBookNotFound (BookAlreadyReturnedException exception) {
        ProblemDetail detail = forStatusAndDetail(NOT_FOUND, exception.getMessage());
        detail.setTitle("Bad Request, Book Already Returned");
        detail.setType(URI.create("https://docs.oracle.com/en/java/javase/21/docs/api/java" +
            ".base/java/lang/RuntimeException.html"));
        detail.setProperty("errorCategory", "Book Already Returned");
        detail.setProperty("timestamp", Instant.now());
        detail.setDetail(exception.getMessage());
        return detail;
    }
    
    @ExceptionHandler (NoBorrowingRecordFoundException.class)
    ProblemDetail handleBookNotFound (NoBorrowingRecordFoundException exception) {
        ProblemDetail detail = forStatusAndDetail(NOT_FOUND, exception.getMessage());
        detail.setTitle("Bad Request, No Borrowing Record Found");
        detail.setType(URI.create("https://docs.oracle.com/en/java/javase/21/docs/api/java" +
            ".base/java/lang/RuntimeException.html"));
        detail.setProperty("errorCategory", "No Borrowing Record Found");
        detail.setProperty("timestamp", Instant.now());
        detail.setDetail(exception.getMessage());
        return detail;
    }
    
    @ExceptionHandler (MissingPathVariableException.class)
    ProblemDetail handleMissingPathVariable (HttpMessageNotReadableException exception) {
        ProblemDetail detail = forStatusAndDetail(BAD_REQUEST, exception.getMessage());
        detail.setTitle("Bad request, Missing required path variables");
        detail.setType(URI.create(
            "https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework" +
                "/web/bind/MissingPathVariableException.html"));
        detail.setProperty("errorCategory", "Missing Parameter");
        detail.setProperty("timestamp", Instant.now());
        detail.setDetail(exception.getMessage());
        return detail;
    }
    
    @ExceptionHandler (HttpMessageNotReadableException.class)
    ProblemDetail handleMissingRequestBody (HttpMessageNotReadableException exception) {
        ProblemDetail detail = forStatusAndDetail(BAD_REQUEST, exception.getMessage());
        detail.setTitle("Bad Request, Required request body is missing or unreadable");
        detail.setType(URI.create(
            "https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework" +
                "/http/converter/HttpMessageNotReadableException.html"));
        detail.setProperty("errorCategory", "Missing Parameter");
        detail.setProperty("timestamp", Instant.now());
        detail.setDetail(exception.getMessage());
        return detail;
    }
    
    @ExceptionHandler (MethodArgumentNotValidException.class)
    ProblemDetail handleMethodArgumentNotValid (MethodArgumentNotValidException exception) {
        ProblemDetail detail = forStatusAndDetail(BAD_REQUEST, exception.getMessage());
        detail.setTitle("Bad Request, Validation failed for one or more arguments");
        detail.setType(URI.create(
            "https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework" +
                "/messaging/handler/annotation/support/MethodArgumentNotValidException.html"));
        detail.setProperty("errorCategory", "Missing Parameter");
        detail.setProperty("timestamp", Instant.now());
        detail.setDetail(exception.getMessage());
        return detail;
    }
    
    @ExceptionHandler (MissingServletRequestParameterException.class)
    ProblemDetail handleMissingParameterException (
        MissingServletRequestParameterException exception) {
        ProblemDetail detail = forStatusAndDetail(BAD_REQUEST, exception.getMessage());
        detail.setTitle("Bad Request, Missing Parameter");
        detail.setType(URI.create(
            "https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework" +
                "/web/bind/MissingServletRequestParameterException.html"));
        detail.setProperty("errorCategory", "Missing Parameter");
        detail.setProperty("timestamp", Instant.now());
        detail.setDetail(exception.getMessage());
        return detail;
    }
    
    @ExceptionHandler (Exception.class)
    ProblemDetail handleGeneralExceptions (Exception exception) {
        ProblemDetail detail = forStatusAndDetail(INTERNAL_SERVER_ERROR, exception.getMessage());
        detail.setTitle("General Error: ");
        detail.setType(URI.create(
            "https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Exception" +
                ".html"));
        detail.setProperty("errorCategory", "Internal Error");
        detail.setProperty("timestamp", Instant.now());
        detail.setDetail(exception.getMessage());
        return detail;
    }
}
