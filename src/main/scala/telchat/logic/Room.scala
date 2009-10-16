package telchat.logic

import scala.actors.Actor

object Room extends Actor {
	def onConnect(client: Client) {
		this ! ('onConnect, client)
	}

	def onDisconnect(client: Client) {
		this ! ('onDisconnect, client)
	}

	def onResquest(client: Client, req: String) {
		this ! ('onResquest, client, req)
	}

	//--------------------------------------------------------------------------

	def act {
		react {
			case msg =>
				println("received message: "+ msg)
				act
		}
	}
}
