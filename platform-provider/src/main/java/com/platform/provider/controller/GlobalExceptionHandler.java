package com.platform.provider.controller;

import com.platform.provider.entity.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Response defaultErrorHandler(Exception e) {
        e.printStackTrace();
        Response response = new Response();
        response.setMessage(e.getMessage());
        // TODO 其它异常
        if (e instanceof NoHandlerFoundException) {
            response.setCode(404);
        } else {
            response.setCode(500);
        }
        return response;
    }

}
