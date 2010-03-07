package telchat.net

import java.net.InetSocketAddress
import java.util.concurrent.Executors
import org.jboss.netty.bootstrap.ServerBootstrap
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory

class Server(port: Int) {
  def start {
    val channelFactory = new NioServerSocketChannelFactory(
      Executors.newCachedThreadPool, Executors.newCachedThreadPool
    )

    val bootstrap = new ServerBootstrap(channelFactory)
    bootstrap.setPipelineFactory(new PipelineFactory)
    bootstrap.bind(new InetSocketAddress(port))
  }
}
