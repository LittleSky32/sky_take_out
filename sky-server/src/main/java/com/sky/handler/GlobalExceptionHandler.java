package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /*
    Cause: java.sql.SQLIntegrityConstraintViolationException:
    Duplicate entry 'wsnd' for key 'employee.idx_username'
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
         String msg = ex.getMessage();
         if (msg.contains("Duplicate entry")){
             String[] s = msg.split(" ");
             String return_msg = s[2] + MessageConstant.ALREADY_EXISTS;
             return Result.error(return_msg);
         } else {
             return Result.error(MessageConstant.UNKNOWN_ERROR);
         }
    }

}
