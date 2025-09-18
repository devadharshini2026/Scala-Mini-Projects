import scala.io.StdIn._
import java.io.{File, PrintWriter}
import scala.io.Source

object ContactBook {
  var contacts: Map[String, String] = loadContacts()

  def main(args: Array[String]): Unit = {
    println("=== Contact Book CLI ===")
    var continue = true

    while (continue) {
      println("\nChoose an option:")
      println("1: Add Contact")
      println("2: View Contacts")
      println("3: Search Contact")
      println("4: Delete Contact")
      println("5: Exit")

      readLine().trim match {
        case "1" => addContact()
        case "2" => viewContacts()
        case "3" => searchContact()
        case "4" => deleteContact()
        case "5" =>
          saveContacts()
          println("Exiting Contact Book. Goodbye!")
          continue = false
        case _ => println("Invalid option. Try again.")
      }
    }
  }

  def addContact(): Unit = {
    println("Enter contact name:")
    val name = readLine().trim
    println("Enter contact number:")
    val number = readLine().trim
    contacts += (name -> number)
    println(s"Contact $name added successfully.")
  }

  def viewContacts(): Unit = {
    if (contacts.isEmpty) println("No contacts found.")
    else {
      println("\nContacts:")
      contacts.foreach { case (name, number) =>
        println(s"$name: $number")
      }
    }
  }

  def searchContact(): Unit = {
    println("Enter name to search:")
    val name = readLine().trim
    contacts.get(name) match {
      case Some(number) => println(s"$name: $number")
      case None => println("Contact not found.")
    }
  }

  def deleteContact(): Unit = {
    println("Enter name to delete:")
    val name = readLine().trim
    if (contacts.contains(name)) {
      contacts -= name
      println(s"Contact $name deleted successfully.")
    } else println("Contact not found.")
  }

  def saveContacts(): Unit = {
    val pw = new PrintWriter(new File("contacts.txt"))
    contacts.foreach { case (name, number) =>
      pw.println(s"$name,$number")
    }
    pw.close()
  }

  def loadContacts(): Map[String, String] = {
    val file = new File("contacts.txt")
    if (file.exists()) {
      val lines = Source.fromFile(file).getLines()
      lines.map { line =>
        val Array(name, number) = line.split(",")
        (name -> number)
      }.toMap
    } else Map()
  }
}
