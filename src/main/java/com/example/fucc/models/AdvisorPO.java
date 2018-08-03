package com.example.fucc.models;
/*
 * @Author liuxiongfeng
 * @Description 投顾观点的实体类
 */


import java.util.Date;

public class AdvisorPO {
    /*private Long viewpointId;
    private Long productId;
    private String title;
    private Long viewpointType;
    private String audioUpload;
    private Integer isShowAudio;
    private Long isTop;
    private Integer riskLevel;
    private Long isChargeable;
    private Double chargeAmt;
    private Long registrant;
    private String abstract;*/

    private Long id;
    private Long yyb;
    private Long ryxx;
    private String advisorId;
    private String orgidAbbr;
    private String jobs;
    private Long advisorType;
    private String advisorIntr;
    private String levelId;
    private Long status;
    private String authority;
    private Date updateTime;
    private Double qaPrice;
    private Long qaIsCharge;
    private String qaProdId;
    private Long qaIsReask;
    private String viewProdId;
    private String advisorProdId;
    private Long qaIsView;
    private Double viewPrice;
    private String tgdjbm;
    private String wgdjbm;
    private String dyqxxx;
    private Long advisorIsCharge;
    private String yspProdId;
    private String textLiveProdId;
    private Double monthPrice;
    private Double quarterPrice;
    private Double yearPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getYyb() {
        return yyb;
    }

    public void setYyb(Long yyb) {
        this.yyb = yyb;
    }

    public Long getRyxx() {
        return ryxx;
    }

    public void setRyxx(Long ryxx) {
        this.ryxx = ryxx;
    }

    public String getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(String advisorId) {
        this.advisorId = advisorId;
    }

    public String getOrgidAbbr() {
        return orgidAbbr;
    }

    public void setOrgidAbbr(String orgidAbbr) {
        this.orgidAbbr = orgidAbbr;
    }

    public String getJobs() {
        return jobs;
    }

    public void setJobs(String jobs) {
        this.jobs = jobs;
    }

    public Long getAdvisorType() {
        return advisorType;
    }

    public void setAdvisorType(Long advisorType) {
        this.advisorType = advisorType;
    }

    public String getAdvisorIntr() {
        return advisorIntr;
    }

    public void setAdvisorIntr(String advisorIntr) {
        this.advisorIntr = advisorIntr;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Double getQaPrice() {
        return qaPrice;
    }

    public void setQaPrice(Double qaPrice) {
        this.qaPrice = qaPrice;
    }

    public Long getQaIsCharge() {
        return qaIsCharge;
    }

    public void setQaIsCharge(Long qaIsCharge) {
        this.qaIsCharge = qaIsCharge;
    }

    public String getQaProdId() {
        return qaProdId;
    }

    public void setQaProdId(String qaProdId) {
        this.qaProdId = qaProdId;
    }

    public Long getQaIsReask() {
        return qaIsReask;
    }

    public void setQaIsReask(Long qaIsReask) {
        this.qaIsReask = qaIsReask;
    }

    public String getViewProdId() {
        return viewProdId;
    }

    public void setViewProdId(String viewProdId) {
        this.viewProdId = viewProdId;
    }

    public String getAdvisorProdId() {
        return advisorProdId;
    }

    public void setAdvisorProdId(String advisorProdId) {
        this.advisorProdId = advisorProdId;
    }

    public Long getQaIsView() {
        return qaIsView;
    }

    public void setQaIsView(Long qaIsView) {
        this.qaIsView = qaIsView;
    }

    public Double getViewPrice() {
        return viewPrice;
    }

    public void setViewPrice(Double viewPrice) {
        this.viewPrice = viewPrice;
    }

    public String getTgdjbm() {
        return tgdjbm;
    }

    public void setTgdjbm(String tgdjbm) {
        this.tgdjbm = tgdjbm;
    }

    public String getWgdjbm() {
        return wgdjbm;
    }

    public void setWgdjbm(String wgdjbm) {
        this.wgdjbm = wgdjbm;
    }

    public String getDyqxxx() {
        return dyqxxx;
    }

    public void setDyqxxx(String dyqxxx) {
        this.dyqxxx = dyqxxx;
    }

    public Long getAdvisorIsCharge() {
        return advisorIsCharge;
    }

    public void setAdvisorIsCharge(Long advisorIsCharge) {
        this.advisorIsCharge = advisorIsCharge;
    }

    public String getYspProdId() {
        return yspProdId;
    }

    public void setYspProdId(String yspProdId) {
        this.yspProdId = yspProdId;
    }

    public String getTextLiveProdId() {
        return textLiveProdId;
    }

    public void setTextLiveProdId(String textLiveProdId) {
        this.textLiveProdId = textLiveProdId;
    }

    public Double getMonthPrice() {
        return monthPrice;
    }

    public void setMonthPrice(Double monthPrice) {
        this.monthPrice = monthPrice;
    }

    public Double getQuarterPrice() {
        return quarterPrice;
    }

    public void setQuarterPrice(Double quarterPrice) {
        this.quarterPrice = quarterPrice;
    }

    public Double getYearPrice() {
        return yearPrice;
    }

    public void setYearPrice(Double yearPrice) {
        this.yearPrice = yearPrice;
    }
}
