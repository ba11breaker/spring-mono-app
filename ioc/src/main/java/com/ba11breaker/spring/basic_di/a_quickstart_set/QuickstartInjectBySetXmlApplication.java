package com.ba11breaker.spring.basic_di.a_quickstart_set;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ba11breaker.spring.basic_di.a_quickstart_set.bean.Cat;
import com.ba11breaker.spring.basic_di.a_quickstart_set.bean.Person;

public class QuickstartInjectBySetXmlApplication  {
    public static void main(String[] args) throws Exception {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("basic_di/inject-set.xml");
        Person person = beanFactory.getBean(Person.class);
        System.out.println(person);
        
        Cat cat = beanFactory.getBean(Cat.class);
        System.out.println(cat);
    }
}
