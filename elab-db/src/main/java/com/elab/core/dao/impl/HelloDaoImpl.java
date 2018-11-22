package com.elab.core.dao.impl;

import com.elab.core.bean.PageModel;
import com.elab.core.dao.BaseDao;
import com.elab.core.dao.IHelloDao;
import com.elab.core.exception.CoreException;
import com.elab.core.models.demo.CustomerDTO;
import com.elab.core.models.demo.EnumerationDemo;
import com.elab.core.models.demo.HelloDTO;
import com.elab.core.models.demo.LayoutTemplateDTO;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuhx on 2016/12/8 15:54
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
@Component
public class HelloDaoImpl extends BaseDao implements IHelloDao {

//    @Autowired
//    public JdbcTemplate jdbcTemplate;

    @Override
    public String print() throws CoreException {
        System.out.println("jdbcTemplate:"+jdbcTemplate.toString());
        int count;
        count = jdbcTemplate.queryForInt("select count(1) FROM users");
        jdbcTemplate.execute("update enumeration set lasterupdate=sysdate() where id=1");
        return "print core dao----users----"+count;
    }


    @Override
    public String otherPrint() throws CoreException {
        System.out.println("jdbcTemplate:"+jdbcTemplate.toString());
        int count = jdbcTemplate.queryForInt("select count(1) FROM t_house_product");
//        jdbcTemplate.execute("update t_house_product set created=sysdate() where id=1");
        return "print core dao----t_house_product ----"+count;
    }

    @Override
    public void update() throws CoreException {
        jdbcTemplate.execute("update t_house_product set created=sysdate() where id=1");
    }

    @Override
    public int update(EnumerationDemo demo) throws CoreException {
        return super.executeUpdate("hello.updateObject", demo);
    }


    @Override
    public PageModel getPageList(int pageNo, int pageSize, LinkedHashMap<String, Object> params) throws CoreException {
        PageModel pageModel = new PageModel(1,pageNo,pageSize);
        return findPagingList(pageModel, "hello.template", params);
    }

    @Override
    public PageModel<LayoutTemplateDTO> getPageListForObject(int pageNo, int pageSize, LinkedHashMap<String, Object> params) throws CoreException {
        PageModel pageModel = new PageModel(1,pageNo,pageSize);
        return super.findPagingList(pageModel, "hello.template", params, LayoutTemplateDTO.class);
    }

    @Override
    public List<Map<String, Object>> getList(LinkedHashMap<String, Object> params) throws CoreException {
        List<Map<String, Object>> result = findList("hello.demo2", params);
        return result;
    }

    @Override
    public List<CustomerDTO> getListForObject(LinkedHashMap<String, Object> params) throws CoreException {
        List<CustomerDTO> helloDTOs = findList("hello.list", params, CustomerDTO.class);
        return helloDTOs;
    }


    @Override
    public Map<String, Object> getSingle(LinkedHashMap<String, Object> params) throws CoreException {
        Map<String, Object> sqlRow = findUnique("hello.single", params);
        return sqlRow;
    }

    @Override
    public HelloDTO getSingleForObject(LinkedHashMap<String, Object> params) throws CoreException {
        return findUnique("hello.single", params, HelloDTO.class);
    }


    @Override
    public int insert(LinkedHashMap<String, Object> params) throws CoreException {
        int id = this.executeInsert("hello.insert", params);
        return id;
    }

    @Override
    public int update(LinkedHashMap<String, Object> params) throws CoreException {
        int count = this.executeUpdate("hello.update", params);
        return count;
    }

    @Override
    public int delete(LinkedHashMap<String, Object> params) throws CoreException {
        int count = this.executeDelete("hello.delete", params);
        return count;
    }


    @Override
    public List<Map<String, Object>> getMoreList(LinkedHashMap<String, Object> params) throws CoreException {
        List<Map<String, Object>> result = findList("hello.moreList", params);
        return result;
    }

    @Override
    public List<Map<String, Object>> getMoreList2(LinkedHashMap<String, Object> params) throws CoreException {
        List<Map<String, Object>> result = findList("hello.moreList2", params);
        return result;
    }
}
