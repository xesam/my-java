package dev.xesam.javalang.net;

import dev.xesam.javalang.tools.L;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by xe on 14-11-24.
 */
public class EchoSocketTcpClient {

    public static void main(String[] args) {

        EchoSocketTcpClient echoSocketTcpClient = new EchoSocketTcpClient();
        echoSocketTcpClient.requestEcho();

//        echoSocketClient.httpGet();

//        ExecutorService executorService = Executors.newFixedThreadPool(1);
//
//        for (int i = 0; i < 10; i++) {
//            executorService.submit(new SimpleSocketRequest(i));
//        }
//        executorService.shutdown();
    }

    public void requestEcho() {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(SocketConfig.ECHO_SERVER_HOST, SocketConfig.TCP_ECHO_SERVER_PORT);
        try {
            Socket socket = new Socket();
            socket.connect(inetSocketAddress);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String send = "hello,xesam";
            bufferedWriter.write(send);
            bufferedWriter.write(SocketConfig.ECHO_CRLF);
            bufferedWriter.flush();
            L.log("send:" + send);

            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = bufferedReader.readLine();
            L.log("receive:" + line);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void httpGet() {
        Socket socket = null;
        BufferedWriter bw = null;
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress("127.0.0.1", 80));
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            StringBuilder sb = new StringBuilder()
                    .append("GET / HTTP/1.1").append("\n")
                    .append("Host:localhost").append("\n")
                    .append("Connection:Keep-Alive").append("\n")
                    .append("User-Agent:Xesam/1.0").append("\n")
                    .append("\r\n");
            bw.write(sb.toString());
            bw.flush();

            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {

            }
        }
    }

    public static class SimpleSocketRequest implements Runnable {

        private int index;
        private String name;

        public SimpleSocketRequest(int index) {
            this.index = index;
            this.name = "request:" + index;
        }

        @Override
        public void run() {
            Socket socket = null;
            BufferedWriter bw = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress("127.0.0.1", 80));
                bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bw.write("hello xesam from " + name + "\r\n");
                bw.flush();
                Thread.sleep(1000 * this.index);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bw != null) {
                        bw.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {

                }
            }
        }
    }
}