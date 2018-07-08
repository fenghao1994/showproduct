package com.shfound.showproduct.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户model
 */
@Entity
@Table(name = "user")
public class UserModel {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    //手机号
    @Column
    private String mobile;

    //密码
    @Column
    private String password;

    //头像
    @Column
    private String headImg;

    //创建时间
    @Column
    private Date createTime;

    //修改时间
    @Column
    private Date editTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }
}
