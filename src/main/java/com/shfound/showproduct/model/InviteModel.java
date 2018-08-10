package com.shfound.showproduct.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

//邀请审核model
@Entity
@Table(name = "invite")
@EntityListeners(AuditingEntityListener.class)
public class InviteModel {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    //邀请者
    @Column
    private String superUser;
    //被邀请者
    @Column
    private String subUser;
    //图片地址
    @Column
    private String imgPath;
    //状态 0 无操作 1 通过 2 不通过
    @Column
    private int status;

    //图片是否已删除
    @Column
    private boolean isDelete;

    //创建时间
    @CreatedDate
    private Long createTime;

    //修改时间
    @LastModifiedDate
    private Long editTime;

    public String getSuperUser() {
        return superUser;
    }

    public void setSuperUser(String superUser) {
        this.superUser = superUser;
    }

    public String getSubUser() {
        return subUser;
    }

    public void setSubUser(String subUser) {
        this.subUser = subUser;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
