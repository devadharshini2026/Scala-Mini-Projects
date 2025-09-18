import scala.io.StdIn._
import java.io.{File, PrintWriter}
import scala.io.Source

trait Account {
  val accountNumber: Int
  var balance: Double
  var transactions: List[String] = List()

  def deposit(amount: Double): Unit = {
    balance += amount
    val t = s"Deposit: $$${amount}, New Balance: $$${balance}"
    transactions = transactions :+ t
    println(t)
  }

  def withdraw(amount: Double): Unit = {
    if (amount <= balance) {
      balance -= amount
      val t = s"Withdraw: $$${amount}, New Balance: $$${balance}"
      transactions = transactions :+ t
      println(t)
    } else println("Insufficient balance.")
  }

  def displayBalance(): Unit = println(s"Account $accountNumber balance: $$${balance}")

  def showTransactions(): Unit = {
    if (transactions.isEmpty) println("No transactions yet.")
    else transactions.foreach(println)
  }
}

class SavingsAccount(val accountNumber: Int, var balance: Double) extends Account {
  val interestRate: Double = 0.04

  def addInterest(): Unit = {
    val interest = balance * interestRate
    balance += interest
    val t = s"Interest Added: $$${interest}, New Balance: $$${balance}"
    transactions = transactions :+ t
    println(t)
  }
}

class CurrentAccount(val accountNumber: Int, var balance: Double) extends Account {
  val overdraftLimit: Double = 5000

  override def withdraw(amount: Double): Unit = {
    if (amount <= balance + overdraftLimit) {
      balance -= amount
      val t = s"Withdraw: $$${amount}, New Balance: $$${balance}"
      transactions = transactions :+ t
      println(t)
    } else println("Exceeds overdraft limit.")
  }
}

object AdvancedBankingSystem {
  var accounts: Map[Int, Account] = loadAccounts()
  var nextAccountNumber: Int = if (accounts.isEmpty) 1001 else accounts.keys.max + 1

  def main(args: Array[String]): Unit = {
    println("=== Advanced Banking System ===")
    var continue = true

    while (continue) {
      println(
        """
          |Choose an option:
          |1: Create Savings Account
          |2: Create Current Account
          |3: Deposit
          |4: Withdraw
          |5: Display Balance
          |6: Show Transactions
          |7: Add Interest (Savings)
          |8: Exit
          |""".stripMargin
      )

      readLine().trim match {
        case "1" => createSavings()
        case "2" => createCurrent()
        case "3" => deposit()
        case "4" => withdraw()
        case "5" => displayBalance()
        case "6" => showTransactions()
        case "7" => addInterest()
        case "8" =>
          saveAccounts()
          println("Exiting Advanced Banking System. Goodbye!")
          continue = false
        case _ => println("Invalid option. Try again.")
      }
    }
  }

  def createSavings(): Unit = {
    val account = new SavingsAccount(nextAccountNumber, 0)
    accounts += (nextAccountNumber -> account)
    println(s"Savings Account created with account number: $nextAccountNumber")
    nextAccountNumber += 1
  }

  def createCurrent(): Unit = {
    val account = new CurrentAccount(nextAccountNumber, 0)
    accounts += (nextAccountNumber -> account)
    println(s"Current Account created with account number: $nextAccountNumber")
    nextAccountNumber += 1
  }

  def deposit(): Unit = {
    val accNum = promptAccount()
    accounts.get(accNum).foreach { account =>
      println("Enter amount to deposit:")
      val amt = readLine().toDouble
      account.deposit(amt)
    }
  }

  def withdraw(): Unit = {
    val accNum = promptAccount()
    accounts.get(accNum).foreach { account =>
      println("Enter amount to withdraw:")
      val amt = readLine().toDouble
      account.withdraw(amt)
    }
  }

  def displayBalance(): Unit = {
    val accNum = promptAccount()
    accounts.get(accNum).foreach(_.displayBalance())
  }

  def showTransactions(): Unit = {
    val accNum = promptAccount()
    accounts.get(accNum).foreach(_.showTransactions())
  }

  def addInterest(): Unit = {
    val accNum = promptAccount()
    accounts.get(accNum) match {
      case Some(s: SavingsAccount) => s.addInterest()
      case Some(_) => println("Interest applicable only to Savings Account.")
      case None => println("Account not found.")
    }
  }

  def promptAccount(): Int = {
    println("Enter account number:")
    readLine().toInt
  }

  def saveAccounts(): Unit = {
    val pw = new PrintWriter(new File("transactions.txt"))
    accounts.foreach { case (id, acc) =>
      val typeStr = acc match {
        case _: SavingsAccount => "S"
        case _: CurrentAccount => "C"
      }
      pw.println(s"$id,$typeStr,${acc.balance},${acc.transactions.mkString(";")}")
    }
    pw.close()
  }

  def loadAccounts(): Map[Int, Account] = {
    val file = new File("transactions.txt")
    if (!file.exists()) return Map()
    val lines = Source.fromFile(file).getLines()
    lines.map { line =>
      val parts = line.split(",", 4)
      val id = parts(0).toInt
      val accType = parts(1)
      val balance = parts(2).toDouble
      val trans = if (parts.length == 4) parts(3).split(";").toList else List()
      val acc: Account = accType match {
        case "S" =>
          val s = new SavingsAccount(id, balance)
          s.transactions = trans
          s
        case "C" =>
          val c = new CurrentAccount(id, balance)
          c.transactions = trans
          c
      }
      (id -> acc)
    }.toMap
  }
}
