import scala.io.StdIn._

object Calculator {
  def main(args: Array[String]): Unit = {
    println("=== Simple Calculator CLI ===")
    println("Enter first number:")
    val num1 = readLine().toDouble

    println("Enter operator (+, -, *, /):")
    val operator = readLine()

    println("Enter second number:")
    val num2 = readLine().toDouble

    val result = operator match {
      case "+" => num1 + num2
      case "-" => num1 - num2
      case "*" => num1 * num2
      case "/" => 
        if (num2 != 0) num1 / num2
        else {
          println("Error: Division by zero")
          Double.NaN
        }
      case _ => 
        println("Invalid operator")
        Double.NaN
    }

    println(s"Result: $result")
  }
}
