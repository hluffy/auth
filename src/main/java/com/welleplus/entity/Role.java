package com.welleplus.entity;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "role")
@Table(name = "role")
public class Role implements Serializable {
    private String id;
    private String name;
    private User user;


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

    @Column(name = "NAME",unique = true,nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch=FetchType.LAZY,cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)//可选属性optional=false,表示fence不能为空。删除文章，不影响用户
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
