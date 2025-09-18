import scala.io.StdIn._

object FactorialFibonacci {
  def main(args: Array[String]): Unit = {
    println("=== Factorial & Fibonacci CLI ===")
    
    println("Enter a number:")
    val num = readLine().toInt
    
    println("Choose an option:")
    println("1: Compute Factorial")
    println("2: Compute Fibonacci Sequence up to n")
    
    val choice = readLine()
    
    choice match {
      case "1" =>
        println(s"Factorial of $num is: ${factorial(num)}")
      case "2" =>
        println(s"Fibonacci sequence up to $num:")
        fibonacci(num).foreach(n => print(s"$n "))
        println()
      case _ =>
        println("Invalid choice")
    }
  }

  // Factorial using recursion
  def factorial(n: Int): BigInt = {
    if (n <= 1) 1
    else n * factorial(n - 1)
  }

  // Fibonacci sequence as a List
  def fibonacci(n: Int): List[BigInt] = {
    def fibHelper(a: BigInt, b: BigInt, count: Int, acc: List[BigInt]): List[BigInt] = {
      if (count == 0) acc
      else fibHelper(b, a + b, count - 1, acc :+ b)
    }

    if (n <= 0) List()
    else if (n == 1) List(0)
    else fibHelper(0, 1, n - 1, List(0))
  }
}
