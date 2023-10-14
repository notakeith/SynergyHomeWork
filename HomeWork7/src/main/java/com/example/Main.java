package com.example;

import com.example.HelloBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        HelloBean helloBean = context.getBean("helloBean", HelloBean.class);
        System.out.println(helloBean.getMessage());
    }
}
