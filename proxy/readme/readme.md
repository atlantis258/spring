# 第一章 静态代理设计模式

## 2.1 概念

1. 通过代理类，为原始类（目标）增加额外的功能。

2. 好处：有利于原始类（目标）的维护。

## 2.2 名词解释

1. 目标类（原始类）

   指的是 业务类（核心功能 --> 业务运算 DAO调用）

2. 目标方法（原始方法）

   目标类中的方法，就是目标方法（原始方法）

3. 额外功能（附加功能）

   日志，事务，性能

## 2.3 代理开发的核心要素

代理类 = 目标类（原始类） + 额外功能 + 目标类（原始类）实现相同的接口

```java
public interface UserService {
    void m1();
    void m2();
}

public class UserServiceImpl implements UserService { // 目标类
    @Override
    public void m1() {
        // 业务运算，DAO调用
    }
    @Override
    public void m2() {
    }
}

public class UserServiceProxy implements UserService { // 代理类
    @Override
    public void m1() {
        // 业务运算，DAO调用
    }
    @Override
    public void m2() {
    }
}
```

## 2.4 编码

静态代理：为每一个原始类，手工编写一个代理类（.java .class）

代码：见static1包内的代码

## 2.5 静态代理存在的问题

1. 静态类文件数量过多，不利于项目管理

   ```
   UserService		UserServiceImpl		UserServiceProxy
   OrderService	OrderServiceImpl	OrderServiceProxy
   ```

2. 额外功能维护性差

   代理类中 额外功能修改复杂（麻烦）



# 第二章 Spring的动态代理开发

## 1.Spring动态代理的概念

概念：通过代理类为原始类（目标类）增加额外功能
好处：有利于原始类（目标类）的维护

## 2.搭建开发环境

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-aop</artifactId>
    <version>5.3.13</version>
</dependency>

<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjrt</artifactId>
    <version>1.9.6</version>
</dependency>

<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.6</version>
</dependency>
```

## 3.Spring动态代理的开发步骤

1.创建原始对象（目标对象）

```java
public class UserServiceImpl implements UserService {
    @Override
    public void register(User user) {
        System.out.println("UserServiceImpl.register + 业务运算 + DAO");
    }

    @Override
    public boolean login(String name, String password) {
        System.out.println("UserServiceImpl.login");
        return false;
    }
}
```

```xml
<bean id="userService" class="com.xutp.dynamic.springdynamic.UserServiceImpl"/>
```

2.额外功能
MethodBeforeAdvice接口：额外的功能书写在接口的实现中，在原始方法执行之前运行额外的功能。

```java
public class Before implements MethodBeforeAdvice {
    /*
    作用：需要把运行在原始方法执行之前运行的额外功能，书写在before方法中
     */
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("-----method before advice log----");
    }
}
```

```xml
<bean id="before" class="com.xutp.dynamic.springdynamic.Before"/>
```

3.定义切入点

```
切入点：额外功能加入的位置
目的：由程序员根据自己的需要，决定额外功能加入给那个原始方法
register
login
简单的测试：所有方法都作为切入点，都加入额外的功能
```

```xml
    <aop:config>
<!--        所有的方法，都作为切入点，加入额外功能-->
        <aop:pointcut id="pc" expression="execution(* *(..))"/>
    </aop:config>
```

4.组装（2 3整合）

```xml
表达的含义：所有的方法 都加入 before的额外功能
<!--        组装：目的把切入点 与 额外功能 进行整合-->
        <aop:advisor advice-ref="before" pointcut-ref="pc"/>
