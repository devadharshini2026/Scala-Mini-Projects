import scala.io.StdIn._

object Chatbot {

  def main(args: Array[String]): Unit = {
    println("=== Simple Chatbot CLI ===")
    println("Type 'bye' to exit.")
    var continue = true

    while (continue) {
      print("You: ")
      val userInput = readLine().trim.toLowerCase

      userInput match {
        case "hello" | "hi" =>
          println("Bot: Hello! How can I help you today?")
        case "how are you" =>
          println("Bot: I'm just a program, but I'm doing great! What about you?")
        case "time" =>
          println(s"Bot: The current system time is ${java.time.LocalTime.now()}")
        case "date" =>
          println(s"Bot: Today's date is ${java.time.LocalDate.now()}")
        case "bye" =>
          println("Bot: Goodbye! Have a nice day 😊")
          continue = false
        case _ =>
          println("Bot: Sorry, I don't understand that. Try saying 'hello', 'time', or 'date'.")
      }
    }
  }
}
