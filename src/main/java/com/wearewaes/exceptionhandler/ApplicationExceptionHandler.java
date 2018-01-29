package com.wearewaes.exceptionhandler;

import com.wearewaes.controller.response.Response;
import com.wearewaes.controller.response.ResponseBuilder;
import com.wearewaes.service.exception.EmptyJsonDataException;
import com.wearewaes.service.exception.FileAlreadyExistsException;
import com.wearewaes.service.exception.IDNotFoundException;
import com.wearewaes.service.exception.NumberOfFilesToDiffException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    private MessageSource messageSource;

    public ApplicationExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Response> messsagesList = createMessageList("invalid.message", ex, null);
        return handleExceptionInternal(ex, messsagesList, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Response> messsagesList = new ArrayList<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            String userMessage = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String developerMessage = fieldError.toString();
            messsagesList.add(ResponseBuilder.oneResponse().withUserMessage(userMessage).withDeveloperMessage(developerMessage).get());
        }

        return handleExceptionInternal(ex, messsagesList, headers, status, request);
    }

    @ExceptionHandler({EmptyJsonDataException.class})
    public ResponseEntity<Object> handleEmptyJsonDataException(EmptyJsonDataException ex, WebRequest request) {
        List<Response> messageList = createMessageList("empty.jsondata.uploaded", ex, null);
        return handleExceptionInternal(ex, messageList, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({FileAlreadyExistsException.class})
    public ResponseEntity<Object> handleFileAlreadyExistsException(FileAlreadyExistsException ex, WebRequest request) {
        String[] args = { ex.getType().name(), ex.getId().toString() };
        List<Response> messageList = createMessageList("already.exists.jsondata", ex, args);
        return handleExceptionInternal(ex, messageList, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({NumberOfFilesToDiffException.class})
    public ResponseEntity<Object> handleFileAlreadyExistsException(NumberOfFilesToDiffException ex, WebRequest request) {
        String[] args = { ex.getId().toString(), ex.isHasLeftJson() ? "OK" : "Missing", ex.isHasRightJson() ? "OK" : "Missing" };
        List<Response> messageList = createMessageList("file.missing", ex, args);
        return handleExceptionInternal(ex, messageList, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({IDNotFoundException.class})
    public ResponseEntity<Object> handleFileAlreadyExistsException(IDNotFoundException ex, WebRequest request) {
        String[] args = { ex.getId().toString() };
        List<Response> messageList = createMessageList("id.notfound", ex, args);
        return handleExceptionInternal(ex, messageList, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    private List<Response> createMessageList(String key, Exception ex, String[] args) {
        String userMessage = messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
        String developerMessage = userMessage + ". StackTrace: " + ex.getCause() != null ? ex.getCause().toString() : ex.toString();
        return Arrays.asList(ResponseBuilder.oneResponse().withUserMessage(userMessage).withDeveloperMessage(developerMessage).get());
    }

}
