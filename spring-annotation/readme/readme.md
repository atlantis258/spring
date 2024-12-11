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



