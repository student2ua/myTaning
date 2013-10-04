package com.tor.myTraning.jboss.netty.echo;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.*;

/**
 * Created with IntelliJ IDEA.
 * User: tor
 * Date: 04.10.13
 * Time: 18:32
 * telnet 9999
 */
public class EchoServerHandler extends SimpleChannelHandler {
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        ChannelBuffer buffer = (ChannelBuffer) e.getMessage();
        while (buffer.readable()) {
            System.out.println(buffer.readChar());
            System.out.flush();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        Channel channel = e.getChannel();
        channel.close();
    }
}
