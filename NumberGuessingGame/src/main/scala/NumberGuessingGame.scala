import scala.io.StdIn._
import scala.util.Random

object NumberGuessingGame {
  def main(args: Array[String]): Unit = {
    println("=== Welcome to the Number Guessing Game ===")
    val randomNumber = Random.nextInt(100) + 1 // Random number between 1 and 100
    var guess = 0
    var attempts = 0

    println("I have chosen a number between 1 and 100. Can you guess it?")

    while (guess != randomNumber) {
      print("Enter your guess: ")
      guess = readLine().toInt
      attempts += 1

      if (guess < randomNumber) {
        println("Too low! Try again.")
      } else if (guess > randomNumber) {
        println("Too high! Try again.")
      } else {
        println(s"Congratulations! You guessed the number in $attempts attempts.")
      }
    }
  }
}
