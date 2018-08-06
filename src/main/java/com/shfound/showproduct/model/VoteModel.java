package com.shfound.showproduct.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * 投票model
 */
@Entity
@Table(name = "vote")
public class VoteModel {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column
    private String wxId;

    @Column
    private int prodId;

    //个人预投额度
    @Column
    private int prodLimit;

    //创建时间
    @CreatedDate
    private Date createTime;

    //修改时间
    @LastModifiedDate
    private Date editTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int proId) {
        this.prodId = proId;
    }

    public int getProdLimit() {
        return prodLimit;
    }

    public void setProdLimit(int prodLimit) {
        this.prodLimit = prodLimit;
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
