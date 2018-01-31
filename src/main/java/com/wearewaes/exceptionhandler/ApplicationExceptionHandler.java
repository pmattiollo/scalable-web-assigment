package com.wearewaes.exceptionhandler;

import com.wearewaes.controller.response.Response;
import com.wearewaes.controller.response.ResponseBuilder;
import com.wearewaes.service.exception.EmptyJsonDataException;
import com.wearewaes.service.exception.InputDataAlreadyExistsException;
import com.wearewaes.service.exception.IDNotFoundException;
import com.wearewaes.service.exception.InsufficientDataToDiffException;
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

/**
 * Exception handler for all expected exceptions of the application
 */
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    private MessageSource messageSource;

    public ApplicationExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Generics exceptions handler. These exceptions usually are about bad formed http requests
     *
     * @param ex that represents the exception to be treated
     * @param headers that represents the http headers
     * @param status that represents the http status
     * @param request that represents the request
     * @return the {@link ResponseEntity} with some custom messages
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        List<Response> messsagesList = createMessageList("invalid.message", ex, null);
        return handleExceptionInternal(ex, messsagesList, headers, status, request);
    }

    /**
     * Invalid arguments exceptions handler. These exceptions are about bad formed data inputs where the auto binding is not possible
     *
     * @param ex that represents the exception to be treated
     * @param headers that represents the http headers
     * @param status that represents the http status
     * @param request that represents the request
     * @return the {@link ResponseEntity} with some custom messages
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        List<Response> messsagesList = new ArrayList<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            String userMessage = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String developerMessage = fieldError.toString();
            messsagesList.add(ResponseBuilder.oneResponse().withUserMessage(userMessage).withDeveloperMessage(developerMessage).get());
        }

        return handleExceptionInternal(ex, messsagesList, headers, status, request);
    }

    /**
     * Empty json data exceptions handler. These exceptions are thrown when the users inform a json with no data inside value tag
     *
     * @param ex that represents the exception to be treated
     * @param request that represents the request
     * @return the {@link ResponseEntity} with some custom messages
     */
    @ExceptionHandler({ EmptyJsonDataException.class })
    public ResponseEntity<Object> handleEmptyJsonDataException(EmptyJsonDataException ex, WebRequest request) {
        List<Response> messageList = createMessageList("empty.jsondata.uploaded", ex, null);
        return handleExceptionInternal(ex, messageList, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Input data already exists exceptions handler. These exceptions are thrown when the users try to upload some data that was previously uploaded
     * according to {@link com.wearewaes.model.InputType} values
     *
     * @param ex that represents the exception to be treated
     * @param request that represents the request
     * @return the {@link ResponseEntity} with some custom messages
     */
    @ExceptionHandler({ InputDataAlreadyExistsException.class })
    public ResponseEntity<Object> handleFileAlreadyExistsException(InputDataAlreadyExistsException ex, WebRequest request) {
        String[] args = { ex.getType().name(), ex.getId().toString() };
        List<Response> messageList = createMessageList("already.exists.jsondata", ex, args);
        return handleExceptionInternal(ex, messageList, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Insufficient data to compare exceptions handler. These exceptions are thrown when the users try get the diff data but is missing one of the
     * left or * Insufficient number of files exceptions handler. These exceptions are thrown when the users try get the diff files but is missing one
     * of the left or right data
     *
     * @param ex that represents the exception to be treated
     * @param request that represents the request
     * @return the {@link ResponseEntity} with some custom messages
     */
    @ExceptionHandler({ InsufficientDataToDiffException.class })
    public ResponseEntity<Object> handleFileAlreadyExistsException(InsufficientDataToDiffException ex, WebRequest request) {
        String[] args = { ex.getId().toString(), ex.isHasLeftJson() ? "OK" : "Missing", ex.isHasRightJson() ? "OK" : "Missing" };
        List<Response> messageList = createMessageList("file.missing", ex, args);
        return handleExceptionInternal(ex, messageList, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * ID not found exceptions handler. These exceptions are thrown when the users try get the diff files but is missing one of the left or *
     * Insufficient number of files exceptions handler. These exceptions are thrown when the users try get the diff files of an ID that does'n exist
     *
     * @param ex that represents the exception to be treated
     * @param request that represents the request
     * @return the {@link ResponseEntity} with some custom messages
     */
    @ExceptionHandler({ IDNotFoundException.class })
    public ResponseEntity<Object> handleFileAlreadyExistsException(IDNotFoundException ex, WebRequest request) {
        String[] args = { ex.getId().toString() };
        List<Response> messageList = createMessageList("id.notfound", ex, args);
        return handleExceptionInternal(ex, messageList, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    private List<Response> createMessageList(String key, Exception ex, String[] args) {
        String userMessage = messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
        String developerMessage = userMessage + ". Exception: " + (ex.getCause() != null ? ex.getCause().toString() : ex.toString());
        return Arrays.asList(ResponseBuilder.oneResponse().withUserMessage(userMessage).withDeveloperMessage(developerMessage).get());
    }

}
