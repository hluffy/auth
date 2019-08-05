package com.welleplus.controller;

import com.welleplus.entity.User;
import com.welleplus.myenum.ReturnStatus;
import com.welleplus.result.Result;
import com.welleplus.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping(value = "user")
public class UserController {
    @Resource
    private UserService userService;
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "login")
//    @CrossOrigin
    public Result login(String username,String password){
        Result result = new Result();
        if(StringUtils.isEmpty(username)){
//            result.setCode(1300);
//            result.setStatus("参数不能为空");
            result.setDescription(ReturnStatus.PARAM_NOT_EMPTY);
            return result;
        }
        Subject currentUser = SecurityUtils.getSubject();
//        if(!currentUser.isAuthenticated()){
//            result.setCode(1301);
//            result.setStatus("用户未登录");
//            return result;
//        }
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        currentUser.login(token);
        result.setDescription(ReturnStatus.SUCCESS);
//        try {
//
////            Session session = currentUser.getSession();
////            session.setAttribute("username",username);
//        } catch (UnknownAccountException e){
//            e.printStackTrace();
////            result.setCode(3);
//            result.setMsg("账号不存在");
//        } catch (AccountException e){
//            e.printStackTrace();
////            result.setCode(3);
//            result.setMsg("账号异常");
//        } catch (IncorrectCredentialsException e){
//            e.printStackTrace();;
////            result.setCode(3);
//            result.setMsg("用户名或密码错误");
//        } catch (Exception e){
//            e.printStackTrace();
////            result.setCode(3);
//            result.setMsg("其他错误");
//        }

        return result;
    }

    @RequestMapping(value = "logout")
    public Result logout(){
        Result result = new Result();
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        result.setDescription(ReturnStatus.SUCCESS);
        return result;
    }

    @RequestMapping(value = "getuser")
    @RequiresRoles(value = {"admin"})
//    @RequiresPermissions(value={"user:show", "user:admin"}, logical= Logical.OR)
//    @RequiresPermissions(value={"user:show", "user:admin"})
//    @RequiresRoles(value = {"user"})
    public Result getUser(){
        Result result = new Result();
        result.setMsg("getuser");
        result.setDescription(ReturnStatus.SUCCESS);
        return result;
    }

    /**
     * 添加用户
     * @return
     */
    @RequestMapping(value = "adduser")
    @RequiresRoles({"admin"})
//    @RequiresPermissions({"user:create"})
    public Result addUser(@RequestBody User user){
        Result result = userService.addUser(user);
        return result;
    }

}
