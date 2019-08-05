package com.welleplus.controller;

import com.welleplus.myenum.ReturnStatus;
import com.welleplus.result.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "list")
public class ListController {

    @RequestMapping(value = "getlist")
    public Result getList(){
        Result result = new Result();
        result.setDescription(ReturnStatus.SUCCESS);
        return result;
    }
}
