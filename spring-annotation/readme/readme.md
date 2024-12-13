# 第一章 注解基础概念

## 1.什么是注解

```
指的是在类或者方法上加入特定的注解（@XXX），完成特定功能的开发。
@Component
public class XXX{}
```



## 2.为什么要讲解注解编程

```
1.注解开发方便
	代码简洁开发速度大大提高
2.Spring开发潮流
	Spring2.x引入注解Spring3.x完善注解SpringBoot普及推广注解编程
```



## 3.注解的作用

1.替换XML这种配置形式，简化配置

![image-20241210221736195](D:\github\atlantis258\spring\spring-annotation\readme\1.png)

2.替换接口，实现调用双方的契约性

```
通过注解的方式，在功能调用者和功能提供者之间达成约定，进而进行功能的调用。因为注解应用更为方便灵活，所以在现在的开发中，更推荐通过注解的形式，完成依赖注入。
```

![image-20241210221651817](D:\github\atlantis258\spring\spring-annotation\readme\2.png)



## 4.Spring注解的发展历程

```
1.Spring2.×开始支持注解编程@Component @service @Scope..
	目的：提供的这些注解只是为了在某些情况下简化XML的配置，作为XML开发的有益补充。
2.Spring3.x @Configuration @Bean.
	目的：彻底替换XML，基于纯注解编程
3.Spring4.×	SpringBoot
	提倡使用注解常见开发
```



## 5.Spring注解开发的·一个问题

```
Spring基于注解进行配置后，还能否解耦合呢？

在Spring框架应用注解时，如果对注解配置的内容不满意，可以通过Spring配置文件进行覆盖的。
```



# 第二章 Spring的基础注解（Spring2.x）

```
这个阶段的注解，仅仅是简化XML的配置，并不能完全替代XML
```

## 1.对象创建相关注解

搭建开发环境

```
<context:component-scan base-package="com.atlantis"/>

作用：让Spring框架在设置包及其子包中扫描对应的注解，使其生效。
```

对象创建相关注解

- @Component

```markdown
作用：替换原有spring配置文件中的<bean标签
注意：
	id属性 component注解 提供了默认的设置方式 单词首字母小写
	class属性 通过反射获得class内容
```

![image-20241210224421733](D:\github\atlantis258\spring\spring-annotation\readme\3.png)

@Component细节
如何显示指定工厂创建对象的id值

```
@Component("u")
```

Spring配置文件覆盖注解配置内容

```
applicationContext.xml

<bean id="u" class=com.atlantis.entity.User"/>

id值 class的值 要和 注解中的设置保持一致
```

- @Component的衍生注解

```
@Repository
@Service
@Controller

注意：本质上这些衍生注解就是@Component作用细节用法都是完全一致
	作用 <bean>
	细节 @Service("s")

目的：更加准确的表达一个类型的作用
```

- @Scope注解

```
作用：控制简单对象的创建次数
注意：不添加@Scope Spring提供默认值singleton

<bean id="" class="" scope="singleton|prototype"/>
```

- @Lazy注解

```
作用：延迟创建单例对象
注意：一旦使用了@Lazy注解后，Spring会在使用这个对象时候，进行这个对象的创建

<bean id="" class="" lazy="false"/>
```

- 生命周期方法相关注解

```
1.初始化相关方法 @Postconstruct
InitializingBean
<bean init-method=""/>
2.销毁方法 @PreDestroy
DisposableBean
<bean destory-method=""/>
注意：1.上述的2个注解并不是Spring提供的，JSR（JavaEE规范）520
	 2.再一次的验证，通过注解实现了接口的契约性
```



## 2.注入相关注解

- 用户自定义类型 @Autowired

![image-20241211225738816](D:\github\atlantis258\spring\spring-annotation\readme\4.png)

```
@Autowired细节
1.Autowired注解基于类型进行注入
	基于类型的注入：注入对象的类型 必须与目标成员变量类型相同或者是其子类（实现类）
2.Autowired Qualifier 基于名字进行注入
	基于名字的注入：注入对象的id值，必须与Qualifier注解中设置的名字相同
3.Autowired注解放置位置
	a)放置在对应成员变量的set方法上
	b)直接把这个注解放置在成员变量之上，Spring通过反射直接对成员变量进行注入（赋值）
4.JavaEE规范中类似功能的注解
	JSR250 @Resouce(name="userDAOImp1")基于名字进行注入
	@Autowired()
	@Qualifier("userDAOImpl")
	注意：如果在应用Resource注解时，名字没有配对成功，那么他会继续按照类型进行注入。
	JSR330 @Inject作用@Autowired完全一致 基于类型进行注入---》EJB3.0
    <dependency>
        <groupId>javax.inject</groupId>
        <artifactId>javax.inject</artifactId>
        <version>1</version>
    </dependency>
```

