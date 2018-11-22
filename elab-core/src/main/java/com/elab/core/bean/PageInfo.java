package com.elab.core.bean;

/**
 * @author liuhx on 2016/12/19 10:50
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class PageInfo extends BaseInfo {
    private PageModel pageModel;

    public PageInfo() {
    }

    public PageInfo(boolean success, String errorCode, String message) {
        super(success, errorCode, message);
    }

    public PageModel getPageModel() {
        return this.pageModel;
    }

    public void setPageModel(PageModel pageModel) {
        this.pageModel = pageModel;
    }
}
