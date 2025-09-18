import java.io.{File, FileWriter, PrintWriter}
import scala.io.StdIn._

object LogLevel extends Enumeration {
  type LogLevel = Value
  val INFO, WARNING, ERROR = Value
}

import LogLevel._

object SimpleLogger {

  val logFile = new File("application.log")
  if (!logFile.exists()) logFile.createNewFile()

  def log(level: LogLevel, message: String): Unit = {
    val logMessage = s"[${level.toString}] ${java.time.LocalDateTime.now()} : $message"
    println(logMessage)
    val pw = new PrintWriter(new FileWriter(logFile, true))
    pw.println(logMessage)
    pw.close()
  }

  def main(args: Array[String]): Unit = {
    println("=== Simple Logger System ===")
    var continue = true

    while (continue) {
      println(
        """
          |Choose an option:
          |1: Log INFO
          |2: Log WARNING
          |3: Log ERROR
          |4: View Log File
          |5: Exit
          |""".stripMargin
      )

      readLine().trim match {
        case "1" =>
          println("Enter message for INFO:")
          val msg = readLine()
          log(INFO, msg)
        case "2" =>
          println("Enter message for WARNING:")
          val msg = readLine()
          log(WARNING, msg)
        case "3" =>
          println("Enter message for ERROR:")
          val msg = readLine()
          log(ERROR, msg)
        case "4" => viewLog()
        case "5" =>
          println("Exiting Simple Logger System. Goodbye!")
          continue = false
        case _ => println("Invalid option. Try again.")
      }
    }
  }

  def viewLog(): Unit = {
    println("\n--- Log File Content ---")
    val lines = scala.io.Source.fromFile(logFile).getLines()
    lines.foreach(println)
    println("------------------------")
  }
}
