package com.tor.myTraning.jboss.netty.uptime;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.timeout.ReadTimeoutException;
import org.jboss.netty.util.Timeout;
import org.jboss.netty.util.Timer;
import org.jboss.netty.util.TimerTask;

import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * User: tor
 * Date: 08.05.14
 * Time: 16:22
 * To change this template use File | Settings | File Templates.
 */
public class UpTimeClientHandler extends SimpleChannelUpstreamHandler {
    private final ClientBootstrap bootstrap;
    private final Timer timer;
    private int startTime = -1;

    public UpTimeClientHandler(ClientBootstrap bootstrap, Timer timer) {
        this.bootstrap = bootstrap;
        this.timer = timer;
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        println("Disconnected from: " + getRemoteAdress());
    }

    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        println("Slipping for: " + UpTimeClient.RECONNECT_DELAY + "s");
        timer.newTimeout(new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                println("reconnect to: " + getRemoteAdress());
                bootstrap.connect();
            }
        }, UpTimeClient.RECONNECT_DELAY, TimeUnit.SECONDS);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        Throwable throwable = e.getCause();
        if (throwable instanceof ConnectException) {
            startTime = -1;
            println("Connected failded" + throwable.getMessage());
        } else if (throwable instanceof ReadTimeoutException) {
            // The connection was OK but there was no traffic for last period.
            println("Disconnecting due to no inbound traffic");
        } else {
            throwable.printStackTrace();
        }
        ctx.getChannel().close();
    }

    private void println(String msg) {
        if (startTime < 0) {
            System.err.format("Server is DOWN %s%n", msg);
        } else {
            System.err.format("Uptime: %5ds %s%n", (System.currentTimeMillis() - startTime) / 1000, msg);
        }
    }

    private InetSocketAddress getRemoteAdress() {
        return (InetSocketAddress) bootstrap.getOption("remoteAddress");
    }
}
