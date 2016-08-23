package com.java.store.java;

public class TestException1 {
	    private static final long TEST_USER_ID = -1;
	    private static long USER_ID;
//	    private static final long USER_ID;
	    static{
	        try{
	            USER_ID = getUserId();
	        }catch(IdUnavailableException e){
	            USER_ID = TEST_USER_ID;
	            System.out.println("Logging in as guest");
	        }
	    }
	    
	    private static long getUserId() throws IdUnavailableException{
	        throw new IdUnavailableException();
	    }
	    
	    public static void main(String[] args) {
	        System.out.println("User ID:" + USER_ID);
	    }
	    
	}
@SuppressWarnings("serial")
class IdUnavailableException extends Exception{}

/**
该程序很多人认为应该打印输出User Id:-1，因为在调用getUserId时候产生异常，因此常量USER_ID在catch语句块中被赋值。
真实情况是：上述程序根本不能通过编译，catch语句块中USER_ID赋值会报编译错误：
The final field USER_ID may already have been assigned。

原因：
常量USER_ID是一个空final，它是一个在声明时没有进行初始化操作的final域，上述示例代码只有在对USER_ID赋值失败时，
才会在try语句块中抛出异常，因此在catch语句块中赋值是安全的，因为只会对USER_ID赋值一次。
计算机判断程序是否会对一个空final域进行超过一次赋值是一件很困难的事情，事实上这是不可能，这等价于经典的停机问题，
通常被认为是不可能解决的，为了能够编写出一个编译器，语言规范在空final域赋值问题上采用了保守方式——一个空final域只有在它是明确未赋过值的地方才可以被赋值。
上述程序中虽然只会对空final域USER_ID赋值一次，但是编译器为了保证保守的final赋值方式会拒绝编译通过。
结论：
解决上述问题的最好方式是将烦人的空final域改变为普通的final类型，代码如下：
*/