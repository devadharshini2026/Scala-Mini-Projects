import scala.io.StdIn._

object ToDoList {
  var tasks: List[(Int, String, Boolean)] = List()
  var nextId: Int = 1

  def main(args: Array[String]): Unit = {
    println("=== Simple To-Do List CLI ===")
    var continue = true

    while (continue) {
      println("\nChoose an option:")
      println("1: Add Task")
      println("2: View Tasks")
      println("3: Mark Task as Done")
      println("4: Delete Task")
      println("5: Exit")
      
      readLine().trim match {
        case "1" => addTask()
        case "2" => viewTasks()
        case "3" => markTaskDone()
        case "4" => deleteTask()
        case "5" =>
          println("Exiting To-Do List. Goodbye!")
          continue = false
        case _ => println("Invalid option. Try again.")
      }
    }
  }

  def addTask(): Unit = {
    println("Enter task description:")
    val desc = readLine().trim
    tasks = tasks :+ (nextId, desc, false)
    println(s"Task added with ID $nextId.")
    nextId += 1
  }

  def viewTasks(): Unit = {
    if (tasks.isEmpty) println("No tasks found.")
    else {
      println("\nTasks:")
      tasks.foreach { case (id, desc, done) =>
        println(s"ID: $id | [${if (done) "X" else " "}] $desc")
      }
    }
  }

  def markTaskDone(): Unit = {
    println("Enter task ID to mark as done:")
    val id = readLine().toInt
    tasks = tasks.map {
      case (tid, desc, _) if tid == id => (tid, desc, true)
      case other => other
    }
    println(s"Task ID $id marked as done.")
  }

  def deleteTask(): Unit = {
    println("Enter task ID to delete:")
    val id = readLine().toInt
    tasks = tasks.filterNot(_._1 == id)
    println(s"Task ID $id deleted.")
  }
}
