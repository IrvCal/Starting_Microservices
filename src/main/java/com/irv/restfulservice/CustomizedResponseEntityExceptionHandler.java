package com.irv.restfulservice;

import com.irv.restfulservice.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice // agrega a los demas controllers para que hagan uso de este
@RestController
public class CustomizedResponseEntityExceptionHandler
extends ResponseEntityExceptionHandler {

    //dentro de esta parte podemos especificar cuales son las clases de excpeciones que nosotros quermos que caigan en este metodo
    @ExceptionHandler(Exception.class)
    public final ResponseEntity handleAllExceptions(Exception e, WebRequest request){

        //Esta es mi clase quie cree para manejar los detalles de las excepciones
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),e.getMessage(),request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);// El http se customiza de acuerdo a lo que queramos
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity handleUserNotFoundException(UserNotFoundException e, WebRequest request){

        //Esta es mi clase que cree para manejar los detalles de las excepciones
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),e.getMessage(),request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(),"Validation failed",ex.getBindingResult().toString());

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
