# 第一章 Spring与MyBatis整合

## 1.Mybatis开发步骤的回顾

```
1. entity
2. 类型别名
3. table
4. DAO接口
5. Mapper文件
6. Mapper文件的注册
7. Mybatis API调用
```

## 2.Mybatis在开发过程中存在的问题

```
配置繁项 代码冗余
1.实体
2.实体别名			    配置繁琐
3.表
4.创建DAO接口
5.实现Mapper文件
6.注册Mapper文件		配置繁项
7.MybatisAPI调用		 代码元余
```

## 3.Spring与Mybatis的整合思路

![image-20241208183633177](D:\github\atlantis258\spring\spring-mybatis\readme\1)

![image-20241208183700308](D:\github\atlantis258\spring\spring-mybatis\readme\2)

## 4.Spring与Mybatis整合的开发步骤

配置文件（ApplicationContext.xml）进行相关配置

```xml

<bean id="dataSource" class=""/>

        <!--创建SqlSessionFactory-->
<bean id="ssfb" class="SqlSessionFactoryBean">
<property name="dataSource" ref=""/>
<property name="typeAliasesPackage">
    指定实体类所在的包com.baizhiedu.entity User
    Product
</property>
<property name="mapperLocations">
    指定 配置文件（映射文件）的路径还有通用配置
    com.atlantis.mapper/*Mapper.xml
</property>
</bean>

        <!--DA0接口的实现类>
        session --->    session.getMapper（）XXXDA0实现类对象
        XXXDAO ----> xXXDAO
        -->
<bean id="scanner" class="MapperScannerConfigure">
<property name="sqlSessionFactoryBeanName" value="ssfb"/>
<property name="basePacakge">
    指定DA0接口放置的包com.xutp.dao
</property>
</bean>
```

编码：

```
1.实体
2.表
3.创建DAO接口
4.实现Mapper文件
```

## 5.Spring与Mybatis整合代码

1.搭建开发环境

```xml
<dependencies>
  <dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.3.13</version>
  </dependency>

  <dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>3.0.2</version>
  </dependency>

  <dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.3.13</version>
  </dependency>

  <dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.2.15</version>
  </dependency>

  <dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.48</version>
  </dependency>

  <dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.4.6</version>
  </dependency>
  </dependencies>
```

2.Spring配置文件的配置

见代码

3.编码

见代码

## 6.Spring与Mybatis整合细节

问题：Spring与Mybatis整合后，为什么DAO不提交事务，但是数据能够插入数据库中？

```
Connection-->tX
Mybatis(Connection）
本质上控制连接对象（Connection）--->连接池（DataSource）
1.Mybatis提供的连接池对象--->创建Connection
	Connection.setAutocommit（false）手工的控制了事务，操作完成后，手工提交
2.Druid（C3PODBCP）作为连接池创建Connection
	Connection.setAutocommit（true）true默认值保持自动控制事务，一条sgl自动提交
答案：因为Spring与Mybatis整合时，引入了外部连接池对象，保持自动的事务提交这个机制（Connection.setAutocommit（true）），不需要手工进行事务的操作，也能进行事务的提交
注意：未来实战中，还会手工控制事务（多条sql一起成功，一起失败），后续Spring通过事务控制解决这个问题
```



