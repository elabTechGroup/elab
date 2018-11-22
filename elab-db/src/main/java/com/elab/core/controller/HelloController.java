package com.elab.core.controller;

import com.elab.core.aop.annotations.ExceptionHandle;
import com.elab.core.bean.Info;
import com.elab.core.bean.PageInfo;
import com.elab.core.exception.CoreException;
import com.elab.core.models.demo.CustomerDTO;
import com.elab.core.service.IHelloService;
import com.elab.core.utils.AESForNodejs;
import com.elab.core.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.File;

/**
 * @author liuhx on 2016/12/8 17:05
 * @version V1.0
 * @email liuhx@elab-plus.com
 */
@Controller
public class HelloController extends BaseController {

    @Autowired
    private IHelloService helloService;

    @RequestMapping(value = "/objParam", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ExceptionHandle(ModuleName = HelloController.class)
    public @ResponseBody Info objParam(@RequestBody CustomerDTO obj) {
        return helloService.objParams(obj);
    }

    @RequestMapping(value = "/hello", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String countSubjectRequirementByKeyWord(@RequestBody String params) {
        try {
            String print = helloService.print();
            return print;
        } catch(CoreException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/getList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody Info getList(@RequestBody String params) {
        Info list = helloService.getList(params);
        return list;
    }

    @RequestMapping(value = "/getListForObject", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody Info getListForObject(@RequestBody String params) {
        Info list = helloService.getListForObject(params);
        return list;
    }

    @ExceptionHandle(ModuleName = HelloController.class)
    @RequestMapping(value = "/getPageList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String getPageList(@RequestBody String params) {
        try {
            System.out.println(super.getCurrentCustomerId());
            LogUtils.send(HelloController.class, "xxxxxx{}hhhhhhhh{}YYYYYYYYY{}TTTTTTTTTTT{}@!@!@@!", "张三", "王五", "李四", "赵六");
            LogUtils.send(HelloController.class, "啊啊士大夫进口红酒口味加入了可畏惧了圣诞节看的尔特人工费");
            String list = helloService.getPageList(params);
            return list;
        } catch(CoreException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @ExceptionHandle(ModuleName = HelloController.class)
    @RequestMapping(value = "/getPageListForObject", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody Info getPageListForObject(@RequestBody String params) {
        System.out.println(super.getCurrentCustomerId());
        Info list = helloService.getPageListForObject(params);
        return list;
    }

    @RequestMapping(value = "/getSingle", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ExceptionHandle(ModuleName = HelloController.class)
    public @ResponseBody String getSingle(@RequestBody String params) {
        String single = helloService.getSingle(params);
        return single;
    }

    @RequestMapping(value = "/getSingleForObject", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ExceptionHandle(ModuleName = HelloController.class)
    public @ResponseBody Info getSingleForObject(@RequestBody String params) {
        Info single = helloService.getSingleForObject(params);
        return single;
    }


    @RequestMapping(value = "/getMoreList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String getMoreList(@RequestBody String params) {
        try {
            String moreList = helloService.getMoreList(params);
            return moreList;
        } catch(CoreException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }


    @RequestMapping(value = "/getMoreList2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String getMoreList2(@RequestBody String params) {
        try {
            String moreList = helloService.getMoreList2(params);
            return moreList;
        } catch(CoreException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @ExceptionHandle(ModuleName = HelloController.class)
    @RequestMapping(value = "/insert", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody Info insert(@RequestBody String params) {
        Info insert = helloService.insert(params);
        return insert;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String update(@RequestBody String params) {
        try {
            String update = helloService.update(params);
            return update;
        } catch(CoreException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String delete(@RequestBody String params) {
        try {
            String delete = helloService.delete(params);
            return delete;
        } catch(CoreException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }


    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/demo2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String demo2(@RequestBody String params) {
        String url = "http://localhost:9090/skyforest/hello2/upload";
        String file = "E:\\soso\\96_outClub\\用户数据\\统计.sql";
        FileSystemResource resource = new FileSystemResource(new File(file));
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("jarFile", resource);
        param.add("fileName", "统计.sql");
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
//        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String,Object>>(param);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        return responseEntity.getBody();
    }

    public static void main(String[] args) throws Exception {
        String url = "http://localhost:9090/skyforest/hello2/upload";
        String file = "E:\\soso\\96_outClub\\用户数据\\统计.sql";
        RestTemplate rest = new RestTemplate();
//        List list = new ArrayList();
//        list.add(new StringHttpMessageConverter());
//        rest.setMessageConverters(list);
        FileSystemResource resource = new FileSystemResource(new File(file));
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("jarFile", resource);
        String encrypt = AESForNodejs.encrypt("统计.sql", "ACE4F440D45CE1CCF7CB702F");
        System.out.println(encrypt);
        param.add("fileName", encrypt);
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
//        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String,Object>>(param);
        ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.POST, httpEntity, String.class);
    }

}
