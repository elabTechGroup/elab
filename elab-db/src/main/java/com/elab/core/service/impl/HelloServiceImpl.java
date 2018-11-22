package com.elab.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.elab.core.aop.annotations.ExceptionHandle;
import com.elab.core.bean.Info;
import com.elab.core.bean.PageInfo;
import com.elab.core.bean.PageModel;
import com.elab.core.dao.IHelloDao;
import com.elab.core.datasource.DataSource;
import com.elab.core.datasource.DataSourceHelp;
import com.elab.core.exception.CoreException;
import com.elab.core.models.demo.CustomerDTO;
import com.elab.core.models.demo.HelloDTO;
import com.elab.core.models.demo.LayoutTemplateDTO;
import com.elab.core.service.IHelloService;
import com.elab.core.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuhx on 2016/12/8 15:55
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
@Service
public class HelloServiceImpl implements IHelloService {

    @Autowired
    private IHelloDao helloDao;

    @Override
    @Transactional
    @DataSource(name = DataSource.system)
    @ExceptionHandle(ModuleName = HelloServiceImpl.class, Throwable = true)
    public String print() throws CoreException {
        System.out.println("servie:"+helloDao.toString());
        String println = helloDao.print();
        DataSourceHelp.setDataSource(DataSource.mvp);
        otherPrint();
        System.out.println(println);
        return "service hello---------"+println;
    }

    @Override
    @DataSource(name = DataSource.system)
    @ExceptionHandle(ModuleName = HelloServiceImpl.class, Throwable = true)
    public Info objParams(CustomerDTO pojo) throws CoreException {
        LinkedHashMap<String, Object> map = new LinkedHashMap();
        map.put("employeeno", pojo.getEmployeeNo());
        map.put("employeename", pojo.getEmployeeName());
        map.put("phone", pojo.getPhone());
        map.put("email", pojo.getEmail());
        Info info = new Info();
        List<CustomerDTO> customerDTOs = helloDao.getListForObject(map);
        info.setList(customerDTOs);
        return info;
    }

    private List<CustomerDTO> testA() {
        DataSourceHelp.setDataSource("system");
        LinkedHashMap map = new LinkedHashMap<>();
        map.put("employeeno", null);
        map.put("employeename", null);
        map.put("phone", null);
        map.put("email", null);
        List<CustomerDTO> list = helloDao.getListForObject(map);
        return list;
    }

    @Override
    @DataSource(name = DataSource.system)
    @ExceptionHandle(ModuleName = HelloServiceImpl.class, Throwable = true)
    public String getSingle(String params) throws CoreException {
        JSONObject jsonObject = ObjectUtils.strParseJsonNode(params);
        //name=:name and value=:value and t ype=:type and status=:status
        String name = ObjectUtils.strParseNull(jsonObject.getString("name"));
        String value = ObjectUtils.strParseNull(jsonObject.getString("value"));
        String type = ObjectUtils.strParseNull(jsonObject.getString("type"));
        String status = ObjectUtils.strParseNull(jsonObject.getString("status"));
        LinkedHashMap<String, Object> map = new LinkedHashMap();
        map.put("name", "%"+name+"%");
        map.put("value", value);
        map.put("type", type);
        map.put("status", status);
        Map<String, Object> single = helloDao.getSingle(map);
        Info info = new Info();
        info.setSingle(single);
        return ObjectUtils.objectParseJsonStr(info);
    }

    @Override
    @DataSource(name = DataSource.system)
    @ExceptionHandle(ModuleName = HelloServiceImpl.class, Throwable = true)
    public Info getSingleForObject(String params) throws CoreException {
        JSONObject jsonObject = ObjectUtils.strParseJsonNode(params);
        //name=:name and value=:value and t ype=:type and status=:status
        String name = ObjectUtils.strParseNull(jsonObject.getString("name"));
        String value = ObjectUtils.strParseNull(jsonObject.getString("value"));
        String type = ObjectUtils.strParseNull(jsonObject.getString("type"));
        String status = ObjectUtils.strParseNull(jsonObject.getString("status"));
        LinkedHashMap<String, Object> map = new LinkedHashMap();
        map.put("name", "%"+name+"%");
        map.put("value", value);
        map.put("type", type);
        map.put("status", status);
        HelloDTO single = helloDao.getSingleForObject(map);
        Info info = new Info();
        info.setSingle(single);
        return info;
    }

    @Override
    @DataSource(name = DataSource.test)
    @ExceptionHandle(ModuleName = HelloServiceImpl.class, Throwable = true)
    public Info getList(String paraBean) throws CoreException {
        JSONObject jsonObject = ObjectUtils.strParseJsonNode(paraBean);
        String employeeno = ObjectUtils.strParseNull(jsonObject.getString("employeeno"));
        String employeename = ObjectUtils.strParseNull(jsonObject.getString("employeename"));
        String phone = ObjectUtils.strParseNull(jsonObject.getString("phone"));
        String email = ObjectUtils.strParseNull(jsonObject.getString("email"));
        LinkedHashMap<String, Object> map = new LinkedHashMap();
        /*map.put("employeeno", employeeno);
        map.put("employeename", employeename);
        map.put("phone", phone);
        map.put("email", email);*/
        map.put("project", null);
        Info info = new Info();
        List<Map<String, Object>> list = helloDao.getList(map);
        info.setList(list);

        testA();


        return info;
    }

