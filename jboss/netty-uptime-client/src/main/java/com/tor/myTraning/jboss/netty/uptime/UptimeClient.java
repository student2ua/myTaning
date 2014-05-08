package com.tor.myTraning.jboss.netty.uptime;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.timeout.ReadTimeoutHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * User: tor
 * Date: 08.05.14
 * Time: 16:01
 */
public class UptimeClient {
    public static final int RECONNECT_DELAY = 5;
    private static final int Read_TIMEOUT_SECONDS = 10;
    public static String host = "192.168.0.124";
//    public static String host = "localhost";
    public static Integer port = 5445;

    public static void main(String[] args) {
        final Timer timer = new HashedWheelTimer();
        final ClientBootstrap bootstrap = new ClientBootstrap(
                new NioClientSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            private final ChannelHandler timeOutHandler = new ReadTimeoutHandler(timer, Read_TIMEOUT_SECONDS);
            private final ChannelHandler upTimeHandler = new UptimeClientHandler(bootstrap, timer);

            @Override
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(timeOutHandler, upTimeHandler);
            }
        });
        bootstrap.setOption("remoteAddress", new InetSocketAddress(host, port));
        bootstrap.connect();
    }
}
