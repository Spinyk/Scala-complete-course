package lectures.oop.notfatugly.mq

object IbmMq {
  def connectToIbmMq(): Int = {
    // DO NOT TOUCH
    println("Connected to IBM WebSphere super-duper MQ Manager")
    13 // chosen by fair dice roll
  }

  def sendMessageToIbmMq(connectionId: Int, message: String): String = {
    // DO NOT TOUCH
    println(s"Sent MQ message via $connectionId: $message")
    s"Message sending result for $message"
  }
}
