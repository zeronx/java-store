package com.java.store.java;

/**
 * @author zeronx-
 * @date 2016年5月10日下午10:27:16
 * @version v1.0
 */
class Point{
    protected final int x, y;
    private final String name;
    Point(int x, int y){
        this.x = x;
        this.y = y;
        name = makeName();
    }
    
    protected String makeName(){
        return "[" + x + "," + y + "]";
    }
    
    public final String toString(){
        return name;
    }
}

public class ColorPoint extends Point{
    protected final String color;
    ColorPoint(int x, int y, String color){
        super(x, y);
        this.color = color;
    }
    
    protected String makeName(){
        return super.makeName() + ":" + color;
    }
    
    public static void main(String[] args){
        System.out.println(new ColorPoint(4, 2, "Blue"));
    }
}