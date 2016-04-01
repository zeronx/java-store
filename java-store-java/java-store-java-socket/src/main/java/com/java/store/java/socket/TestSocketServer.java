package com.java.store.java.socket;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 服务器端程序
 * @author Dell
 * @date 2016年3月29日下午10:50:03
 * @version v1.0
 */
public class TestSocketServer {
    private ServerSocket ss;
    private Socket s;
    private DataInputStream dis;
    private DataOutputStream dos;
    private TextArea ta;
    private TextField tf;

    private static List<ServerThread> userThreadList = new ArrayList<>();
    private static Map<String, List<ServerThread>> userMapThreads= new HashMap<>();
    private static Map<String, ServerThread> userMapThread= new HashMap<>();
    public static void main(String args[]) {
    	TestSocketServer ts = new TestSocketServer();
        ts.createUI();
        ts.connect();
      //  ts.createThread();
    }

    public void connect() {
        try {
            ss = new ServerSocket(10083);
            while (true) {
            	s = ss.accept();
            	new ServerThread(s, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createUI() {
        Frame f = new Frame("Server");
        ta = new TextArea();
        tf = new TextField();
        Button send = new Button("send");
        Panel p = new Panel();
        p.setLayout(new BorderLayout());
        p.add(tf, "Center");
        p.add(send, "East");
        f.add(ta, "Center");
        f.add(p, "South");
        MyServerListener listener = new MyServerListener(this);
        send.addActionListener(listener);
        tf.addActionListener(listener);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.setSize(400, 400);
        f.setVisible(true);
    }

    public void createThread() {
        MyServerReader reader = new MyServerReader(this);
        reader.start();
    }

    public void close() {
        try {
            dis.close();
            dos.close();
            s.close();
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DataInputStream getDataInputStream() {
        return dis;
    }

    public DataOutputStream getDataOutputStream() {
        return dos;
    }

    public TextArea getTextArea() {
        return ta;
    }

    public TextField getTextField() {
        return tf;
    }
    /**
     * 服务器线程类
     */
    class ServerThread extends Thread {
    	private TestSocketServer server;
        private Socket client;
        private String name;
    	private DataInputStream dis;
    	private DataOutputStream dos;
        public ServerThread(Socket s, TestSocketServer server) throws IOException {
        	this.server=server;
        	client = s;
            dis = new DataInputStream(s.getInputStream());
        	dos = new DataOutputStream(s.getOutputStream());
        	System.out.println("成功连上聊天室,请输入你的名字：");
            start();
        }

    	@Override
        public void run() {
            try {
                int flag = 0;
                while (true) {
                	String line = this.getDataInputStreamIn().readUTF();// in.readLine();
                	name = line.substring(0,6);
                	System.out.println("line ================="+line);
                    // 查看在线用户列表
                    if ("showuser".equals(line)) {
//                        out.println(this.listOnlineUsers());
//                        line = in.readLine();
                    }
                    // 第一次进入，保存名字账号
                    if (flag++ == 0) {
                        name = line;
                        userMapThread.put(name, this);
//                        user_list.add(name);
//                        thread_list.add(this);
                        System.out.println(name + "你好,可以开始聊天了...");
                       // this.pushMessage("Client<" + name + ">进入聊天室...");
                    } else {
                    	ServerThread serverThread = userMapThread.get(name);
                    	if (serverThread != null) {
                    		server.getTextArea().append("XX对"+name+"说"+line+"\n");
                    		serverThread.getDataOutputStreamIn().writeUTF(line);
                    	}
                    	System.out.println("没有找到用户" + name);
                       // this.pushMessage("Client<" + name + "> say : " + line);
                    }
//                    line = in.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {// 用户退出聊天室
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                thread_list.remove(this);
//                user_list.remove(name);
                pushMessage("Client<" + name + ">退出了聊天室");
            }
        }

        // 放入消息队列末尾，准备发送给客户端
    	private void pushMessage(String msg) {
//            message_list.addLast(msg);
//            isPrint = true;
        }

        // 向客户端发送一条消息
        private void sendMessage(String msg) {
//            out.println(msg);
        }

        // 统计在线用户列表
        private String listOnlineUsers() {
            String s = "--- 在线用户列表 ---\015\012";
//            for (int i = 0; i < user_list.size(); i++) {
//                s += "[" + user_list.get(i) + "]\015\012";
//            }
            s += "--------------------";
            return s;
        }
        private DataInputStream getDataInputStreamIn() {
            return dis;
        }

        private DataOutputStream getDataOutputStreamIn() {
            return dos;
        }
    }
}

class MyServerListener implements ActionListener {
    private TestSocketServer server;

    public MyServerListener(TestSocketServer server) {
        this.server = server;
    }

    public void actionPerformed(ActionEvent e) {
        TextField tf = server.getTextField();
        String info = tf.getText();
        server.getTextArea().append("管理员对大家说: " + info + "\n");
        try {
            server.getDataOutputStream().writeUTF(info);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if (info.equals("bye")) {
            server.close();
            System.exit(0);
        }
        tf.setText("");
        tf.requestFocus();
    }
}

class MyServerReader extends Thread {
    private TestSocketServer server;

    public MyServerReader(TestSocketServer server) {
        this.server = server;
    }

    public void run() {
        String info;
        DataInputStream dis = server.getDataInputStream();
        TextArea ta = server.getTextArea();
        try {
            while (true) {
                info = dis.readUTF();
                ta.append("对方说: " + info + "\n");
                if (info.equals("bye")) {
                    server.close();
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
