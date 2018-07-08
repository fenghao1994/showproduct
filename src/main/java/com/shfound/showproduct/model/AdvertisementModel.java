package com.shfound.showproduct.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 轮播 组件model
 */
@Entity
@Table(name = "advertisement")
public class AdvertisementModel {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    //轮播图片url地址
    @Column
    private String imgUrl;

    //点击图片跳转地址
    @Column
    private String jumpPage;

    //是否展示这张轮播图片
    @Column
    private boolean isShow;

    //备注
    @Column
    private String marks;

    @Column
    private Date createDate;

    @Column
    private Date editDate;

    @Column
    private boolean isDelete;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getJumpPage() {
        return jumpPage;
    }

    public void setJumpPage(String jumpPage) {
        this.jumpPage = jumpPage;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
