package com.java.store.web.activemq.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        //阻塞主线程
        System.in.read();
        
        DefaultMessageListenerContainer container = (DefaultMessageListenerContainer)context.getBean("jmsContainer");
        container.shutdown();
    }
}
