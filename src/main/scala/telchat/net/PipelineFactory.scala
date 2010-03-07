package telchat.net

import org.jboss.netty.channel.{Channels, ChannelPipelineFactory, ChannelPipeline}

class PipelineFactory extends ChannelPipelineFactory {
  def getPipeline(): ChannelPipeline = {
    val pipeline = Channels.pipeline
    pipeline.addLast("handler", new Handler)
    return pipeline
  }
}
