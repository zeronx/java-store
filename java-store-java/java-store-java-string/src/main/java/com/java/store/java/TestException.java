package com.java.store.java;

public class TestException {
    public static void main(String[] args) {
        try{
            System.out.println("Hello world!");
        }catch(Exception e){  //IOException
            System.out.println("I've never seen println fail!");
        }
    }
}
/**
这个程序乍一看没什么问题，实际上根本不能通过编译。
该程序演示了CheckedException的一个基本原则：如果在catch语句块中捕获一个CheckedException，
则try语句块必须要抛出相应的CheckException才行，
上述代码中IOException是一个CheckedException，但是try语句中没有抛出任何IOException，因此无法通过编译。
*/

/**
public class Test2 {
    public static void main(String[] args) {
        try{
            
        }catch(Exception e){
            System.out.println("This can't happen!");
        }
    }
}
有了第一个小程序的经验之后，很多人认为改程序不会通过编译，但是它却是可以通过编译的，只是没有任何输出结果。
该程序的catch语句块捕获一个Exception，java中异常的基类是Throwable，其对应两个子类：
Error和Exception，Exception又分为CheckedException和UncheckedException。java语言规范规定，
不管与其对应的try子句的内容为何，catch语句捕获Exception/Throwable是合法的，因此该程序可以通过编译，只是catch语句永远不会执行。
*/