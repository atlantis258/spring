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
        指定实体类所在的包com.baizhiedu.entity    User
                                            Product
    </property>
    <property name="mapperLocations">
        指定	配置文件（映射文件）的路径还有通用配置
        com.xutp.mapper/*Mapper.xml
    </property>
</bean>
    
<!--DA0接口的实现类>
session --->	session.getMapper（）XXXDA0实现类对象
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

