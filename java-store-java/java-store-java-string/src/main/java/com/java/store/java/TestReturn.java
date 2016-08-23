package com.java.store.java;

public class TestReturn {
	 public static void main(String[] args) {
	        System.out.println(decision());
	    }
	    
	    @SuppressWarnings("finally")
		static boolean decision(){
	        try{
	            return true;
	        }finally{
	            return false;
	        }
	    }
}
