import io.circe._, io.circe.parser._, io.circe.generic.auto._
import scala.io.StdIn._
import java.io.File
import scala.io.Source

case class User(id: Int, name: String, age: Int, email: String)

object JSONDataParser {

  var users: List[User] = List()

  def main(args: Array[String]): Unit = {
    println("=== JSON Data Parser CLI ===")
    loadData("data.json")

    var continue = true
    while (continue) {
      println(
        """
          |Choose an option:
          |1: View All Users
          |2: Search User by Name
          |3: Filter Users by Age
          |4: Exit
          |""".stripMargin
      )

      readLine().trim match {
        case "1" => viewUsers()
        case "2" => searchByName()
        case "3" => filterByAge()
        case "4" =>
          println("Exiting JSON Data Parser. Goodbye!")
          continue = false
        case _ => println("Invalid option. Try again.")
      }
    }
  }

  def loadData(filePath: String): Unit = {
    val file = new File(filePath)
    if (!file.exists()) {
      println(s"File $filePath not found.")
      return
    }

    val jsonStr = Source.fromFile(file).getLines().mkString
    decode[List[User]](jsonStr) match {
      case Right(data) => users = data
      case Left(error) => println(s"Failed to parse JSON: $error")
    }
  }

  def viewUsers(): Unit = {
    if (users.isEmpty) println("No users found.")
    else users.foreach { u =>
      println(s"ID: ${u.id}, Name: ${u.name}, Age: ${u.age}, Email: ${u.email}")
    }
  }

  def searchByName(): Unit = {
    println("Enter name to search:")
    val name = readLine().trim.toLowerCase
    val results = users.filter(u => u.name.toLowerCase.contains(name))
    if (results.isEmpty) println("No users found with that name.")
    else results.foreach(u => println(s"ID: ${u.id}, Name: ${u.name}, Age: ${u.age}, Email: ${u.email}"))
  }

  def filterByAge(): Unit = {
    println("Enter minimum age:")
    val minAge = readLine().toInt
    val results = users.filter(_.age >= minAge)
    if (results.isEmpty) println("No users found with age above or equal to that.")
    else results.foreach(u => println(s"ID: ${u.id}, Name: ${u.name}, Age: ${u.age}, Email: ${u.email}"))
  }
}