```

5.调用

```
目的：获得Spring工厂创建的动态代理对象，并进行调用
ApplicationContext ctx =new ClassPathXmlApplicationContext(“/applicationContext.xml"）；
注意：
1.Spring的工厂通过原始对象的id值获得的是代理对象
2.获得代理对象后，可以通过声明接口类型，进行对象的存储
UserService userService=(UserService)ctx.getBean("userService”）
userService.login(”）
userService.register(）

```



## 4.动态代理细节分析

1.Spring创建的动态代理类在哪里？

```
Spring框架在运行时，通过动态字节码技术，在JVM创建的，运行在JVM内部，等程序结束后，会和JVM一起消失。

什么叫动态字节码技术：通过第三个动态字节码框架，在JVM中创建对应类的字节码，进而创建对象，当虚拟机结束，动态字节码跟着消失。

结论：动态代理不需要定义类文件，都是JVM运行过程中动态创建的，所以不会造成静态代理，类文件数量过多，影响项目管理的问题。
```

![image-20241208171458075](D:\github\atlantis258\spring\proxy\readme\3)

2.动态代理编程简化代码开发

```
在额外功能不改变的前提下，创建其他目标类（原始类）的代理对象时，只需要指定原始（目标）对象即可。
```

3.动态代理额外功能的维护性大大增强

# 第三章 Spring动态代理详解

## 1.额外功能的详解

MethodBeforeAdvice分析

```java
1.MethodBeforeAdvice接口作用：额外功能运行在原始万法执行之前，进行额外功能操作.
    
public class Before implements MethodBeforeAdvice {
    /*
    作用：需要把运行在原始方法执行之前运行的额外功能，书写在before方法中
    Method: 额外功能所增加的那个原始方法
            login方法
            register方法
    Object[]: 额外功能所增加给的那个原始方法的参数 String name, String password
                                               User
    Object：额外功能所增加给的那个原始对象 UserServiceImpl
                                       OrderServiceImpl
     */
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("-----method before advice log----");
    }
}

2.before方法的3个参数在实战中，该如何使用？
before方法的参数，在实战中，会根据需要进行使用，不一定都会用到，也有可能都不用。
```

MethodInterceptor（方法拦截器）

​	MethodBeforeAdvice ----> 原始方法执行之前

​	MethodInterceptor接口：额外功能可以根据需要运行在原始方法执行前、后、前后。

```java
public class Around implements MethodInterceptor {
    /**
     * invoke方法的作用：额外功能书写在invoke
     *                  额外功能：原始方法之前
     *                          原始方法之后
     *                          原始方法执行之前 之后
     *                  确定：原始方法怎么运行
     *                  参数：MethodInvocation (Method)：额外功能所增加给的那个原始方法
     *                      login
     *                      register
     *
     *                      invocation.proceed() ----> login运行
     *                                                  register运行
     *                  返回值：Object：原始方法的返回值
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("----额外功能 运行在执行方法之前----");
        Object ret = invocation.proceed(); // 运行原始方法
        System.out.println("----额外功能 运行在执行方法之后----");
        return ret;
    }
}
```

额外功能运行在原始方法抛出异常的时候

```java
@Override
public Object invoke(MethodInvocation invocation) throws Throwable {
    Object ret = null;
    try {
        ret = invocation.proceed();
    } catch (Throwable throwable) {
        System.out.println("----原始方法抛出异常 执行额外的功能");
        throwable.printStackTrace();
    }
    return ret;
}
```

MethodInterceptor影响原始方法的返回值

```java
原始方法的返回值，直接作为invoke方法的返回值返回，Methodinterceptor不会影响原始方法的返回值
Methodinterceptor影响原始方法的返回值
Invoke方法的返回值，不要直接返回原始方法的运行结果即可
@Override
public Object invoke(MethodInvocation invocation) throws Throwable {

    System.out.println("----额外功能 运行在执行方法之前----");
    Object ret = invocation.proceed(); // 运行原始方法
    System.out.println("----额外功能 运行在执行方法之后----");

    return false;
}
```

# 第四章 动态代理类的创建

### 1 JDK的动态代理

代理创建的三要素：

1.原始对象。

2.额外功能。

3.代理对象和原始对象实现相同的接口（interfaces：原始对象所实现的接口）。

Proxy.newProxyInstance方法参数详解：

![image-20241208161447711](D:\github\atlantis258\spring\proxy\readme\1)

![image-20241208161507235](D:\github\atlantis258\spring\proxy\readme\2)

代码见 jdk包

### 2 CGlib的动态代理

CGlib创建动态代理的原理：父子继承关系创建代理对象，原始类作为父类，代理类作为子类，这样既可以保证2者方法一致，同时在代理类中提供新的实现（额外功能+原始方法）。

代码见 cglib包

### 3 总结

```
JDK动态代理    	Proxy.newProxyInstance()		通过接口创建代理的实现类
Cglib动态代理	Enhancer						通过继承父类创建的代理类
```










