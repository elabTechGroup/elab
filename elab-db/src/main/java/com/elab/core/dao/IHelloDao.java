package com.elab.core.dao;

import com.elab.core.bean.PageModel;
import com.elab.core.exception.CoreException;
import com.elab.core.models.demo.CustomerDTO;
import com.elab.core.models.demo.EnumerationDemo;
import com.elab.core.models.demo.HelloDTO;
import com.elab.core.models.demo.LayoutTemplateDTO;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuhx on 2016/12/8 15:54
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
public interface IHelloDao {

    String print() throws CoreException;

    String otherPrint() throws CoreException;

    void update() throws CoreException;

    int update(EnumerationDemo demo) throws CoreException;

    List<Map<String, Object>> getList(LinkedHashMap<String, Object> params) throws CoreException;

    List<CustomerDTO> getListForObject(LinkedHashMap<String, Object> params) throws CoreException;


    Map<String, Object> getSingle(LinkedHashMap<String, Object> params) throws CoreException;

    HelloDTO getSingleForObject(LinkedHashMap<String, Object> params) throws CoreException;

    int insert(LinkedHashMap<String, Object> params) throws CoreException;

    int update(LinkedHashMap<String, Object> params) throws CoreException;

    int delete(LinkedHashMap<String, Object> params) throws CoreException;

    PageModel getPageList(int pageNo,int pageSize, LinkedHashMap<String, Object> params) throws CoreException;

    PageModel<LayoutTemplateDTO> getPageListForObject(int pageNo, int pageSize, LinkedHashMap<String, Object> params) throws CoreException;

    List<Map<String, Object>> getMoreList(LinkedHashMap<String, Object> params) throws CoreException;

    List<Map<String, Object>> getMoreList2(LinkedHashMap<String, Object> params) throws CoreException;
}
