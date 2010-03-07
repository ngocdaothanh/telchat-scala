package telchat

import telchat.logic.Room
import telchat.net.Server

object App {
  def main(args: Array[String]) {
    Room.start

    val server = new Server(3000)
    server.start
  }
}
