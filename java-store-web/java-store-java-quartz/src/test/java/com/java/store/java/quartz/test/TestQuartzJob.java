package com.java.store.java.quartz.test;

import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:java-store-java-quartz.xml"})
public class TestQuartzJob {

	@Test
	public void testJob() {
		Scanner scanner = new Scanner(System.in);
		scanner.nextInt();
	}
}
