package com.welleplus.repositories;

import com.welleplus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
    /**
     * 添加用户
     * @param user
     * @return
     */
    User save(User user);

    /**
     * 根据用户名获取用户对象
     * @param username
     * @return
     */
    User findByUsername(String username);
}
