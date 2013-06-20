package com.tor.jboss.netty.asProxy;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.ClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 13.06.13
 * Time: 16:07
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * http://www.mastertheboss.com/netty/use-netty-to-proxy-your-requests
 */
public class HexDumpProxy {
    public static void main(String[] args) throws Exception {
        // Validate command line options.
        if (args.length != 3) {
            System.err.println(
                    "Usage: " + HexDumpProxy.class.getSimpleName() +
                            " <local port> <remote host> <remote port>");
            return;
        }
        // Parse command line options.
        int localPort = Integer.parseInt(args[0]);
        String remoteHost = args[1];
        int remotePort = Integer.parseInt(args[2]);

        System.err.println("Proxying *:" + localPort + " to " + remoteHost + ':' + remotePort + " ...");
        // Configure the bootstrap.
        Executor executor = Executors.newCachedThreadPool();
        ServerBootstrap sb = new ServerBootstrap(
                new NioServerSocketChannelFactory(executor, executor));
        // Set up the event pipeline factory.
        ClientSocketChannelFactory cf =
                new NioClientSocketChannelFactory(executor, executor);
        sb.setPipelineFactory(
                new HexDumpProxyPipelineFactory(cf, remoteHost, remotePort));
        // Start up the server.
        sb.bind(new InetSocketAddress(localPort));
    }
}

