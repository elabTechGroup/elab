# elab
框架采用SpringMVC+Spring的技术进行搭建，数据库采用Mysql，数据源采用Druid，db操作使用SpringJDBC


# 后端框架说明书
##  文件变更记录
![](http://oub49vfnw.bkt.clouddn.com//file/2018/04/619879fd7d5d46e1b15cb7bbba7e1c0b_image.png)

### 1、框架介绍
	本框架项目由elab后端技术团队出品，在查看了市面上大部分的java服务端框架，经内部讨论：
	        由于要兼容playframworker技术体系，但是又不能偏离太多市场上的技术点；
		故采用spring作为底层框架，结合springMVC和我们自己对jdbc的一些理解和处理；
		推出的一款简便的，直观的基于restfull的小型服务架构。
		同时也为了将来更好的扩容成微服务架构系统，依赖于spring4.x
		
### 2、框架基础目录结构

	Project-service
	  |—— com.elab.项目
		  |—— controllers 
		  |—— services
		  |—— daos
		  |—— enums
		  |—— models
		  |—— utils
	以上这些是固定目录，模块再在对应的包下建立模块包！
	


### 3、采用技术

	框架采用SpringMVC+Spring的技术进行搭建，数据库采用Mysql，数据源采用Druid，db操作使用SpringJDBC；
	
### 4、运行方式

	项目启动方式采用轻量级启动，采用maven插件jetty来启动，默认8080端口，如需要热加载请看如下配置：
	jetty热部署注意事项：
	启动事项：jetty:run -Djetty.reload=automatic -Djetty.scanIntervalSeconds=3
	当修改了java文件时，在IntelliJ中按：‘Ctrl+Shift+F9’ 将重新编译该java文件，
	如果修改了多个java文件，按‘Ctrl+F9’ 可以重新编译整个工程。
	访问地址：[http://localhost:8080/elab](http://localhost:8080/elab)
	8080端口的来源和elab的来源就是在pom.xml中的配置，具体如下：
	
```xml

<build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>${java-version}</source>
                    <target>${java-version}</target>
                </configuration>
            </plugin>

            <plugin>
                <groupId>org.eclipse.jetty</groupId>
                <artifactId>jetty-maven-plugin</artifactId>
                <version>9.4.5.v20170502</version>
                <configuration>
                    <stopPort>9982</stopPort>
                    <stopKey>foo2</stopKey>
                    <scanIntervalSeconds>0</scanIntervalSeconds>

                    <httpConnector>
                        <port>8080</port>
                    </httpConnector>
                    <webAppConfig>
                        <webInfIncludeJarPattern>.*/spring-[^/]*\.jar$</webInfIncludeJarPattern>

                        <contextPath>/elab</contextPath>
                    </webAppConfig>
                </configuration>
            </plugin>
            <plugin>
                <artifactId>maven-war-plugin</artifactId>
                <version>2.1.1</version>
                <configuration>
                    <packagingExcludes>WEB-INF/web.xml</packagingExcludes>
                    <warName>${profiles.active}</warName>
                </configuration>
            </plugin>

        </plugins>
    </build>

```


### 5、框架规范

#### 5-1 dao层class的写法：

```java

public interface IHelloDao {


   PageModel pageList(PageModel pageModel, LinkedHashMap, Object> params) throws CoreException;

   int insert(LinkedHashMap, Object> params) throws CoreException;

   int update(LinkedHashMap, Object> params) throws CoreException;

   int delete(LinkedHashMap, Object> params) throws CoreException;
}



```


```java


@Repository("helloDao")
public class HelloDaoImpl extends BaseDao implements IHelloDao {


  @Override
  public int insert(LinkedHashMap, Object> params) throws CoreException {
	return executeUpdate("hello.insert", params);
  }
  
  @Override
  public int update(LinkedHashMap, Object> params) throws CoreException {
	return executeUpdate("hello.update", params);
  }
  
  @Override
  public int delete(LinkedHashMap, Object> params) throws CoreException {
	return executeUpdate("hello.delete", params);
  }
  
  @Override
  public PageModel pageList(PageModel pageModel, LinkedHashMap, Object> params) throws CoreExceptio {
	return findPagingList(pageModel, "hello.pageList", params);
  }
  
}


```
`hello`.list和`hello`.single和`hello`.pageList和`hello`.insert和`hello`.update和`hello`.delete，这几个字符串的来源是在来源如下`hello`-sql.xml中,标记为红色的字体必须是一模一样；

原则上`hello`.XXX中的hello应该是`hello`-sql.xml，举例：`user`.detail，那么对应的sql文件名称应该是`user`-sql.xml（`必须遵守`）；

hello来源于如下xml中的sqlGroup中的属性name，

list和single和pageList和insert和update和delete来源于如下xml中的SQLGroup下的子属性sql元素中的id属性，在BaseDao中会去resource目录下的sql目录下寻找hello-sql.xml文件中的对应的sql语句；

![](http://oub49vfnw.bkt.clouddn.com//file/2018/04/6272a73e3bea4c738f2a2993683f80d3_image.png)


#### 5-2 xxx-sql.xml 规范

##### 5-2-1 查询（select）

	1、所有where后面不能换行，有内容必须紧跟着；例如：select a from table where b=1
	2、<号需要用转义符号替换 <因为这个小于号和xml中的尖括号冲突了
	3、所有子查询的语句，如果有可变条件查询，则子查询中的可变条件必须是要有值，不能为null
	4、如果涉及多张表多字段展示，但是又是多必要条件查询，则最好将该查询语句写成视图，用视图代替复杂SQL，这样出错几率小
	5、查询参数如有多个，则条件查询的参数必须按照顺序来传值，例如：select a from table where b=:b and c=:c and d=:d ,参数顺序必须是b,c,d
	6、in查询方式，需要使用List的方式进行替换；代码如下：
		Select * from table where name in (:name)
		具体调用的service中：
		List name = Arrays.asList(“数组”);
		Map.put(“name”,  name);
	7、自定义排序写法采用参数替换法orderby，传参map中传：
		Select id from table g order by :orderby
		LinkedHashMap paramsMap = new LinkedHashMap();
		paramsMap.put("orderby","g.followCreated desc");
	8、自定义分组写法采用参数替换法groupby，传参map中传
		Select id from table g group by :groupby
		LinkedHashMap paramsMap = new LinkedHashMap();
		paramsMap.put("groupby","g.mobile");
##### 5-2-2 新增（insert）
	1、传参的顺序必须和你insert中的语句字段顺序保持一致，并且个数也需要保持一致；例如：insert into table(a,b,c,d) values(:a,:b,:c,:d)
	2、传参内容不需要关心是否有值，不管有没有值，按照顺序直接传进去即可；
	
##### 5-2-3 修改（update）

	1、where 后面的内容不能用in和大于（>）小于（<）之类的特殊用法，只支持普通的等号（=）；
	2、基于遵守1的前提下，传参内容不需要关心是否有值，不管有没有值，不需要按照顺序传值，随便传值；例如：update table set a=:a,d=:d,c=:c where b=:b；
	3、update语句的where后面必须带有一个条件，否则程序会直接报错
	4、如果update中的参数值不想修改，但是语句想用同一条（比如：update table set name=:name, sex=:sex, status=:status where id=:id）,该语句中参数是name、sex、status、id，我可以直接只修改其中的status字段，直接传map对应的key是status，就可以修改，其他2个（name和sex）不传入map中即可达到统一update语句多次重复使用。代码如下：
	
![](http://oub49vfnw.bkt.clouddn.com//file/2018/04/8e5b8593e11d4658a2d9297d81e9887e_image.png)

![](http://oub49vfnw.bkt.clouddn.com//file/2018/04/4ad1710327f643f1a67b8a0df8a295be_image.png)


##### 5-2-4 删除（delete）	
	1、介于删除操作的危险性，故不替换任何null的参数，sql中有多少条件，则传值必须传相应的多少参数；
	例如：delete from table where a=:a 
	
### 6 service层的写法及规范

```java	
	
@Service
public class AgoraServiceImpl implements IAgoraService {

  @Autowired
private IAgoraDao agoraDao;

/**
*
*注释内容
***/
@Override
@Transactional
@DataSource(name = DataSource.mvp)
@ExceptionHandle(username="liuhx", ModuleName = AgoraServiceImpl.class, Throwable = true)
public Info publish(AgoraRequest agoraRequest) throws CoreException {
	Info info = new Info();
	LinkedHashMap agoraMap = new LinkedHashMap();
	agoraMap.put("publisher", agoraRequest.getUsername());
	agoraMap.put("receiver", agoraRequest.getSaler());
	agoraMap.put("channel", agoraRequest.getChannel());
	agoraMap.put("channel_key", key);
	agoraMap.put("nickname", nickname);
	agoraMap.put("customerhead", customerhead);
	agoraMap.put("sex", sex);
	agoraMap.put("publisher_id", agoraRequest.getUserid());
	agoraMap.put("signalIsOnline", agoraRequest.getSignalIsOnline());
	agoraMap.put("houseid", agoraRequest.getHouseid());
	int id = agoraDao.save(agoraMap);
	info.setId(id);
	return info;
}

……


```
#### 6-1 多数据源支持

	Service是支持多数据源的，如果需要多数据源操作，则在对应的方法上面加上注解@DataSource，写上对应的数据库名称；
	前提是在一个方法内部不能同时操作2个库，如有这种多库操作的需求，则需要写上单独方法；
	
#### 6-2 缓存支持

	Service是支持方法级别的缓存的，目前Redis，如果对某个查询需要做缓存处理，扩展了Spring的缓存使用，使之可以用spring的注解来支持我们需要的缓存；

	@Cacheable：加入缓存的注解

	@CachePut：更新缓存内容的注解

	@CacheEvict：删除缓存内容的注解

![](http://oub49vfnw.bkt.clouddn.com//file/2018/04/a099767770164d298be385ed5eb9ec49_image.png)

![](http://oub49vfnw.bkt.clouddn.com//file/2018/04/66ca143d737f45e28a9f2080e1c0f306_image.png)


`注意事项`：
	 LinkedHashMap中的参数的顺序必须和sql中需要替换的参数顺序必须保持一致；
	 返回值必须是对象封装成json字符串
	 
	 
### 7 Controller层的写法以及规范	 
	 
	在controller的类上加上@Controller注解；
	类级别的@RequestMapping注解必须加，value的值为具体模块名称，统一小写；
	方法级别的@RequestMapping注解必须加，其中produces属性必须加上，并且要指明数据传输的格式和编码；
	
```java		
	
@Controller
@RequestMapping(value="/hello")
public class HelloController {

    @Autowired
  private IHelloService helloService;

    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody Info countSubjectRequirementByKeyWord(@RequestBody HelloRequest params) {
	Info print = helloService.print();
	return print;
  }	
}	
```

### 8 日志记录
	日志记录和正常的log4j用法一样，只不过封装了一个固定的类，支持字符替换，以下两种写法均可，**原则上只记录重要逻辑业务日志**；
	现有日志不变，还是便于开发定位问题，接入CAT日志系统，主要用于生产环境中的日志查看，问题分析，性能瓶颈分析用。不影响框架本身。
	日志记录方式更新，采用log4J的方式，把对应的慢查询、info信息和error异常信息都单独区分，把log日志信息集成到CAT可视化平台，供查错误和定位问题，正常log4J的使用即可；
### 9 其他配置文件说明

##### global-config.xml

文件定义，全局配置文件：

配置框架的日志，开发环境、测试环境、堡垒环境、生产环境对应日志的存储媒介配置；以及对应的日志级别设置；

配置框架的缓存，开发环境、测试环境、堡垒环境、生产环境对应日志的存储媒介配置；存储内容中还是存储文件中，还是存储数据库中等等；

系统参数配置，采用map对象的形式，体现在key和value的存储方式上；

alreadyAdded_BeiXuan对应在global-config.xml中配置的key;配置如下：
``` xml
<global version="0.1">
    <settings>
<property name="alreadyAdded_BeiXuan" value="已经加入备选"/>
</settings>
</global>
```



获取如下：

根据key获取对应的值：
	ConfigManager.getProperty("alreadyAdded_BeiXuan")





