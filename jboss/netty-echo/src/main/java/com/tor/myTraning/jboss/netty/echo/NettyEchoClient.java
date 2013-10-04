package com.tor.myTraning.jboss.netty.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created with IntelliJ IDEA.
 * User: tor
 * Date: 04.10.13
 * Time: 19:39
 * To change this template use File | Settings | File Templates.
 */
public class NettyEchoClient {
    public static final int port = 9999;

    public static void main(String[] args) {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        SocketChannel sc = null;
        try {
            sc = SocketChannel.open();
            sc.configureBlocking(false);

            sc.connect(new InetSocketAddress(port));
            while (!sc.finishConnect()) {
            }

            System.out.println("Enter the text");
            String HELLO_REQUEST = stdin.readLine();

            System.out.println("Sending a request to HelloServer");
            ByteBuffer buffer = ByteBuffer.wrap(HELLO_REQUEST.getBytes());
            sc.write(buffer);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sc != null) {
                try {
                    sc.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
