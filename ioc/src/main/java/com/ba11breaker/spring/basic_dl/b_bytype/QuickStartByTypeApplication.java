package com.ba11breaker.spring.basic_dl.b_bytype;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ba11breaker.spring.basic_dl.b_bytype.bean.Person;
import com.ba11breaker.spring.basic_dl.b_bytype.dao.DemoDao;

public class QuickStartByTypeApplication {
    public static void main(String[] args) throws Exception {
        BeanFactory factory = new ClassPathXmlApplicationContext("basic_dl/quickstart-bytype.xml");
        Person person = factory.getBean(Person.class);
        System.out.println(person);

        DemoDao demoDao = factory.getBean(DemoDao.class);
        System.out.println(demoDao.findAll());
    }
}
