package com.ali.First.spring.project;

import com.ali.First.spring.project.exceptionHandler.ErrorResponse;
import com.ali.First.spring.project.exceptionHandler.NotFoundBook;
import com.ali.First.spring.project.exceptionHandler.NotFoundCategory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
public class GlobalExceptionHandler {

        private final FunctionInterface createNewResponse = ErrorResponse::new;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return status(BAD_REQUEST).body(errors);
    }


    // Invalid URL
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundURLException(NoHandlerFoundException ex, HttpServletRequest request) {
        final ErrorResponse errorResponse = createNewResponse.create(now()
                , NOT_FOUND.value()
                ,"URL is invalid "
                ,"Not found method with this URL !"
                , request.getRequestURI());
        return status(NOT_FOUND)
                .body(errorResponse);
    }

    // Not found method by this URL
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> notFoundRequestedMethod (HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        final ErrorResponse errorResponse = createNewResponse.create(now()
                , METHOD_NOT_ALLOWED.value()
                , "Not found request"
                , "Not found request with this URL"
                , request.getRequestURI());
        return status(METHOD_NOT_ALLOWED)
                .body(errorResponse);
    }

    // Invalid parameter
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> invalidParameterInURL(HttpServletRequest request){
        final ErrorResponse errorResponse = createNewResponse.create(now()
                , BAD_REQUEST.value()
                , "Invalid parameter"
                , "parameter is invalid, Please enter correct values"
                , request.getRequestURI());
        return status(BAD_REQUEST)
                .body(errorResponse);
    }


    // Not Found User
    @ExceptionHandler(NotFoundBook.class)
    public ResponseEntity<ErrorResponse> notFoundUser(NotFoundBook ex, HttpServletRequest request) {
        final String[] spreadingURL = request.getRequestURI().split("/");
        final String foundId = spreadingURL[3];
        final ErrorResponse errorResponse = createNewResponse.create(now()
                , NOT_FOUND.value()
                , "Not found"
                , ex.getMessage()
                , foundId);
        return status(NOT_FOUND).body(errorResponse);
    }

    // NOT FOUND CATEGORY
    @ExceptionHandler(NotFoundCategory.class)
    public ResponseEntity<ErrorResponse> notFoundCategory(NotFoundCategory ex, HttpServletRequest request) {
        final String[] spreadingURl = request.getRequestURI().split("/");
        final String foundCategory = spreadingURl[1];
        ErrorResponse errorResponse = createNewResponse.create(now()
                , NOT_FOUND.value()
                , "Not Found"
                , ex.getMessage()
                , foundCategory
        );
        return status(NOT_FOUND).body(errorResponse);
    }


}
