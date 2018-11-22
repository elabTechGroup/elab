package com.elab.core.bean;

import java.util.List;
import java.util.Map;

/**
 * @author liuhx on 2016/12/19 10:50
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public class PageModel<T> {

    private int rowTotal;
    private int pageSize = 3;
    private int count = 1;
    private int total;
    private int beginIndex;
    private int endIndex;
    private List<T> resultSet;
    private String orderby = "";

    public PageModel(int totalRow, int count) {
        this.rowTotal = totalRow;
        this.count = count;
    }

    public PageModel(int totalRow, int count, int pageSize) {
        this.rowTotal = totalRow;
        this.count = count;
        this.pageSize = pageSize;
    }

    public List<T> getResultSet() {
        return this.resultSet;
    }

    public void setResultSet(List<T> resultSet) {
        this.resultSet = resultSet;
    }

    public int getRowTotal() {
        return this.rowTotal;
    }

    public void setRowTotal(int rowTotal) {
        this.rowTotal = rowTotal;
        this.calculate();
    }

    private void calculate() {
        if(this.rowTotal != 0) {
            this.total = this.rowTotal / this.pageSize + (this.rowTotal % this.pageSize > 0?1:0);
            if(this.count >= this.total) {
                this.count = this.total;
            } else if(this.count < 1) {
                this.count = 1;
            }

            this.beginIndex = (this.count - 1) * this.pageSize;
            if(this.beginIndex > this.rowTotal) {
                this.beginIndex = this.rowTotal - 1;
            }

            this.endIndex = this.beginIndex + this.pageSize - 1;
            if(this.endIndex > this.rowTotal) {
                this.endIndex = this.rowTotal;
            }

        }
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
        this.calculate();
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalRow() {
        return this.rowTotal;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        this.calculate();
    }

    public int getBeginIndex() {
        return this.beginIndex;
    }

    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }

    public int getEndIndex() {
        return this.endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public String getOrderby() {
        return this.orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }
}
