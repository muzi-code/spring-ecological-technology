package com.jd.nlp.dev.muzi.spring5.exercise.aop01;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component("beforeAdviceBean")
public class BeforeAdviceBean implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("======自定义BeforeAdvice增强一记");
    }
}