    @Override
    @DataSource(name = DataSource.system)
    @ExceptionHandle(ModuleName = HelloServiceImpl.class, Throwable = true)
    public Info getListForObject(String params) throws CoreException {
        JSONObject jsonObject = ObjectUtils.strParseJsonNode(params);
        String employeeno = ObjectUtils.strParseNull(jsonObject.getString("employeeno"));
        String employeename = ObjectUtils.strParseNull(jsonObject.getString("employeename"));
        String phone = ObjectUtils.strParseNull(jsonObject.getString("phone"));
        String email = ObjectUtils.strParseNull(jsonObject.getString("email"));
        LinkedHashMap<String, Object> map = new LinkedHashMap();
        map.put("employeeno", employeeno);
        map.put("employeename", employeename);
        map.put("phone", phone);
        map.put("email", email);
        Info info = new Info();
        List<CustomerDTO> list = helloDao.getListForObject(map);
        info.setList(list);
        return info;
    }

    @Override
    @Transactional
    @DataSource(name = DataSource.system)
    @ExceptionHandle(ModuleName = HelloServiceImpl.class, Throwable = true)
    public Info insert(String params) throws CoreException {
//        int i = 1/ 0;
        Info info = new Info();
        JSONObject jsonObject = ObjectUtils.strParseJsonNode(params);
        String name = ObjectUtils.strParseNull(jsonObject.getString("name"));
        String value = ObjectUtils.strParseNull(jsonObject.getString("value"));
        String type = ObjectUtils.strParseNull(jsonObject.getString("type"));
        String parentcode = ObjectUtils.strParseNull(jsonObject.getString("parentcode"));
        String remark = ObjectUtils.strParseNull(jsonObject.getString("remark"));
        String status = ObjectUtils.strParseNull(jsonObject.getString("status"));
        //(:name, :value, :type, :parentcode, :remark, SYSDATE(), :status, sysdate())
        LinkedHashMap<String, Object> map = new LinkedHashMap();
        map.put("name", name);
        map.put("value", "");
        map.put("type", null);
//        map.put("parentcode", parentcode);
        map.put("remark", null);
//        map.put("status", status);
        int id = helloDao.insert(map);
        info.setId(id);
        return info;
    }

    @Override
    @Transactional
    @DataSource(name = DataSource.system)
    @ExceptionHandle(ModuleName = HelloServiceImpl.class, Throwable = true)
    public String update(String params) throws CoreException {
        Info info = new Info();
        JSONObject jsonObject = ObjectUtils.strParseJsonNode(params);
        String name = ObjectUtils.strParseNull(jsonObject.getString("name"));
        String remark = ObjectUtils.strParseNull(jsonObject.getString("remark"));
        String id = ObjectUtils.strParseNull(jsonObject.getString("id"));
        String type = ObjectUtils.strParseNull(jsonObject.getString("type"));
        String status = ObjectUtils.strParseNull(jsonObject.getString("status"));
        LinkedHashMap<String, Object> map = new LinkedHashMap();
        map.put("status", status);
        map.put("type", type);
        map.put("id", id);
//        map.put("remark", remark);
        map.put("name", name);

        /*EnumerationDemo demo = new EnumerationDemo();
        demo.setId(id);
        demo.setType("integral_add_type");
        demo.setName(name);
        int count = helloDao.update(demo);*/
        int count = helloDao.update(map);
//        int i= 11/0;
        info.setId(count);
        return ObjectUtils.objectParseJsonStr(info);
    }

    @Override
    @DataSource(name = DataSource.system)
    @ExceptionHandle(ModuleName = HelloServiceImpl.class, Throwable = true)
    public String delete(String params) throws CoreException {
        Info info = new Info();
        JSONObject jsonObject = ObjectUtils.strParseJsonNode(params);
        String id = ObjectUtils.strParseNull(jsonObject.getString("id"));
        String status = ObjectUtils.strParseNull(jsonObject.getString("status"));
        String type = ObjectUtils.strParseNull(jsonObject.getString("type"));
        LinkedHashMap<String, Object> map = new LinkedHashMap();
        map.put("type", type);
        int count = helloDao.delete(map);
        info.setId(count);
        return ObjectUtils.objectParseJsonStr(info);
    }

