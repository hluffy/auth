package com.welleplus.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity(name = "user")
@Table(name = "user")
public class User implements Serializable {
    private String id;
    private String username;
    private String password;
    private Integer isLock; //是否锁定，0位未锁定，1位锁定
    private Integer isDisabled; //是否激活，0位激活，1位未激活
    private Timestamp createTime;
    private Timestamp updateTime;

    private Set<Role> roles;
    private Set<String> rolestrs;

    @Id
    @GenericGenerator(name="idGenerator",strategy="uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "USERNAME",unique = true,nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "PASSWORD",nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "IS_LOCK",insertable = false,columnDefinition = "int default 0")
    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    @Column(name = "IS_DISABLED",insertable = false,columnDefinition = "int default 0")
    public Integer getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Integer isDisabled) {
        this.isDisabled = isDisabled;
    }

    @Column(name = "CREATE_TIME")
    @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @CreatedDate
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Column(name = "UPDATE_TIME")
    @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @UpdateTimestamp
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }


    @JsonIgnoreProperties(value = { "user" })  //控制json深度，避免死循环
    @OneToMany(mappedBy = "user",cascade= CascadeType.ALL,fetch=FetchType.LAZY)
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Transient
    public Set<String> getRolestrs() {
        return rolestrs;
    }

    public void setRolestrs(Set<String> rolestrs) {
        this.rolestrs = rolestrs;
    }
}
