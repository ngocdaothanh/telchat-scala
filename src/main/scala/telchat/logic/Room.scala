package telchat.logic

import scala.actors.Actor
import scala.collection.mutable.ArrayBuffer

object Room extends Actor {
  var clients = new ArrayBuffer[Client]()

  def onConnect(client: Client) = {
    this ! ('onConnect, client)
  }

  def onDisconnect(client: Client) = {
    this ! ('onDisconnect, client)
  }

  def onResquest(client: Client, req: String) = {
    this ! ('onResquest, client, req)
  }

  //--------------------------------------------------------------------------

  def act = {
    react {
      case ('onConnect, client: Client) =>
        clients += client
        val s = client + " connected"
        println(s)
        for (c <- clients) c.sendResponse(s)
        act

      case ('onDisconnect, client: Client) =>
        clients.dropWhile(_ == client)
        val s = client + " disconnected"
        println(s)
        for (c <- clients) c.sendResponse(s)
        act

      case ('onResquest, client: Client, req: String) =>
        val s = client + ": " + req
        println(s)
        for (c <- clients) c.sendResponse(s)
        act

      case other =>
        println("Other: " + other)
        act
    }
  }
}
