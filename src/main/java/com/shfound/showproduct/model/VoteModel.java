package com.shfound.showproduct.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 投票model
 */
@Entity
@Table(name = "vote")
@EntityListeners(AuditingEntityListener.class)
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
}
