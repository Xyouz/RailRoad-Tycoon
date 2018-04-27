package client

import java.net._
import java.io._
import scala.io._

case class NetworkError(message: String) extends Exception(message)


class Client(val address : String, val port : Int) {
  println("TODO : Gestion des exception lors de la création du client")
  // var id : Int
  // var socket : Socket
  // var inStream : BufferedSource
  // var outStream : PrintStream

  // initialisation
  // try {
  val  socket = new Socket(InetAddress.getByName(address), port)
  val  inStream = new BufferedSource(socket.getInputStream)
  val  outStream = new PrintStream(socket.getOutputStream)
  val  id = 42 // Should be changed to a unique ID for each client
  // }
  // catch {
  //   case e: Exception => {
  //     println("Error while creating the client :/")
  //     println(e.getStackTrace)
  //     System.exit(1)
  //   }
  // }

  def sendPacket(packet : String) = {
    try {
      outStream.println(packet)
      outStream.flush()
    }
    catch {
      case e : Exception => {
        println("Error: I've been unable to send the packet :/")
        println(e.getStackTrace)
        throw NetworkError("packetSend")
      }
    }
  }

  def closeConnection() = {
    println("TODO : send the approriate message once they are well defined.")
    socket.close()
  }

  // def getPacket() = {
  //   try {
  //     inStream.readLine  // readLine is not a member of BufferedSource
  //   }
  //   catch {
  //     case e : Exception => {
  //       println("TODO : Gestion des exceptions lors de la réception des packets")
  //     }
  //   }
  // }

  def getPackets() = {
    try {
      inStream.getLines
    }
    catch {
      case e : Exception => {
        println("TODO : Gestion des exceptions lors de la réception des packets")
      }
    }
  }

}
