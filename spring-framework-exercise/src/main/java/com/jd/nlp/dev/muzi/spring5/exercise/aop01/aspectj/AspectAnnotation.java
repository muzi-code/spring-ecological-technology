package com.jd.nlp.dev.muzi.spring5.exercise.aop01.aspectj;

import com.jd.nlp.dev.muzi.spring5.exercise.annotation.ReturnValue;
import com.jd.nlp.dev.muzi.spring5.exercise.annotation.TargetMethod;
import com.jd.nlp.dev.muzi.spring5.exercise.annotation.ThrowsAnno;
import com.jd.nlp.dev.muzi.spring5.exercise.aop01.service.DataCheck;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Aspect
public class AspectAnnotation {

    /*
    * introduction 引介动态添加功能，并且改变目标类的类型，其实就是目标类多实现了接口而已
    * */
    @DeclareParents(value = "com.jd.nlp.dev.muzi.spring5.exercise.aop01.service.BankServiceImpl",
            defaultImpl = com.jd.nlp.dev.muzi.spring5.exercise.aop01.service.DataCheckImpl.class)
    private DataCheck dataCheck;



    /**
     * 这个方法可以理解为一组 joinPoint 的集合，切入点的集合。
     */
    @Pointcut("execution(public * com.jd.nlp.dev.muzi.spring5.exercise.aop01.service.*.*(..))")
    public void pc1(){}


    /**
     * 这个方法就叫做 advice 通知
     */
    @Around("pc1()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("==============AspectAnnotation around前置通知=========");
        Object result = joinPoint.proceed();
        System.out.println("==============AspectAnnotation around后置通知=========");
        return result;
    }

    @Pointcut("execution(public * com.jd.nlp.dev.muzi.spring5.exercise.aop01.service.*.add*(..))")
    public void pc2(){}


    @Before("pc2()")
    public void before() {
        System.out.println("===============只拦截add方法=========");
    }

    @Pointcut("execution(public * com.jd.nlp.dev.muzi.spring5.exercise.aop01.service.*.*(..))")
    public void pc3(){}


    @Before("pc3()")
    public void before1(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        System.out.println("===============before1 service1包的拦截=========");
    }

    @Before("pc3()&&args(bankId,id,list)")
    public void before2(String bankId,Integer id,List list) {
        System.out.println("===============before2 service1包的拦截=========");
    }

    @Before(value = "@annotation(targetMethod)",argNames = "joinPoint,targetMethod")
    public void xx(JoinPoint joinPoint, TargetMethod targetMethod) {
        System.out.println("===============注解拦截 前置通知=========" + targetMethod.name());
    }

    @AfterReturning(value = "@annotation(returnValue)",returning = "retVal")
    public void afterReturning(JoinPoint joinPoint, ReturnValue returnValue, Object retVal) {
        System.out.println("==============AspectAnnotation 后置通知  拿返回值=========" + retVal);
    }

    @AfterThrowing(value = "@annotation(throwsAnno)",throwing = "e")
    public void throwMethod(JoinPoint joinPoint, ThrowsAnno throwsAnno, Throwable e) {
        System.out.println("==============AspectAnnotation 异常通知  拿异常=========" + e);
    }


    /**
     * 就算不写 argNames这个属性也是可以拿到的
     *
     * Spring 可以通过 LocalVariableTableParameterNameDiscoverer类拿到对应的参数名称。
     *
     *     public static void main(String[] args) {
     *         LocalVariableTableParameterNameDiscoverer lv = new LocalVariableTableParameterNameDiscoverer();
     *         Method[] declaredMethods = MyTest.class.getDeclaredMethods();
     *         for (Method declaredMethod : declaredMethods) {
     *             String[] names = lv.getParameterNames(declaredMethod);
     *
     *             for (String name : names) {
     *                 System.out.println(name);
     *             }
     *         }
     *     }
     */
}
