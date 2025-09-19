import scala.io.StdIn._
import java.io.{File, FileWriter, PrintWriter}
import scala.io.Source

case class Account(id: Int, name: String, var balance: Double)

object BankingApp {
  val accounts: scala.collection.mutable.Map[Int, Account] = scala.collection.mutable.Map()
  val transactionsFile = new File("transactions.txt")
  if (!transactionsFile.exists()) transactionsFile.createNewFile()

  def main(args: Array[String]): Unit = {
    println("=== Mini Banking Dashboard ===")
    var continue = true

    while (continue) {
      println(
        """
          |Choose an option:
          |1: Create Account
          |2: Deposit Money
          |3: Withdraw Money
          |4: Check Balance
          |5: View All Accounts
          |6: View Transactions
          |7: Exit
          |""".stripMargin
      )

      readLine().trim match {
        case "1" => createAccount()
        case "2" => depositMoney()
        case "3" => withdrawMoney()
        case "4" => checkBalance()
        case "5" => viewAllAccounts()
        case "6" => viewTransactions()
        case "7" =>
          println("Exiting Banking Dashboard. Goodbye!")
          continue = false
        case _ => println("Invalid option. Try again.")
      }
    }
  }

  def createAccount(): Unit = {
    println("Enter Account ID (number):")
    val id = readLine().toInt
    if (accounts.contains(id)) {
      println("Account already exists with this ID.")
      return
    }
    println("Enter Account Holder Name:")
    val name = readLine()
    accounts(id) = Account(id, name, 0.0)
    println(s"Account created for $name with ID: $id")
    logTransaction(s"Created account for $name (ID: $id)")
  }

  def depositMoney(): Unit = {
    println("Enter Account ID:")
    val id = readLine().toInt
    accounts.get(id) match {
      case Some(acc) =>
        println("Enter amount to deposit:")
        val amt = readLine().toDouble
        acc.balance += amt
        println(s"Deposited $$${amt} to ${acc.name}'s account. New Balance: $$${acc.balance}")
        logTransaction(s"Deposited $$${amt} to ${acc.name} (ID: $id)")
      case None => println("Account not found.")
    }
  }

  def withdrawMoney(): Unit = {
    println("Enter Account ID:")
    val id = readLine().toInt
    accounts.get(id) match {
      case Some(acc) =>
        println("Enter amount to withdraw:")
        val amt = readLine().toDouble
        if (amt > acc.balance) {
          println("Insufficient balance.")
        } else {
          acc.balance -= amt
          println(s"Withdrew $$${amt} from ${acc.name}'s account. New Balance: $$${acc.balance}")
          logTransaction(s"Withdrew $$${amt} from ${acc.name} (ID: $id)")
        }
      case None => println("Account not found.")
    }
  }

  def checkBalance(): Unit = {
    println("Enter Account ID:")
    val id = readLine().toInt
    accounts.get(id) match {
      case Some(acc) => println(s"${acc.name}'s Balance: $$${acc.balance}")
      case None      => println("Account not found.")
    }
  }

  def viewAllAccounts(): Unit = {
    println("\n--- All Accounts ---")
    if (accounts.isEmpty) println("No accounts found.")
    else accounts.values.foreach(acc => println(s"ID: ${acc.id}, Name: ${acc.name}, Balance: $$${acc.balance}"))
    println("---------------------")
  }

  def viewTransactions(): Unit = {
    println("\n--- Transaction History ---")
    val lines = Source.fromFile(transactionsFile).getLines()
    if (lines.isEmpty) println("No transactions recorded.")
    else lines.foreach(println)
    println("----------------------------")
  }

  def logTransaction(entry: String): Unit = {
    val pw = new PrintWriter(new FileWriter(transactionsFile, true))
    pw.println(entry)
    pw.close()
  }
}
