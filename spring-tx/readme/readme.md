# 第一章 Spring的事务

## 1.什么是事务？

保证业务操作完整性的一种数据库机制
事务的4特点：ACID

A 原子性
C 一致性
I 隔离性
D 持久性



## 2.如何控制事务

```
JDBC:
    Connection.setAutoCommit(false）;
    Connection.commit();
    Connection.rollback();

Mybatis：
    Mybatis自动开启事务
    sqlSession(Connection).commit();
    sqlSession(Connection).rollback();
结论：控制事务的底层都是Connection对象完成的。
```



## 3.Spring控制事务的开发

1.原始对象

```java
public class XXXUserServiceImpl {
    private XXXDAO XXXDAO
    set get
    1.原始对象---》原始方法---》核心功能（业务处理+DAO调用）
    2.DAO作为Service的成员变量，依赖注入的方式进行赋值
}
```

2.额外功能

```java
1.org.springframework·jdbc.datasource.DataSourceTransactionManager 会提供 如下功能：
2.注入DataSource

MethodInterceptor
public Object invoke(MethodInvocation invocation）{
    try {
        Connection.setAutoCommit(false);
        Object ret = invocation·proceed();
        Connection.commit();
    } catch(Exception e）{
        Connection.rollback();
    }
    return ret;                  
}
2.@Aspect
@Around
```

3.切入点

```java
@Transactional
事务的额外功能加入给那些业务方法
1.类上：类中所有的方法都会加入事务
2.方法上：这个方法会加入事务
```

4.组装切面

```markdown
1.切入点
2.额外功能
<tx:annotation-driven transaction-manager=""/>
```



## 4.Spring控制事务的编码

1.搭建开发环境

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-tx</artifactId>
    <version>5.3.13</version>
</dependency>
  
```

2.编码

```
<!--    原始对象 -->
<bean id="userService" class="com.atlantis.UserServiceImpl">
	<property name="userDAO" ref="userDAO"/>
</bean>

<!--    额外功能 DataSourceTransactionManager-->
<bean id="dataSourceTransactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
	<property name="dataSource" ref="dataSource"/>
</bean>

// 切入点
@Transactional
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;
}

<!--    组装切面-->
<tx:annotation-driven transaction-manager="dataSourceTransactionManager"/>
```

细节

```
<tx:annotation-driven transaction-manager="dataSourceTransactionManager" proxy-target-class="true"/>
进行动态代理底层实现的切换
	默认	false	JDK
		 true	Cglib
```



# 第二章 Spring中的事务属性（Transaction Attribute）

1.什么是事务属性

```markdown
属性：描述物体特征的一系列值
	性别 身高 体重．.
事务属性：描述事务特征的一系列值
1.隔离属性
2.传播属性
3.只读属性
4.超时属性
5.异常属性
```

## 2.如何添加事务属性

```java
@Transactional（isloation=propagation=, readonly=, timeout=, ro11backFor=, noRollbackFor=,)
```

## 3.事务属性详解

### 1.隔离属性（ISOLATION）

```
概念：他描述了事务解决并发问题的特征
	1.什么是并发
		多个事务（用户）在同一时间，访问操作了相同的数据
		同一时间：0.00几秒微小前微小后
	2.并发会产生那些问题
        1.脏读
        2.不可重复读
        3.幻影读
    3.并发问题如何解决
		通过隔离属性解决隔离属性中设置不同的值解决并发处理过程中的问题
