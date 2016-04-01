package com.java.store.java.socket;
/**
* @author ltao
* @version 1.0 创建时间：2016年3月29日 下午12:06:11
* 
*/
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Socket{

    private static final String SERVER_IP = "192.168.0.178";
    private static final int SERVER_PORT = 2013;
    
    private Socket client;
    private PrintWriter out;
    private BufferedReader in;
    
    /**
     * 与服务器连接，并输入发送消息
     */
    public Client() throws Exception{
        super(SERVER_IP, SERVER_PORT);
        client = this;
        out = new PrintWriter(this.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(this.getInputStream()));
        new readLineThread();
        
        while(true){
            in = new BufferedReader(new InputStreamReader(System.in));
            String input = in.readLine();
            out.println(input);
        }
    }
    
    /**
     * 用于监听服务器端向客户端发送消息线程类
     */
    class readLineThread extends Thread{
        
        private BufferedReader buff;
        public readLineThread(){
            try {
                buff = new BufferedReader(new InputStreamReader(client.getInputStream()));
                start();
            } catch (Exception e) {
            }
        }
        
        @Override
        public void run() {
            try {
                while(true){
                    String result = buff.readLine();
                    if("byeClient".equals(result)){//客户端申请退出，服务端返回确认退出
                        break;
                    }else{//输出服务端发送消息
                        System.out.println(result);
                    }
                }
                in.close();
                out.close();
                client.close();
            } catch (Exception e) {
            }
        }
    }
    
    @SuppressWarnings("resource")
	public static void main(String[] args) {
        try {
            new Client();//启动客户端
        } catch (Exception e) {
        }
    }
}