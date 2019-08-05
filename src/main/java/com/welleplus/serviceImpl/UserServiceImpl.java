package com.welleplus.serviceImpl;

import com.welleplus.entity.Role;
import com.welleplus.entity.User;
import com.welleplus.myenum.ReturnStatus;
import com.welleplus.repositories.UserRepository;
import com.welleplus.result.Result;
import com.welleplus.service.UserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;

    @Override
    @Transactional
    public Result addUser(User user) {
        Result result = new Result();
        if(user == null || StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())){
            result.setDescription(ReturnStatus.PARAM_NOT_EMPTY);
            return result;
        }
//        判断用户名是否存在
        if(userRepository.findByUsername(user.getUsername()) != null){
            result.setDescription(ReturnStatus.USER_EXIST);
            return result;
        }
        String hashAlgorithmName = "MD5";
        int hashIterations = 1024;
        String password = new SimpleHash(hashAlgorithmName,user.getPassword(),user.getUsername(),hashIterations).toBase64();
        user.setPassword(password);
        if(user.getRolestrs() != null && user.getRolestrs().size() > 0){
            Set<Role> roles = new HashSet<>();
            for(String name:user.getRolestrs()){
                Role role = new Role();
                role.setName(name);
                role.setUser(user);
                roles.add(role);
            }
            user.setRoles(roles);
        }
        user = userRepository.save(user);
        result.setDescription(ReturnStatus.SUCCESS);
        result.setData(user);

        return result;
    }

    public static void main(String[] args){
        String str = "user";
        String hashAlgorithmName = "MD5";
        int hashIterations = 1024;
        String password = new SimpleHash(hashAlgorithmName,"user",str,hashIterations).toBase64();
        System.out.println(password);
    }
}
