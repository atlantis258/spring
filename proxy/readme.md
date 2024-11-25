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

## 2.动态代理类的创建

### 2.1 JDK的动态代理



### 2.2 CGlib的动态代理

CGlib创建动态代理的原理：父子继承关系创建代理对象，原始类作为父类，代理类作为子类，这样既可以保证2者方法一致，同时在代理类中提供新的实现（额外功能+原始方法）。

代码见 cglib包

### 2.3 总结

```
JDK动态代理    	Proxy.newProxyInstance()		通过接口创建代理的实现类
Cglib动态代理	Enhancer						通过继承父类创建的代理类
```










