package com.ba11breaker.spring.basic_dl.a_quickstart_byname;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.ba11breaker.spring.basic_dl.a_quickstart_byname.bean.Person;

public class QuickStartByNameApplication {
    public static void main(String[] args) throws Exception {
        BeanFactory factory = new ClassPathXmlApplicationContext("basic_dl/quickstart-byname.xml");
        Person person = (Person) factory.getBean("person");
        System.out.println(person);
    }
}
