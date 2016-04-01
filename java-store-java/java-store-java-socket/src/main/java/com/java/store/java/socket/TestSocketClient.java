package com.java.store.java.socket;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
/**
 * 客户端程序
 * @author Dell
 * @date 2016年3月29日下午10:50:25
 * @version v1.0
 */
public class TestSocketClient {
    private Socket s;
    private DataInputStream dis;
    private DataOutputStream dos;
    private TextArea ta;
    private TextField tf;

    public static void main(String args[]) {
        TestSocketClient tc = new TestSocketClient();
        tc.createUI();
        tc.connect();
        tc.createThread();
    }

    public void connect() {
        try {
            s = new Socket("192.168.133.1", 10083);
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createUI() {
        Frame f = new Frame("Client");
        ta = new TextArea();
        tf = new TextField();
        Button send = new Button("send");
        Panel p = new Panel();
        p.setLayout(new BorderLayout());
        p.add(tf, "Center");
        p.add(send, "East");
        f.add(ta, "Center");
        f.add(p, "South");
        MyClientListener listener = new MyClientListener(this);
        send.addActionListener(listener);
        tf.addActionListener(listener);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.setSize(400, 400);
        f.setLocation(600, 0);
        f.setVisible(true);
    }

    public void createThread() {
        MyClientReader reader = new MyClientReader(this);
        reader.start();
    }

    public void close() {
        try {
            dis.close();
            dos.close();
            s.close();
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
}

class MyClientListener implements ActionListener {

    private TestSocketClient client;

    public MyClientListener(TestSocketClient client) {
        this.client = client;
    }

    public void actionPerformed(ActionEvent e) {
        TextField tf = client.getTextField();
        String info = tf.getText();
        client.getTextArea().append("自己说: " + info + "\n");
        try {
            client.getDataOutputStream().writeUTF(info);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if (info.equals("bye")) {
            client.close();
            System.exit(0);
        }
        tf.setText("");
        tf.requestFocus();
    }
}

class MyClientReader extends Thread {
    private TestSocketClient client;

    public MyClientReader(TestSocketClient client) {
        this.client = client;
    }

    public void run() {
        String info;
        DataInputStream dis = client.getDataInputStream();
        TextArea ta = client.getTextArea();
        try {
            while (true) {
                info = dis.readUTF();
                ta.append("对方说: " + info + "\n");
                if (info.equals("bye")) {
                    client.close();
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}