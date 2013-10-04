package com.tor.myTraning.jboss.netty.echo;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: tor
 * Date: 04.10.13
 * Time: 18:27
 * To change this template use File | Settings | File Templates.
 */
public class NettyServer {

    public void startServer() {
        ChannelFactory factory = new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool());
        ServerBootstrap bootstrap = new ServerBootstrap(factory);
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new EchoServerHandler());
            }
        });
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);
        bootstrap.bind(new InetSocketAddress(NettyEchoClient.port));
    }
}
