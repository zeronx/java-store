package com.java.store.java.string.split;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.junit.Test;

/**
 * @author zeronx-
 * @date 2016年5月10日下午10:27:16
 * @version v1.0
 */
public class TestStringClass {

	@Test
	public void testStringSplit() {
		String str = new String("I love dannie,and i miss her");//定义一个字符串
		StringTokenizer token = new StringTokenizer(str, " ,");//按照空格和逗号进行截取
		String[] array = new String[10];//定义一个字符串数组
		int i = 0;
		while (token.hasMoreTokens()) {
		array[i] = token.nextToken();//将分割开的子字符串放入数组中
		i++;
		}
		for (int j = 0; j < array.length; j++) {
		System.out.print(array[j] + "  ");//遍历输出数组
		}
		String string = "java string-split,sldjkf.";
		// 特殊字符 需要用 \\ 转义， 如果是\ 则需要 \\\\ 特殊字符：+、*、.、 \、等
		String[] strings = string.split(" |-|,|\\.");
		System.out.println(Arrays.toString(strings));
	}
	
	@Test
	public void testDate() {
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date data =null;
		try {
			data = dFormat.parse("0001-01-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(data.toString());
	}
	
	@Test
	public void testSub() {
		String string = "00112233";
		System.out.println(string.substring(0,8));
	}
	
	@Test
	public void testSet() {
		Set<String> strings = new HashSet<>();
		strings.add("1213_12");
		strings.add("1213_12");
		strings.add("1213_22");
		strings.add("1213_13");
		System.out.println(strings.toString());
	}
}
