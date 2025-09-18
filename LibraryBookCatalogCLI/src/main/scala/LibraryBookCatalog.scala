import scala.io.StdIn._

case class Book(title: String, author: String, genre: String, year: Int)

object LibraryBookCatalog {
  var catalog: Map[Int, Book] = Map()
  var nextId: Int = 1

  def main(args: Array[String]): Unit = {
    println("=== Library Book Catalog CLI ===")
    var continue = true

    while (continue) {
      println("\nChoose an option:")
      println("1: Add Book")
      println("2: View All Books")
      println("3: Search by Author")
      println("4: Search by Genre")
      println("5: Exit")

      readLine().trim match {
        case "1" => addBook()
        case "2" => viewBooks()
        case "3" => searchByAuthor()
        case "4" => searchByGenre()
        case "5" =>
          println("Exiting Library Book Catalog. Goodbye!")
          continue = false
        case _ => println("Invalid option. Try again.")
      }
    }
  }

  def addBook(): Unit = {
    println("Enter book title:")
    val title = readLine().trim
    println("Enter author name:")
    val author = readLine().trim
    println("Enter genre:")
    val genre = readLine().trim
    println("Enter publication year:")
    val year = readLine().toInt

    catalog += (nextId -> Book(title, author, genre, year))
    println(s"Book added with ID $nextId.")
    nextId += 1
  }

  def viewBooks(): Unit = {
    if (catalog.isEmpty) println("No books in catalog.")
    else {
      println("\nBooks in Catalog:")
      catalog.foreach { case (id, book) =>
        println(s"ID: $id | ${book.title} by ${book.author} | Genre: ${book.genre} | Year: ${book.year}")
      }
    }
  }

  def searchByAuthor(): Unit = {
    println("Enter author name to search:")
    val author = readLine().trim.toLowerCase
    val results = catalog.filter { case (_, book) => book.author.toLowerCase.contains(author) }

    if (results.isEmpty) println("No books found by this author.")
    else {
      println("\nBooks Found:")
      results.foreach { case (id, book) =>
        println(s"ID: $id | ${book.title} | Genre: ${book.genre} | Year: ${book.year}")
      }
    }
  }

  def searchByGenre(): Unit = {
    println("Enter genre to search:")
    val genre = readLine().trim.toLowerCase
    val results = catalog.filter { case (_, book) => book.genre.toLowerCase.contains(genre) }

    if (results.isEmpty) println("No books found in this genre.")
    else {
      println("\nBooks Found:")
      results.foreach { case (id, book) =>
        println(s"ID: $id | ${book.title} by ${book.author} | Year: ${book.year}")
      }
    }
  }
}
