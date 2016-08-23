package com.java.store.java;

class Cache{
    static{
        initializelIfNecessary();
    }
    private static int sum;
    public static int getSum(){
        initializelIfNecessary();
        return sum;
    }
    private static boolean initialized = false;
    private static synchronized void initializelIfNecessary(){
        if(!initialized){
            for(int i = 0; i < 100; i++){
                sum += i;
            }
            initialized = true;
        }
    }
}

public class TestChuShiHua{
    public static void main(String[] args){
        System.out.println(Cache.getSum());
    }
}

/**
 * 
 * 本程序期望打印出4950，但是程序真实运行结果为9900，是我们期望的两倍，即重复计算了一次。
原因：
我们通过对程序执行过程的分析，来找出问题的根本原因：
(1).在Test类的main方法中调用Cache.getSum()方法时，由于是静态方法，因此首先java虚拟机加载Cache类。
(2).在java虚拟机加载Cache类的时候，执行类初始化方法(静态初始化块和静态变量赋值)，静态初始化块中调用initializelIfNecessary()方法时initialized域还没有被初始化，因此被赋值为默认值false，sum也没有被初始化，默认值为0，在循环中计算出sum的值为4950并且缓存在sum变量中，initialized变量被赋值为true。
(3).静态初始化块执行完毕顺序执行静态变量赋值，此时sum没有被赋值，因此缓存了4950的计算结果，而initialized又被赋值为false。
(4).Cache类加载和初始化完成之后调用getSum方法时initialized还是false，因此又执行了initializelIfNecessary中运行，sum值被累加了4940，最后打印输出9900.

结论：
由于不能在延迟初始化和积极初始化中做出选择，上述代码同时使用了二者，结果产生了初始化的顺序问题，千万不要同时使用延迟初始化和积极初始化。
我们可以使用静态初始化顺序重排和积极初始化来避免上面的重复计算问题，代码如下：
 * class Cache{
    private static final int sum = computeSum();
    private static int computeSum(){
        int result = 0;
        for(int i = 0; i < 100; i++){
            result += i;
        }
        return result;
    }
    public static int getSum(){
        return sum;
    }
}

public class Test{
    public static void main(String[] args){
        System.out.println(Cache.getSum());
    }
}
 */
