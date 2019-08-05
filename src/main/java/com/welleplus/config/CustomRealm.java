package com.welleplus.config;

import com.welleplus.entity.Role;
import com.welleplus.entity.User;
import com.welleplus.repositories.UserRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义realm
 */
public class CustomRealm extends AuthorizingRealm {
    @Resource
    private UserRepository userRepository;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        User user = userRepository.findByUsername(username);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> stringSet = new HashSet<>();
        for(Role role:user.getRoles()){
            stringSet.add(role.getName());
        }
        info.setRoles(stringSet);

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        System.out.println("-------身份认证方法--------");
//        String userName = (String) authenticationToken.getPrincipal();
//        String userPwd = new String((char[]) authenticationToken.getCredentials());
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String userName = token.getUsername();

        //根据用户名从数据库获取密码
        User user = userRepository.findByUsername(userName);
        if(user == null){
            throw new UnknownAccountException();    //账号不存在
        }
        if(1 == user.getIsDisabled()){
            throw new DisabledAccountException();   //账号是否激活
        }
        if(1 == user.getIsLock()){
            throw new LockedAccountException(); //账号是否锁定
        }
        return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(), ByteSource.Util.bytes(user.getUsername()),getName());
    }

}
