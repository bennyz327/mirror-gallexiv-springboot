package com.team.gallexiv.security;

import com.team.gallexiv.lang.VueData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AccessDeniedException.class)
    public VueData handler(AccessDeniedException e) {
        log.info("security权限不足：----------------{}", e.getMessage());
        return VueData.error("權限不足");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public VueData handler(MethodArgumentNotValidException e) {
        log.info("方法參數異常：----------------{}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.getAllErrors().stream().findFirst().isPresent()) {
            ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
            return VueData.error(objectError.getDefaultMessage());
        }
       return VueData.error("錯誤，無法獲得錯誤訊息");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public VueData handler(IllegalArgumentException e) {
        log.error("Assert異常：----------------{}", e.getMessage());
        return VueData.error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public VueData handler(RuntimeException e) {
        log.error("運行時異常：----------------{}", e);
        return VueData.error(e.getMessage());
    }
}
