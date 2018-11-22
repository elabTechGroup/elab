package com.elab.core.bean;

import java.util.List;
import java.util.Map;

/**
 * 单个对象或者是集合，使用这个返回类
 * @author liuhx on 2016/12/11 15:11
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class Info<T> extends BaseInfo {
    private T single;
    private List<T> list;
    private int id;
    private PageModel<T> pageModel;

    public Info() {

    }

    public Info(Boolean success, String errorCode, String message) {
        super(success, errorCode, message);
    }

    public T getSingle() {
        return single;
    }

    public void setSingle(T single) {
        this.single = single;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PageModel<T> getPageModel() {
        return pageModel;
    }

    public void setPageModel(PageModel<T> pageModel) {
        this.pageModel = pageModel;
    }
}