- JDK类型

```
@Value注解完成
1.设置xxx.properties
	id=10
	name=suns
2.Spring的工厂读取这个配置文件
	<context:property-placeholder location=""/>
3.代码
	属性@Value("${key}")
```

@PropertySource

```
1.作用：用于替换Spring配置文件中的<context:property-placeholder location=""/>标签
2.开发步骤
	1.设置xxx.properties
        id=10
        name=suns
	2.应用@PropertySource
	3.代码
		@Value
```

@Value注解使用细节

1.@value注解不能应用在静态成员变量上

```
如果应用，赋值（注入）失败
```

2.@Value注解+Properties这种方式，不能注入集合类型

```
Spring提供新的配置形式YAMLYML（SpringBoot）
```



## 3.注解扫描详解

```
<context:component-scan base-package="com.atlantis"/>
当前包及其子包
```

1.排除方式

```
context:component-scan base-package="com.atlantis">
<context:exclude-filter type="" expression=""/>
    type：assignable：排除特定的类型不进行扫描
    annotation：排除特定的注解不进行扫描
    aspectj:切入点表达式
        包切入点：com.atlantis.entity..
        类切入点：*..User
    regex：正则表达式
    custom：自定义排除策略框架底层开发
</context：component-scan>

排除策略可以叠加使用
<context:component-scan base-package="com.atlantis">
<context:exclude-filter type="assignable" expression="com.atlantis.entity.User"/>
<context:exclude-filter type="aspectj" ekpression="com.atlantis.injection..*"/>
</context:component-scan>
```

2.包含方式

```
<context:component-scan base-package=com.atlantis" use-default-filters="false">
	<context:include-filter type="" expression=""/>
</context:component-scan>

1.use-default-filters="false"
	作用：让Spring默认的注解扫描方式失效。
2.<context:include-filter type="" expression=""/>
	作用：指定扫描那些注解
	type：assignable：排除特定的类型不进行扫描
        annotation：排除特定的注解不进行扫描
        aspectj：切入点表达式
            包切入点：com.atlantis.entity..*
            类切入点：*..User
        regex:正则表达式
        custom：自定义排除策略框架底层开发
        
包含的方式支持叠加
<context:component-scan base-package="com.atlantis" use-default-filters="false">
    <context:include-filter type="annotation" expression="org.springframework.stereotype.Repository"/>
    <context:include-filter type=”annotation" expression=”org.springframework.stereotype.Service"/>
</context:component-scan>
```



## 4.对于注解开发的思考

1.配置互通

```
Spring注解配置配置文件的配置互通
@Repository
public class UserDAOImpl {

}
public class UserServiceImpl {
	private UserDAO userDAO;
	set get
}

<bean id="userService" class="com.baizhiedu.UserServiceImpl">
	<property name="userDAO" ref="userDAOImp1"/>
</bean>
```

2.什么情况下使用注解？什么情况下使用配置文件？

```
@Component替换<bean>

基础注解（@Component @Autowired @value）程序员开发类型的配置
1，在程序员开发的类型上可以加入对应注解进行对象的创建
User UserService  UserDAO
2.应用其他非程序员开发的类型时，还是需要使用<bean>进行配置
SqlSessionFactoryBean MapperScannerConfigure
```



5.SSM整合开发（半注解开发）

省略 [173_SSM整合半注解版_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1Zw41177B7?spm_id_from=333.788.videopod.episodes&vd_source=ae4dac78c13d73ce3caade6a9b8e2914&p=173) 



# 第三章 Spring的高级注解（Spring3.x及以上）

## 1.配置Bean

```
Spring在3.X提供的新的注解，用于替换XML配置文件。

@Configuration
public class AppConfig {

}
```

1.配置Bean在应用的过程中替换了XML具体什么内容呢？

![image-20241213234154433](D:\github\atlantis258\spring\spring-annotation\readme\5.png)

2.AnnotationConfigApplicationContext

