package telchat.net

import org.jboss.netty.channel.{
	ChannelPipelineCoverage, SimpleChannelUpstreamHandler, Channel,
	ChannelHandlerContext, ChannelStateEvent, MessageEvent, ExceptionEvent
}
import org.jboss.netty.buffer.{ChannelBuffers, ChannelBuffer}

import telchat.logic.{Client, Room}

// We need this blank class just because Clojure does not support annotation!
@ChannelPipelineCoverage("one")
class Handler extends SimpleChannelUpstreamHandler with Client {
	var channel: Channel = null

	override def channelConnected(ctx: ChannelHandlerContext, e: ChannelStateEvent) {
		channel = e.getChannel
		Room.onConnect(this)
	}

	override def channelDisconnected(ctx: ChannelHandlerContext, e: ChannelStateEvent) {
		Room.onDisconnect(this)
	}

	override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent) {
		val cb = e.getMessage.asInstanceOf[ChannelBuffer]
        val req = cb.toString("UTF-8")
		Room.onResquest(this, req)
	}

	override def exceptionCaught(ctx: ChannelHandlerContext, e: ExceptionEvent) {
		println(e.getCause())
    	e.getChannel.close  // channelConnected will be called
    }

	//--------------------------------------------------------------------------

	override def sendResponse(res: String) {
		val cb = ChannelBuffers.copiedBuffer(res, "UTF-8")
		channel.write(cb)
	}
}
