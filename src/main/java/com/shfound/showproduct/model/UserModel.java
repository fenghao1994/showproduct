package com.shfound.showproduct.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 用户model
 */
@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class UserModel {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column
    private String wxId;

    //上级邀请人
    @Column String superInviteCode;

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
    @CreatedDate
    private Long createTime;

    //修改时间
    @LastModifiedDate
    private Long editTime;

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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getEditTime() {
        return editTime;
    }

    public void setEditTime(Long editTime) {
        this.editTime = editTime;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getSuperInviteCode() {
        return superInviteCode;
    }

    public void setSuperInviteCode(String superInviteCode) {
        this.superInviteCode = superInviteCode;
    }
}
