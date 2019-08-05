package com.welleplus.controller;

import com.welleplus.myenum.ReturnStatus;
import com.welleplus.result.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "admin")
public class AdminController {

//    @RequiresPermissions(value={"user:a", "user:b"}, logical= Logical.OR)
    @RequestMapping(value = "getadmin")
    @RequiresRoles(value = {"admin"})
    public Result getAdmin(){
        Result result = new Result();
        result.setDescription(ReturnStatus.SUCCESS);
        return result;
    }

}
