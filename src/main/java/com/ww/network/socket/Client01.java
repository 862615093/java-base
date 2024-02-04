package com.ww.frame.network.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;


public class Client01 {
    public static void main(String[] args) {
//        TCP协议Socket：客户端
//        try {
//            //创建套接字对象socket并封装ip与port
//            Socket socket = new Socket("127.0.0.1", 8000);
//            //根据创建的socket对象获得一个输出流
//            OutputStream outputStream = socket.getOutputStream();
//            //控制台输入以IO的形式发送到服务器
//            System.out.println("TCP连接成功 \n请输入：");
//            while (true) {
//                byte[] car = new Scanner(System.in).nextLine().getBytes();
//                outputStream.write(car);
//                System.out.println("TCP协议的Socket发送成功");
//                //刷新缓冲区
//                outputStream.flush();
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        //UDP协议Socket：客户端
        try {
            //DatagramSocket代表声明一个UDP协议的Socket
            DatagramSocket socket = new DatagramSocket(2468);
            //字符串存储人Byte数组
            byte[] car = "UDP协议的Socket请求，有可能失败哟".getBytes();
            //InetSocketAddress类主要作用是封装端口
            InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8888);
            //DatagramPacket 类用来表示数据报包DatagramPacket
            DatagramPacket packet = new DatagramPacket(car, car.length, address);
            //send() 方法发送数据包。
            socket.send(packet);
            System.out.println("UDP协议的Socket发送成功");
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
