package com.example.fucc.vo;

import java.util.List;

/**
 * @program: fucc
 * @description: 观点列表
 * @author: liuxiongfeng
 * @create: 2018-10-25 19:50
 **/
public class ViewpointListVO<T> {
    private String code;
    private String note;
    private String rows;
    private boolean success;
    private List<T> viewpoint;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<T> getViewpoint() {
        return viewpoint;
    }

    public void setViewpoint(List<T> viewpoint) {
        this.viewpoint = viewpoint;
    }
}
