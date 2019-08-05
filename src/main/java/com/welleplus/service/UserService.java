package com.welleplus.service;

import com.welleplus.entity.User;
import com.welleplus.result.Result;

public interface UserService {
    /**
     * 添加用户
     * @param user
     * @return
     */
    Result addUser(User user);
}
