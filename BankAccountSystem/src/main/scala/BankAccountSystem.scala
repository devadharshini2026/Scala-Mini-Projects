import scala.io.StdIn._

trait Account {
  val accountNumber: Int
  var balance: Double

  def deposit(amount: Double): Unit = {
    balance += amount
    println(s"Deposited $$${amount}. New balance: $$${balance}")
  }

  def withdraw(amount: Double): Unit = {
    if (amount <= balance) {
      balance -= amount
      println(s"Withdrew $$${amount}. New balance: $$${balance}")
    } else {
      println("Insufficient balance.")
    }
  }

  def displayBalance(): Unit = {
    println(s"Account $accountNumber balance: $$${balance}")
  }
}

class SavingsAccount(val accountNumber: Int, var balance: Double) extends Account {
  val interestRate: Double = 0.04

  def addInterest(): Unit = {
    val interest = balance * interestRate
    balance += interest
    println(s"Interest added: $$${interest}. New balance: $$${balance}")
  }
}

class CurrentAccount(val accountNumber: Int, var balance: Double) extends Account {
  val overdraftLimit: Double = 5000

  override def withdraw(amount: Double): Unit = {
    if (amount <= balance + overdraftLimit) {
      balance -= amount
      println(s"Withdrew $$${amount}. New balance: $$${balance}")
    } else {
      println("Exceeds overdraft limit.")
    }
  }
}

object BankAccountSystem {
  var accounts: Map[Int, Account] = Map()
  var nextAccountNumber: Int = 1001

  def main(args: Array[String]): Unit = {
    println("=== Bank Account System ===")
    var continue = true

    while (continue) {
      println("\nChoose an option:")
      println("1: Create Savings Account")
      println("2: Create Current Account")
      println("3: Deposit")
      println("4: Withdraw")
      println("5: Display Balance")
      println("6: Exit")

      readLine().trim match {
        case "1" => createSavings()
        case "2" => createCurrent()
        case "3" => deposit()
        case "4" => withdraw()
        case "5" => displayBalance()
        case "6" =>
          println("Exiting Bank Account System. Goodbye!")
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
    println("Enter account number:")
    val accNum = readLine().toInt
    accounts.get(accNum) match {
      case Some(account) =>
        println("Enter amount to deposit:")
        val amt = readLine().toDouble
        account.deposit(amt)
      case None => println("Account not found.")
    }
  }

  def withdraw(): Unit = {
    println("Enter account number:")
    val accNum = readLine().toInt
    accounts.get(accNum) match {
      case Some(account) =>
        println("Enter amount to withdraw:")
        val amt = readLine().toDouble
        account.withdraw(amt)
      case None => println("Account not found.")
    }
  }

  def displayBalance(): Unit = {
    println("Enter account number:")
    val accNum = readLine().toInt
    accounts.get(accNum) match {
      case Some(account) => account.displayBalance()
      case None => println("Account not found.")
    }
  }
}
