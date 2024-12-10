# 第一章 Spring的事务

## 1.什么是事务？

保证业务操作完整性的一种数据库机制
事务的4特点：ACID

A 原子性
C 一致性
I 工隔离性
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