```

事务并发产生的问题

- 脏读：

```
一个事务，读取了另一个事务中没有提交的数据。会在本事务中产生数据不一致的问题
解决方案：@Transactional(isolation=Isolation.READ_COMMITTED）
```

- 不可重复读

```
一个事务中，多次读取相同的数据，但是读取结果不一样。会在本事务中产生数据不一致的问题
注意：1不是脏读 2一个事务中
解决方案：@Transactional(isolation=IsolationREPEATABLE_READ）
本质：一把行锁
```

- 幻读

```
一个事务中，多次对整表进行查询统计，但是结果不一样，会在本事务中产生数据不一致的问题
解决方案：@Transactional（isolation=Isolation.SERIALIZABLE）
本质：表锁
```

- 总结

```
并发安全：SERIALIZABLE > REPEATABLE_READ > READ_COMMITTED
运行效率：READ_COMMITTED > REPEATABLE_READ > SERIALIZABLE
```

数据库对于隔离属性的支持

| 隔离属性的值              | MySQL | Oracle |
| ------------------------- | ----- | ------ |
| ISOLATION_READ_COMMITTED  | √     | √      |
| IOSLATION_REPEATABLE_READ | √     | ×      |
| ISOLATION SERIALIZABLE    | √     | √      |

```
Oracle不支持REPEATABLE_READ值，如何解决不可重复读？
采用的是多版本比对的方式解决不可重复读的问题
```

默认隔离属性：

```
ISOLATION_DEFAULT：会调用不同数据库所设置的默认隔离属性
MySQL：REPEATABLE_READ
Oracle：READ_COMMITTED
```

查看默认隔离级别：

```
MySQL
	select @@tx_isolation;
Oracle
略
```

隔离属性在实战中的建议

```
推荐使用Spring指定的ISOLATION_DEFAULT
1.MySQL		repeatable-read
2.Oracle	read_commited

未来中的实战中，并发访问情况很低

如果真遇到并发问题，使用乐观锁

Hibernate(JPA)	Version
MyBatis			通过拦截器自定义开发
```



### 2.传播属性(PROPAGATION)

传播属性的概念

```
概念：他描述了事务解决嵌套问题的特征

什么叫做事务的嵌套：他指的是一个大的事务中，包含了若干个小的事务

问题：大事务中融入了很多小的事务，他们彼此影响，最终就会导致外部大的事务，丧失了事务的原子性
```

传播属性的值及其用法

| 传播属性的值  | 外部不存在事务 | 外部存在事务               | 用法                                                     | 备注             |
| ------------- | -------------- | -------------------------- | -------------------------------------------------------- | ---------------- |
| REQUIRED      | 开启新的事务   | 融合到外部事务中           | @Transactional(propagation = Propagation.REQUIRED）      | 增删改 方法      |
| SUPPORTS      | 不开启新的事务 | 融合到外部事务中           | @Transactional(propagation = Propagation.SUPPORTS）      | 查询 方法        |
| REQUIRES NEW  | 开启新的事务   | 挂起外部事务，创建新的事务 | @Transactional(propagation = Propagation.REQUIRES_NEW）  | 日志记录的方法中 |
| NOT_SUPPORTED | 不开启新的事务 | 挂起外部事务               | @Transactional(propagation = Propagation,NOT_SUPPORTED） |                  |
| NEVER         | 不开启新的事务 | 抛出异常                   | @Transactional(propagation= Propagation.NEVER）          |                  |
| MANDATORY     | 抛出异常       | 融合到外部事务中           | @Transactional(propagation= Propagation.MANDATORY）      |                  |

默认的传播属性

```
REQUIRED是传播属性的默认值
```

推荐传播属性的使用方式

```
增删改	方法：直接使用默认值REQUIRED
查询	操作：显示指定传播属性的值为SUPPORTS
```



### 3.只读属性(readOnly)

```
针对于只进行查询操作的业务方法，可以加入只读属性，提供运行效率

默认值：false
```



### 4.超时属性(timeout)

```
指定了事务等待的最长时间

1.为什么事务进行等待？
	当前事务访问数据时，有可能访问的数据被别的事务进行加锁的处理，那么此时本事务就必须进行等待。
2.等待时间：秒
3.如何应用@Transactional(timeout=2)
4.超时属性的默认值：-1
	最终由对应的数据库指定
```



### 5.异常属性

```
Spring事务处理过程中
默认对于RuntimeException及其子类采用的是回滚的策略
默认对于Exception及其子类采用的是提交的策略

rollbackFor= {java.lang.Exception，xxx,XXX}
noRollbackFor = {java.lang.RuntimeException，XXX，XX
@Transactional(rollbackFor = {java.lang.Exception.class}, noRollbackFor = {java.lang.RuntimeException.class})

建议：实战中使用RuntimeExceptin及其子类使用事务异常属性的默认值
```



## 5.事务属性常见配置总结

```
1.隔离属性	默认值
2.传播属性	Required（默认值）增删改	Supports查询操作
3.只读属性	readonly false增删改	true查询操作
4.超时属性	默认值	-1
5.异常属性	默认值

增删改操作	@Transactional
查询操作	@Transactional（propagation=Propagation.SUPPORTS，readonly=true）
```



## 6.基于标签的事务配置方式（事务开发的第二种形式）

比较麻烦：略。 [138_标签式事务配置_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1Zw41177B7?spm_id_from=333.788.player.switch&vd_source=ae4dac78c13d73ce3caade6a9b8e2914&p=138)  

