package com.java.store.java;

interface Type1{
    void f() throws CloneNotSupportedException;
}

interface Type2{
    void f() throws InterruptedException;
}

interface Type3 extends Type1, Type2{}

public class TestException2 implements Type3{
    public void f(){
        System.out.println("Hello world!");
    }
    
    public static void main(String[] args) {
        Type3 t3 = new TestException2();
        t3.f();
    }
}

/**
有了前两个小程序做基础，第3个程序更加复杂，初看起来也觉得不能通过编译，因为方法f在Type1接口中声明
要抛出CheckedException——CloneNotSupportedException，
并且在Type2接口中声明要抛出CheckedException——InterruptedException，Type3接口继承了Type1和Type2，
因此看起来在Type3类型对象上调用方法f时，有潜在可能会抛出Type1和Type2异常的并集。一个方法必须要捕获其方法体可以抛出的所有CheckedException，
因此在Type3中方法f应该声明抛出Type1和Type2方法f的所有CheckedException才能正常编译，但是Type3方法f没有声明抛出任何异常却仍然可以通过编译。
java语言规范规定：一个方法可以抛出的CheckedException是它所适用的所有CheckedException的交集，
而并非并集，因此Type3方法f根本不抛出任何异常，因此可以正常编译，该程序可以正常打印输出Hello world!

结论：
对于catch语句捕获CheckedException时，try语句必须抛出相应的CheckedException，多继承而来的方法抛出异常的交集。
*/