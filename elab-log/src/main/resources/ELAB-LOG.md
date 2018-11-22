## CAT日志使用介绍

### 配置环境
1. 加载CAT配置  
  `在resource目录下创建文件夹META-INF` 
  然后创建一个`app.properties`文件里面定义`app.name=cat-demo`,后面是应用的名称

2. 加载配置 [xml]  

```xml
<dependency>
    <groupId>com.elab.log</groupId>
    <artifactId>elab-log</artifactId>
    <version>1.0</version>
</dependency>
```
  2.1 spring配置
```xml
<aop:aspectj-autoproxy></aop:aspectj-autoproxy>

<!-- 定义一个bean -->
<bean id="catAspect" class="com.elab.log.asepct.CatAspect">
</bean>

<!-- 加载一个切入点 -->
<aop:config>
   <aop:aspect id="myAspect" ref="myInterceptor">
            <aop:pointcut  id="catPoint"  expression="execution(* com.elab.cat.catdemo.service..*(..))" />
            <aop:around pointcut-ref="catPoint" method="aroundMethod" />
   </aop:aspect>
</aop:config>
```
2.2 log4j配置
```tex
log4j.rootLogger=info, Console ,cat
## 控制台
log4j.appender.Console=org.apache.log4j.ConsoleAppender
log4j.appender.Console.layout=org.apache.log4j.PatternLayout
log4j.appender.Console.layout.ConversionPattern=%d [%t] %-5p [%c] - %m%n
## 这个配置很重要,集成了CAT的日志
log4j.appender.cat=com.elab.log.log4j.CatExtLog
log4j.appender.cat.Threshold = INFO
log4j.appender.cat.layout=org.apache.log4j.PatternLayout
log4j.appender.cat.layout.ConversionPattern=%p: [%d{yy/MM/dd HH:mm:ss}][%C-%M] -%m%n
## sql语句
log4j.logger.java.sql.Connection=DEBUG
log4j.logger.java.sql.Statement=DEBUG
## 包下面的类的打印级别
log4j.logger.org=ERROR
log4j.logger.org.springframework=ERROR
log4j.logger.com.x.jdbc=DEBUG
```

3. 应用中植入CAT日志
```java

// 建议在方法上加入
//username 为作者名称
@ExceptionHandle(username = "liukx",ModuleName=TestServiceImpl.class)

protected final Log logger = LogFactory.getLog(this.getClass());

// 下面两种日志会输出到Event模块下
// 打印info日志
logger.info("xxx");
// 打印debug日志
logger.info("ggg");
// 打印错误日志 => 错误日志会出现在Problem
logger.error("lll");
```



4. **需要注意**
   1. 应用中需要根据自己的需求将对应的`logger.info`打印出来
   2. **如何查看对应的CAT在当前的哪个环境下?**
      1. 到你当前项目的文件夹的根目录下,比如我:E:\data\appdatas\cat\client.xml
      2. 里面有对应的路径地址,表明当前项目的日志会上传到这个对应的CAT下面
   3. **如何查看对应的错误?**
      1. 登录CAT的web查询页面
      2. 选择对应的项目
      3. 点击Problem页面查看