```
1.创建工厂代码
ApplicationContext ctx =new AnnotationConfigApplicationContext();
2.指定配置文件
1.指定配置bean的class
ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
2.指定配置bean所在的路径
ApplicationContext ctx =new AnnotationConfigApplicationContext("com.atlantis.config");
```

配置Bean开发的细节分析

- 基于注解开发使用日志

```
1.不能集成Log4j
2.集成logback
```

- 引入相关jar

```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.25</version>
</dependency>
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>jcl-over-slf4j</artifactId>
    <version>1.7.25</version>
</dependency>
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.2.3</version>
</dependency>
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-core</artifactId>
    <version>1.2.3</version>
</dependency>
```

- 引入logback配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!--控制台输出-->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <!--格式化输出：%d表示日期，%thread表示线程名，%-51evel：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
        </encoder>
    </appender>
    <root level="DEBUG">
        <appender-ref ref="STDOUT" />
    </root>
</configuration>
```

@Configuration注解的本质

```
本质：也是@Component注解的衍生注解

可以应用<context:component-scan>进行扫描
```



## 2.@Bean注解

```
@Bean注解在配置bean中进行使用，等同于XML配置文件中的<bean>标签
```

### 1.@Bean注解的基本使用

![image-20241213235739966](D:\github\atlantis258\spring\spring-annotation\readme\6.png)

对象的创建

```
1.简单对象
直接能够通过new方式创建的对象
    User UserService UserDAO

2.复杂对象
不能通过new的方式直接创建的对象
	Connection SqlSessionFactory
```

@Bean注解创建复杂对象的注意事项

```java
public class ConnectionFactoryBean implements FactoryBean<Connection> {
    @Override
    public Connection getObject() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/spring_mybatis?useSSL=false", "root", "root");
        return conn;
    }

    @Override
    public Class<?> getObjectType() {
        return Connection.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}

// 遗留系统整合
@Bean
public Connection conn2() {
    Connection conn2 = null;
    try {
        ConnectionFactoryBean factoryBean = new ConnectionFactoryBean();
        conn2 = factoryBean.getObject();
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
    return conn2;
}
```

自定义id值

```
@Bean("id")
```

控制对象创建次数

![image-20241214001627356](D:\github\atlantis258\spring\spring-annotation\readme\7.png)

```java
@Bean
@Scope("singleton|prototype")
public User user() {
	return new User();
}
```

### 2.@Bean注解的注入

- 用户自定义类型的注入

```java
@Configuration
public class AppConfig2 {

    @Bean
    public UserDAO userDAO() {
        return new UserDAOImpl();
    }

    @Bean
    public UserService userService(UserDAO userDAO) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDAO(userDAO);
        return userService;
    }

    /**
     * 简化写法
     * @return
     */
    @Bean
    public UserService userService() {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDAO(userDAO());
        return userService;
    }
}
```

- JDK类型的注入

```java
@Bean
public Customer customer() {
    Customer customer = new Customer();
    customer.setId(1);
    customer.setName("nihao");
    return customer;
}
```

JDK类型注入的细节分析

```java
如果直接在代码中进行set方法的调用，会存在耦合的问题

@Configuration
@PropertySource("classpath:/init.properties")
public class AppConfig2 {

    @Value("${id}")
    private Integer id;

    @Value("${name}")
    private String name;

    @Bean
    public Customer customer() {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        return customer;
    }
}
```



## 3.@ComponentScan注解

```
@ComponentScan注解在配置bean中进行使用，等同于XML配置文件中的<context:component-scan>标签

目的：进行相关注解的扫描@Component	@Value,,,@Autowired
```

1.基本使用

```java
@Configuration
@ComponentScan(basePackages = "com.atlantis.config.scan")
public class AppConfig3 {

}

等价于：
<context:component-scan base-package="com.atlantis.config.scan"/>
```

2.排除、包含的使用

- 排除：

```xml
<context:component-scan base-package="com.atlantis">
	<context:exclude-filter type="annotation" expression="org.springframework.stereotype.Service"/>
</context:component-scan>
```

```java
@Configuration
@ComponentScan(basePackages = "com.atlantis.config.scan",
excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Service.class}),
        @ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = {"*..User1"})
})
public class AppConfig3 {

}


type = FilterType.ANNOTATION		value
				.ASSIGNABLE_TYPE	value
				.ASPECTJ			pattern
				.REGEX				pattern
				.CUSTOM				Value
