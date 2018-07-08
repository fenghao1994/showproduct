package com.shfound.showproduct.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 项目信息model
 */
@Entity
@Table(name = "productInfo")
public class ProductInfoModel {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    //项目名称
    @Column
    private String proName;

    //项目简单描述
    @Column
    private String proSimDesc;

    //项目评级
    @Column
    private String proGrade;

    //项目是否火热
    @Column
    private boolean isHot;

    //开始收集时间
    @Column
    private Date StratTime;

    //结束收集时间
    @Column
    private Date endTime;

    //项目小图片  icon url
    @Column
    private String iconUrl;
    //项目大图 url
    @Column
    private String proImgUrl;
    //项目标签  以"|" 分割
    @Column
    private String proLabel;

    //项目类型 1 进行中 2即将开始 3 已经结束 4 投票
    @Column
    private int proType;

    //备注
    @Column
    private String marks;

    //项目详情 H5内容
    @Column
    private String proRecommend;

    //项目真实额度
    @Column
    private String proRealLimit;

    //项目假额度
    @Column
    private String proMockLimit;

    //收币地址
    @Column
    private String collectionCoinAddress;

    //是否停止收币
    @Column
    private boolean isStopCollection;

    //创建时间
    @Column
    private Date createTime;

    //修改时间
    @Column
    private Date editTime;

    @Column
    private boolean isDelete;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProSimDesc() {
        return proSimDesc;
    }

    public void setProSimDesc(String proSimDesc) {
        this.proSimDesc = proSimDesc;
    }

    public String getProGrade() {
        return proGrade;
    }

    public void setProGrade(String proGrade) {
        this.proGrade = proGrade;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }

    public Date getStratTime() {
        return StratTime;
    }

    public void setStratTime(Date stratTime) {
        StratTime = stratTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getProImgUrl() {
        return proImgUrl;
    }

    public void setProImgUrl(String proImgUrl) {
        this.proImgUrl = proImgUrl;
    }

    public String getProLabel() {
        return proLabel;
    }

    public void setProLabel(String proLabel) {
        this.proLabel = proLabel;
    }

    public int getProType() {
        return proType;
    }

    public void setProType(int proType) {
        this.proType = proType;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getProRecommend() {
        return proRecommend;
    }

    public void setProRecommend(String proRecommend) {
        this.proRecommend = proRecommend;
    }

    public String getProRealLimit() {
        return proRealLimit;
    }

    public void setProRealLimit(String proRealLimit) {
        this.proRealLimit = proRealLimit;
    }

    public String getProMockLimit() {
        return proMockLimit;
    }

    public void setProMockLimit(String proMockLimit) {
        this.proMockLimit = proMockLimit;
    }

    public String getCollectionCoinAddress() {
        return collectionCoinAddress;
    }

    public void setCollectionCoinAddress(String collectionCoinAddress) {
        this.collectionCoinAddress = collectionCoinAddress;
    }

    public boolean isStopCollection() {
        return isStopCollection;
    }

    public void setStopCollection(boolean stopCollection) {
        isStopCollection = stopCollection;
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

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
