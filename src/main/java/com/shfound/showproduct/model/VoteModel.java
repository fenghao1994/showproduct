package com.shfound.showproduct.model;

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
    private int userId;

    @Column
    private int prodId;

    //个人预投额度
    @Column
    private double limit;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int proId) {
        this.prodId = proId;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
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
