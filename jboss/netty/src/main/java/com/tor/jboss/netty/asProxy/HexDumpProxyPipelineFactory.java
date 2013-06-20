package com.tor.jboss.netty.asProxy;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.ClientSocketChannelFactory;

import static org.jboss.netty.channel.Channels.pipeline;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 13.06.13
 * Time: 16:08
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 */
public class HexDumpProxyPipelineFactory implements ChannelPipelineFactory {
    ClientSocketChannelFactory cf;
    String remoteHost;
    int remotePort;

    public HexDumpProxyPipelineFactory(ClientSocketChannelFactory cf, String remoteHost, int remotePort) {
        this.cf = cf;
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
    }

    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline p = pipeline(); // Note the static import.
        p.addLast("handler", new HexDumpProxyInboundHandler(cf, remoteHost, remotePort));

        return p;
    }
}
