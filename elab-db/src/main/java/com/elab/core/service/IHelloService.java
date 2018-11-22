package com.elab.core.service;

import com.elab.core.bean.Info;
import com.elab.core.bean.PageInfo;
import com.elab.core.exception.CoreException;
import com.elab.core.models.demo.CustomerDTO;

/**
 * @author liuhx on 2016/12/8 15:55
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public interface IHelloService {
    public String print() throws CoreException;

    Info objParams(CustomerDTO pojo) throws CoreException;

    Info getList(String paraBean) throws CoreException;

    Info getListForObject(String params) throws CoreException;

    String getSingle(String params) throws CoreException;

    Info getSingleForObject(String params) throws CoreException;

    Info insert(String params) throws CoreException;

    String update(String params) throws CoreException;

    String delete(String params) throws CoreException;

    String getPageList(String params) throws CoreException;

    Info getPageListForObject(String params) throws CoreException;

    String getMoreList(String params) throws CoreException;

    String getMoreList2(String params) throws CoreException;
}
