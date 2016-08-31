package com.java.store.web.activemq.test.Template;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zeronx
 * @date 2016年6月2日下午10:06:50
 * @version v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath*:java-store-web-dubbo-consumer.xml"})
@ContextConfiguration(locations = { "classpath*:java-store-web-activemq-server.xml" })
public class BaseTestTemplate {
}
