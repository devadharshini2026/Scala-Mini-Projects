import scala.io.StdIn._
import java.io.{File, FileWriter, PrintWriter}
import scala.io.Source

object Calculator {

  val historyFile = new File("history.txt")
  if (!historyFile.exists()) historyFile.createNewFile()

  def main(args: Array[String]): Unit = {
    println("=== Command Line Calculator with History ===")
    var continue = true

    while (continue) {
      println(
        """
          |Choose an option:
          |1: Perform Calculation
          |2: View History
          |3: Exit
          |""".stripMargin
      )

      readLine().trim match {
        case "1" => performCalculation()
        case "2" => viewHistory()
        case "3" =>
          println("Exiting Calculator. Goodbye!")
          continue = false
        case _ => println("Invalid option. Try again.")
      }
    }
  }

  def performCalculation(): Unit = {
    println("Enter expression (e.g., 5 + 3):")
    val input = readLine().trim
    val tokens = input.split(" ")

    if (tokens.length != 3) {
      println("Invalid format. Please use: number operator number")
      return
    }

    try {
      val num1 = tokens(0).toDouble
      val op   = tokens(1)
      val num2 = tokens(2).toDouble
      val result = calculate(num1, op, num2)

      val record = s"$input = $result"
      println(record)
      saveHistory(record)

    } catch {
      case e: Exception => println(s"Error: ${e.getMessage}")
    }
  }

  def calculate(num1: Double, op: String, num2: Double): Double = {
    op match {
      case "+" => num1 + num2
      case "-" => num1 - num2
      case "*" => num1 * num2
      case "/" =>
        if (num2 == 0) throw new ArithmeticException("Division by zero")
        else num1 / num2
      case _ => throw new IllegalArgumentException("Unsupported operator")
    }
  }

  def saveHistory(entry: String): Unit = {
    val pw = new PrintWriter(new FileWriter(historyFile, true))
    pw.println(entry)
    pw.close()
  }

  def viewHistory(): Unit = {
    println("\n--- Calculation History ---")
    val lines = Source.fromFile(historyFile).getLines()
    if (lines.isEmpty) println("No history found.")
    else lines.foreach(println)
    println("---------------------------")
  }
}
