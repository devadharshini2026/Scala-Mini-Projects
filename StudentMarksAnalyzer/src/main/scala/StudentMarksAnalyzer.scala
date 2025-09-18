import scala.io.StdIn._

object StudentMarksAnalyzer {
  var students: Map[String, List[Double]] = Map()

  def main(args: Array[String]): Unit = {
    println("=== Student Marks Analyzer ===")
    var continue = true

    while (continue) {
      println("\nChoose an option:")
      println("1: Add Student and Marks")
      println("2: View All Students")
      println("3: Calculate Average Marks")
      println("4: Find Topper")
      println("5: Exit")
      
      readLine().trim match {
        case "1" => addStudent()
        case "2" => viewStudents()
        case "3" => calculateAverages()
        case "4" => findTopper()
        case "5" =>
          println("Exiting Student Marks Analyzer. Goodbye!")
          continue = false
        case _ => println("Invalid option. Try again.")
      }
    }
  }

  def addStudent(): Unit = {
    println("Enter student name:")
    val name = readLine().trim
    println("Enter marks separated by space (e.g., 85 90 78):")
    val marks = readLine().split(" ").toList.map(_.toDouble)
    students += (name -> marks)
    println(s"Student $name added successfully.")
  }

  def viewStudents(): Unit = {
    if (students.isEmpty) println("No students found.")
    else {
      println("\nStudents and Marks:")
      students.foreach { case (name, marks) =>
        println(s"$name: ${marks.mkString(", ")}")
      }
    }
  }

  def calculateAverages(): Unit = {
    if (students.isEmpty) println("No students to calculate averages.")
    else {
      println("\nAverage Marks:")
      students.foreach { case (name, marks) =>
        val avg = marks.sum / marks.size
        println(s"$name: $avg")
      }
    }
  }

  def findTopper(): Unit = {
    if (students.isEmpty) println("No students to find topper.")
    else {
      val averages = students.map { case (name, marks) => (name, marks.sum / marks.size) }
      val topper = averages.maxBy(_._2)
      println(s"Topper: ${topper._1} with average marks ${topper._2}")
    }
  }
}
