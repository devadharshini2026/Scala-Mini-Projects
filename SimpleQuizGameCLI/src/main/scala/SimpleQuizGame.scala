import scala.io.StdIn._

object SimpleQuizGame {

  case class Question(question: String, options: List[String], answer: Int)

  val quizQuestions: List[Question] = List(
    Question(
      "What is the capital of France?",
      List("1. Paris", "2. London", "3. Berlin", "4. Madrid"),
      1
    ),
    Question(
      "Which language is primarily used for Android development?",
      List("1. Python", "2. Java", "3. Scala", "4. C++"),
      2
    ),
    Question(
      "Who wrote 'Romeo and Juliet'?",
      List("1. Charles Dickens", "2. William Shakespeare", "3. Mark Twain", "4. Jane Austen"),
      2
    )
  )

  def main(args: Array[String]): Unit = {
    println("=== Simple Quiz Game CLI ===")
    var score = 0

    for ((q, index) <- quizQuestions.zipWithIndex) {
      println(s"\nQuestion ${index + 1}: ${q.question}")
      q.options.foreach(println)
      print("Enter your answer (number): ")
      val userAnswer = readLine().toInt

      if (userAnswer == q.answer) {
        println("Correct!")
        score += 1
      } else {
        println(s"Wrong! The correct answer is ${q.answer}.")
      }
    }

    println(s"\nQuiz finished! Your score: $score/${quizQuestions.length}")
  }
}
