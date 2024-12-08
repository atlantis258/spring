## 第一章 开发流程
### 1. MyBatis开发的流程

   ```markdown
   1. MyBatis做什么？
   MyBatis是一个ORM类型的框架，解决数据库访问和操作的问题，对现有JDBC技术的封装。
   2. MyBatis搭建开发环境
   	1. 准备jar
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
       2. 准备配置文件
       	a. 基本配置文件 mybatis-config.xml
       		1. 设置数据源 environments
       		2. 类型别名
       		3. 注册mapper文件
       	b. Mapper文件
       		1. DAO规定方法的实现 --> SQL语句
       3. 初始化配置
       	mybatis-config.xml
       	配置 environment
   3. 开发步骤
   	1. entity
   	2. 类型别名
   	3. table
   	4. DAO接口
   	5. Mapper文件
   	6. Mapper文件的注册
   	7. API
   ```

   核心代码分析：

   ```java
   InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
   SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
   SqlSession sqlSession = sqlSessionFactory.openSession();
   
   // 两种方式功能等价，第一种方式更好 表达概念更清晰，而非耦合性问题。
   // 第一种方式，本质上是对第二种方法的封装，代理设计模式。
   UserDAO userDAO = sqlSession.getMapper(UserDAO.class);
   List<User> users = userDAO.queryAllUsers();
   
   List<User> users = sqlSession.selectList("com.xutp.dao.UserDAO.queryAllUsers");
   ```



## 第二章 Mybatis的核心对象

### 1. MyBatis的核心对象及作用

   ```markdown
   1. 数据存储类对象
   	概念：在Java中（JVM）对MyBatis相关的配置信息进行封装
   	mybatis-config.xml	-->	org.apache.ibatis.mapping.Configuration
   		Configuration文件
               1. 封装了mybatis-config.xml
               2. 封装了mapper文件：MappedStatement
               3. 创建了Mybatis其他相关的对象
   	XXXDAOMapper.xml	-->	org.apache.ibatis.mapping.MappedStatement
   		MappedStatement对象
   		对应的就是 Mapper 文件中的一个一个的配置标签
   		<select id > ----> MappedStatement
   		<insert id > ----> MappedStatement
   		...
   		注定 一个Mybatis应用中会有 N个MappedStatement对象
   		
   		MappedStatement 中 封装了 SQL语句 ----> BoundSql
   2. 操作类对象
   	Executor	org.apache.ibatis.executor.Executor 接口
   		Executor 是 Mybatis中处理功能的核心
   			1. 增删改upadte	查query
   			2. 事务操作
   				提交 回滚
   			3. 缓存相关的操作
   		Executor接口：
   			BatchExecutor
   				JDBC批处理的操作
   			ReuseExecutor
   				目的：复用 Statement （SQL很少一模一样）
   			SimpleExecutor
   				常用 Executor， Mybatis默认 推荐使用 protected ExecutorType defaultExecutorType = ExecutorType.SIMPLE;
   	StatementHandler
   		StatementHandler 是Mybatis封装了 JDBC Statement，真正Mybatis进行数据库访问操作的核心
   			功能：增删改查
   			SimpleStatementHandler
   			PreparedStatementHandler
   			CallableStatementHandler
   	ParamenterHandler
   		功能：Mybatis参数 ---->	JDBC相关的参数
   			@Param , #{} ----> ?
   	ResultSetHandler
   		功能：对JDBC中查询结果集 ResultSet 进行封装
   	TypeHandler
   		功能：
   		Java程序操作数据库
   		Java类型	   数据库类型
   		String		varchar
   		int			number
   		int			int
   	...
   ```



![image-20241208033915095](D:\github\atlantis258\spring\mybatis\readme\7)



- mybatis-config.xml	-->	org.apache.ibatis.session.Configuration 对应关系

![image-20241208022853601](D:\github\atlantis258\spring\mybatis\readme\1)

​														<typeAliases>

![image-20241208023158424](D:\github\atlantis258\spring\mybatis\readme\2)

​														<mappers>

![image-20241208023343302](D:\github\atlantis258\spring\mybatis\readme\3)

​														Mapper文件相关的内容，在Configuration对象中进行了汇总

![image-20241208023526542](D:\github\atlantis258\spring\mybatis\readme\4)

​														Configuration负责创建了MyBatis其他的核心对象

![image-20241208023726186](D:\github\atlantis258\spring\mybatis\readme\5)



- XXXDAOMapper.xml	-->	org.apache.ibatis.mapping.MappedStatement 对应关系

![image-20241208024340767](D:\github\atlantis258\spring\mybatis\readme\6)



### 2. Mybatis的核心对象 如何与SqlSession建立联系？

1. Mybatis源码中的这些核心对象 在 SqlSession调用对应功能的时候建立联系。

   SqlSession.insert()：调用链如下：

   ​	DefaultSqlSession

   ​		Executor

   ​			StatementHandler

   SqlSession.update()

   SqlSession.delete()

   SqlSession.selectOne()

   ...

   底层：

   SqlSession.insert()

   SqlSession.update()

   SqlSession.delete()

   ...

   应用层面：

   UserDAO userDAO = sqlSession.getMapper(UserDAO.class); 

   ​		获取 UserDAO接口 的实现类的对象（动态字节码技术，类在JVM运行时创建，在JVM运行结束后消失）

   ​		动态字节码技术：

   ​			1.如何创建 UserDAO XXXDAO接口的实现类？

   ​				代理（动态代理）

   ​				a. 为原始对象（目标）增加 额外功能

   ​				b. 远程代理 1.网络通信 2.输出传输 RPC（Dubbo）

   ​				c. 无中生有：接口实现类，看不见实实在在的类文件，但是运行时却能体现出来。

   ​					Proxy.newProxyInstance(ClassLoader, Interface, InvocationHandler)

   ​			2.实现类 如何进行实现的？

   ```java
   public interface UserDAO {
       public void save(User user);
       public List<User> queryAllUsers();
   }
   
   public UserDAOImple implements UserDAO {
       queryAllUsers() {
           sqlSession.select("namespace.id", 参数);
           	|- Executr
                   |- StatementHandler
                   	|- ParameterHandler, ResultSetHandler, TypeHandler
       }
       
       save() {
           sqlSession.insert("namespace.id", 参数);
       }
   }
   
   ```

   userDAO.queryUserById()

   userDAO.queryUsers()

   

   MyBatis 完成代理创建 核心类型 ---->	DAO接口的实现类

   自定义实现如下：

   MyMapperProxy implements InvocationHandler 

   ​	private DAO接口.Class

   ​	private SqlSession sqlSession

   ​	invoke() 

   ​		SqlSession.insert

   ​							update

   ​							delete

   ​							selectOne

   ​							selectList

   真实源码实现如下：

   MapperProxyFactory

   ​	Proxy.newProxyInstance()



## 第三章 总结

![image-20241208122159043](D:\github\atlantis258\spring\mybatis\readme\11)

![image-20241208122210060](D:\github\atlantis258\spring\mybatis\readme\12)

![image-20241208121836917](D:\github\atlantis258\spring\mybatis\readme\8)

![image-20241208112513749](D:\github\atlantis258\spring\mybatis\readme\9)

![image-20241208122145216](D:\github\atlantis258\spring\mybatis\readme\10)



## 第四章  



学习来源：

[mybatis源码分析第四课_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1iX4y1B7rh?spm_id_from=333.788.videopod.episodes&vd_source=ae4dac78c13d73ce3caade6a9b8e2914&p=4)  