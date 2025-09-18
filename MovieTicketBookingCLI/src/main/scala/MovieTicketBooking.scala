import scala.io.StdIn._

object MovieTicketBooking {
  var movies: Map[Int, (String, Int)] = Map(
    1 -> ("Avengers: Endgame", 50),
    2 -> ("Inception", 30),
    3 -> ("Interstellar", 40)
  )

  def main(args: Array[String]): Unit = {
    println("=== Movie Ticket Booking CLI ===")
    var continue = true

    while (continue) {
      println("\nChoose an option:")
      println("1: View Movies")
      println("2: Book Tickets")
      println("3: Exit")

      readLine().trim match {
        case "1" => viewMovies()
        case "2" => bookTickets()
        case "3" =>
          println("Exiting Movie Ticket Booking. Goodbye!")
          continue = false
        case _ => println("Invalid option. Try again.")
      }
    }
  }

  def viewMovies(): Unit = {
    println("\nAvailable Movies and Seats:")
    movies.foreach { case (id, (name, seats)) =>
      println(s"$id: $name | Seats Available: $seats")
    }
  }

  def bookTickets(): Unit = {
    println("Enter movie ID to book tickets:")
    val movieId = readLine().toInt
    movies.get(movieId) match {
      case Some((name, seats)) =>
        println(s"Enter number of tickets to book for $name:")
        val tickets = readLine().toInt
        if (tickets <= seats) {
          movies += (movieId -> (name, seats - tickets))
          println(s"$tickets ticket(s) booked for $name.")
        } else {
          println(s"Not enough seats available. Only $seats seat(s) left.")
        }
      case None => println("Movie not found.")
    }
  }
}
