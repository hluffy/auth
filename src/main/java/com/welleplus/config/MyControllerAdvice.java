package com.welleplus.config;

import com.welleplus.myenum.ReturnStatus;
import com.welleplus.result.Result;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理
 */
@ControllerAdvice
public class MyControllerAdvice {
    /**
     * 无权限异常
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = UnauthorizedException.class)
    public Result errorHandler(Exception ex) {
        ex.printStackTrace();
        Result result = new Result();
//        result.setCode(2);
        result.setMsg("没有权限！");
        result.setDescription(ReturnStatus.NO_PROMISE);
        return result;
    }

    /**
     * 账号异常
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = AccountException.class)
    public Result errorAuth(Exception ex){
        ex.printStackTrace();
        Result result = new Result();
        result.setMsg("用户名不存在！");
        result.setDescription(ReturnStatus.NO_USERNAME);
        return result;
    }

    /**
     * 账号不存在
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = UnknownAccountException.class)
    public Result unknowAccount(Exception ex){
        ex.printStackTrace();
        Result result = new Result();
        result.setMsg("账号不存在！");
        result.setDescription(ReturnStatus.NO_USERNAME);
        return result;
    }

    /**
     * 账号为激活
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = DisabledAccountException.class)
    public Result disabledAccount(Exception ex){
        ex.printStackTrace();
        Result result = new Result();
        result.setMsg("账号未激活！");
        result.setDescription(ReturnStatus.DISABLED_ACCOUNT);
        return result;
    }

    /**
     * 账号被锁定
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = LockedAccountException.class)
    public Result lockedAccount(Exception ex){
        ex.printStackTrace();
        Result result = new Result();
        result.setMsg("账号被锁定");
        result.setDescription(ReturnStatus.LOCKED_ACCOUNT);
        return result;
    }


    /**
     * 密码错误异常
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = IncorrectCredentialsException.class)
    public Result errorPasswordException(Exception ex){
        ex.printStackTrace();
        Result result = new Result();
        result.setMsg("用户名或密码错误");
        result.setDescription(ReturnStatus.ERROR_PASSWORD);
        return result;
    }

    /**
     * 所有异常
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result errorSystem(Exception ex){
        ex.printStackTrace();
        Result result = new Result();
//        result.setCode(5);
        result.setDescription(ReturnStatus.SYSTEM_ERROR);
        result.setMsg("系统错误！");
        return result;
    }
}