    @Override
    @DataSource(name = DataSource.mvp)
    @ExceptionHandle(ModuleName = HelloServiceImpl.class, Throwable = true)
    public String getPageList(String params) throws CoreException {
        JSONObject jsonObject = ObjectUtils.strParseJsonNode(params);
        String name = ObjectUtils.strParseNull(jsonObject.getString("name"));
        String buildingnum = ObjectUtils.strParseNull(jsonObject.getString("buildingnum"));
        String frame_code = ObjectUtils.strParseNull(jsonObject.getString("frame_code"));
        String template_code = ObjectUtils.strParseNull(jsonObject.getString("template_code"));
        String layoutplancode = ObjectUtils.strParseNull(jsonObject.getString("layoutplancode"));
        String status = ObjectUtils.strParseNull(jsonObject.getString("status"));
        String isupdate = ObjectUtils.strParseNull(jsonObject.getString("template_code"));
        int pageno = ObjectUtils.strParseNull(jsonObject.getString("pageno")) == null ? 1 : jsonObject.getInteger("pageno");
        int pagesize = ObjectUtils.strParseNull(jsonObject.getString("pagesize")) == null ? 10 : jsonObject.getInteger("pagesize");

        LinkedHashMap<String, Object> map = new LinkedHashMap();
        map.put("name", "%"+name+"%");
        map.put("buildingnum", "%"+buildingnum+"%");
        map.put("frame_code", "%"+frame_code+"%");
        map.put("template_code", "%"+template_code+"%");
        map.put("layoutplancode", "%"+layoutplancode+"%");
        map.put("status", status);
        map.put("isupdate", isupdate);
        PageInfo info = new PageInfo();
        PageModel page = helloDao.getPageList(pageno, pagesize, map);
        info.setPageModel(page);
        return ObjectUtils.objectParseJsonStr(info);
    }

    @Override
    @DataSource(name = DataSource.mvp)
    @ExceptionHandle(ModuleName = HelloServiceImpl.class, Throwable = true)
    public Info getPageListForObject(String params) throws CoreException {
        JSONObject jsonObject = ObjectUtils.strParseJsonNode(params);
        String name = ObjectUtils.strParseNull(jsonObject.getString("name"));
        String buildingnum = ObjectUtils.strParseNull(jsonObject.getString("buildingnum"));
        String frame_code = ObjectUtils.strParseNull(jsonObject.getString("frame_code"));
        String template_code = ObjectUtils.strParseNull(jsonObject.getString("template_code"));
        String layoutplancode = ObjectUtils.strParseNull(jsonObject.getString("layoutplancode"));
        String status = ObjectUtils.strParseNull(jsonObject.getString("status"));
        String isupdate = ObjectUtils.strParseNull(jsonObject.getString("template_code"));

        int pageno = ObjectUtils.strParseNull(jsonObject.getString("pageno")) == null ? 1 : jsonObject.getInteger("pageno");
        int pagesize = ObjectUtils.strParseNull(jsonObject.getString("pagesize")) == null ? 10 : jsonObject.getInteger("pagesize");


        LinkedHashMap<String, Object> map = new LinkedHashMap();
        map.put("name", "%"+name+"%");
        map.put("buildingnum", "%"+buildingnum+"%");
        map.put("frame_code", "%"+frame_code+"%");
        map.put("template_code", "%"+template_code+"%");
        map.put("layoutplancode", "%"+layoutplancode+"%");
        map.put("status", status);
        map.put("isupdate", isupdate);
        Info info = new Info();
        PageModel<LayoutTemplateDTO> page = helloDao.getPageListForObject(pageno, pagesize, map);
        info.setPageModel(page);
        return info;
    }

    @Override
    @DataSource(name = DataSource.mvp)
    @ExceptionHandle(ModuleName = HelloServiceImpl.class, Throwable = true)
    public String getMoreList(String json) throws CoreException {
        Info info = new Info();
        JSONObject jnObject = ObjectUtils.strParseJsonNode(json);
        String startdate = ObjectUtils.strParseNull(jnObject.getString("startdate"));
        String enddate = ObjectUtils.strParseNull(jnObject.getString("enddate"));
        String loginid = ObjectUtils.strParseNull(jnObject.getString("loginid"));
        String status = ObjectUtils.strParseNull(jnObject.getString("status"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
        /*params.put("createdate0", startdate);
        params.put("createdate1", enddate);
        params.put("loginid", "%"+loginid+"%");
        params.put("status", status);*/

        params.put("product", null);
        params.put("project", null);

        List<Map<String, Object>> result = helloDao.getMoreList(params);
        info.setList(result);
        return ObjectUtils.objectParseJsonStr(info);
    }


    @Override
    @ExceptionHandle(ModuleName = HelloServiceImpl.class, Throwable = true)
    public String getMoreList2(String json) throws CoreException {
        Info info = new Info();
        JSONObject jnObject = ObjectUtils.strParseJsonNode(json);
        String subjectid = ObjectUtils.strParseNull(jnObject.getString("subjectid"));
        String plancode = ObjectUtils.strParseNull(jnObject.getString("plancode"));
        LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("subjectid", subjectid);
        params.put("plancode", plancode);
        List<Map<String, Object>> result = helloDao.getMoreList2(params);
        info.setList(result);
        return ObjectUtils.objectParseJsonStr(info);
    }

    public String otherPrint() throws CoreException {

        System.out.println("servie:"+helloDao.toString());
        String println = helloDao.otherPrint();
        helloDao.update();
        System.out.println(println);
        return "service hello---------"+println;
    }
}