```

- 包含

```xml
<context:component-scan base-package="com.atlantis" use-default-filters="false">
	<context:include-filter type="annotation" expression="org.springframework.stereotype.Service"/>
</context:component-scan>
```

```java
@Configuration
@ComponentScan(basePackages = "com.atlantis.config.scan", useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Service.class}),
//                @ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = {"*..User1"})
        })
//@ImportResource("applicationContext.xml")
public class AppConfig3 {

}
```



## 4.Spring工厂创建对象的多种配置方式

1.多种配置方式的应用场景

![image-20241214010441794](D:\github\atlantis258\spring\spring-annotation\readme\8.png)

2.配置优先级

```
@Component及其衍生注解  <  @Bean  <  配置文件bean标签
优先级高的配置覆盖优先级低配置
@Component
public class User{
}

@Bean
public User user(){
	return new User()
}

<bean id="user”class="xXX.User"/>

配置覆盖：id值 保持一致
```

3.解决基于注解进行配置的耦合问题

利用配置优先级特性解决。



## 5.整合多个配置信息

1.为什么会有多个配置信息

```
拆分多个配置bean的开发，是一种模块化开发的形式，也体现了面向对象各司其职的设计思想
```

2.多配置信息整合的方式

```
多个配置Bean的整合
配置Bean与@Component相关注解的整命
配置Bean与SpringXML配置文件的整合
```

3.整合多种配置需要关注那些要点

```
如何使多配置的信息汇总成一个整体
如何实现跨配置的注入
```

多配置的信息汇总

- base-package进行多个配置Bean的整合

![image-20241214012456050](D:\github\atlantis258\spring\spring-annotation\readme\9.png)

- @Import

```
1.可以创建对象
2.多配置bean的整合
```

![image-20241214012653000](D:\github\atlantis258\spring\spring-annotation\readme\10.png)

- 在工厂创建时，指定多个配置Bean的Class对象

```
ApplicationContext ctx=new AnnotationConfigApplicationContext(Appeonfiq.class，AppConfig2.class);
```



跨配置进行注入

@Autowired解决



2.配置Bean与@Component相关注解的整合

略



3.配置Bean与配置文件整合

```
1.遗留系统的整合
2.配置覆盖
```



## 6.配置Bean底层实现原理

```
Spring在配置Bean中加入了@Configuration注解后底层就会通过Cglib的代理方式，来进行对象相关的配置、处理
```

![image-20241214014904299](D:\github\atlantis258\spring\spring-annotation\readme\12.png)

![image-20241214014738063](D:\github\atlantis258\spring\spring-annotation\readme\11.png)



## 7.四维一体的开发思想

1.什么是四维一体

```
Spring开发一个功能的4种形式，虽然开发方式不同，但是最终效果是一样的。
1.基于schema
2.基于特定功能注解
3.基于原始<bean>
4.基于@Bean注解
```

2.四维一体的开发案例

```
1.<context：property-placehoder>
2.@PropertySource（推荐）
3.<bean id=" class="PropertySourcePlaceholderConfigure"/>
4.@Bean【推荐】
```



## 8.纯注解版本AOP开发

1.搭建环境

```
1.应用配置Bean
2.注解扫描
```

2.开发步骤

```
1.原始对象
@Service(@Component)
public class UserServiceImpl implements UserService {

}

2.创建切面类（额外功能切入点组装切面）
@Aspect
@Component
public class MyAspect {
    @Around("execution（*1ogin(..))")
    public Object arround(ProceedingJoinPoint joinPoint) throws Throwable 
    System.out·printin(----aspect 1og----");
    Object ret= joinPoint.proceed();
    return ret;
}

3.Spring的配置文件中
<aop:aspectj-autoproxy/>
@EnableAspectjAutoProxy  ---->  配置Bean
```

3.注解AOP细节分析

```
1.代理创建方式的切换 JDK Cglib
<aop:aspectj-autoproxy proxy-target-class=true|false>

@EnableAspectJAutoProxy(proxyTargetClass = true)

2.SpringBoot AOP的开发方式
@EnableAspectjAutoProxy 已经设置好了。
```

![image-20241214021618952](D:\github\atlantis258\spring\spring-annotation\readme\13.png)



Spring	AOP代理默认实现JDK

SpringBoot 	AOP 代理默认实现Cglib