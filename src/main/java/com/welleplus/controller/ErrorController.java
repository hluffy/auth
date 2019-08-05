package com.welleplus.controller;

import com.welleplus.myenum.ReturnStatus;
import com.welleplus.result.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "error")
public class ErrorController {
    @RequestMapping(value = "nologin",method = RequestMethod.GET)
    public Result noLogin(){
        Result result = new Result();
//        result.setCode(1);
        result.setStatus("没有登录");
        result.setDescription(ReturnStatus.NO_LOGIN);
        return result;
    }

    @RequestMapping(value = "noauth",method = RequestMethod.GET)
    public Result noAuth(){
        Result result = new Result();
//        result.setCode(2);
        result.setStatus("没有权限");
        result.setDescription(ReturnStatus.NO_PROMISE);
        return result;
    }
}
