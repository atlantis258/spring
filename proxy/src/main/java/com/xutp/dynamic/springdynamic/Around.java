package com.xutp.dynamic.springdynamic;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

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

//        Object ret = null;
//        try {
//            ret = invocation.proceed();
//        } catch (Throwable throwable) {
//            System.out.println("----原始方法抛出异常 执行额外的功能");
//            throwable.printStackTrace();
//        }
//        return ret;
    }
}
