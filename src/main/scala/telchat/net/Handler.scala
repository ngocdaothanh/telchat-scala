package telchat.net

import org.jboss.netty.channel.{
  SimpleChannelUpstreamHandler, Channel,
  ChannelHandlerContext, ChannelStateEvent, MessageEvent, ExceptionEvent
}
import org.jboss.netty.buffer.{ChannelBuffers, ChannelBuffer}
import java.nio.charset.Charset

import telchat.logic.{Client, Room}

class Handler extends SimpleChannelUpstreamHandler with Client {
  var channel: Channel = null

  override def channelConnected(ctx: ChannelHandlerContext, e: ChannelStateEvent) = {
    channel = e.getChannel
    Room.onConnect(this)
  }

  override def channelDisconnected(ctx: ChannelHandlerContext, e: ChannelStateEvent) = {
    Room.onDisconnect(this)
  }

  override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent) = {
    val cb = e.getMessage.asInstanceOf[ChannelBuffer]
    val req = cb.toString(Charset.forName("UTF-8"))
    Room.onResquest(this, req)
  }

  override def exceptionCaught(ctx: ChannelHandlerContext, e: ExceptionEvent) = {
    // e is mostly java.nio.channels.ClosedChannelException
    // channelDisconnected will be called
    e.getChannel.close
  }

  //--------------------------------------------------------------------------

  override def sendResponse(res: String) = {
    val cb = ChannelBuffers.copiedBuffer(res, Charset.forName("UTF-8"))
    channel.write(cb)
  }
}
