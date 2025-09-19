import scala.io.StdIn._

object CurrencyConverter {

  // Predefined exchange rates (base: 1 USD)
  val rates: Map[String, Double] = Map(
    "USD" -> 1.0,        // US Dollar
    "EUR" -> 0.92,       // Euro
    "INR" -> 83.0,       // Indian Rupee
    "GBP" -> 0.79,       // British Pound
    "JPY" -> 147.5,      // Japanese Yen
    "AUD" -> 1.55        // Australian Dollar
  )

  def main(args: Array[String]): Unit = {
    println("=== Currency Converter CLI ===")
    var continue = true

    while (continue) {
      println(
        """
          |Choose an option:
          |1: Convert Currency
          |2: Show Supported Currencies
          |3: Exit
          |""".stripMargin
      )

      readLine().trim match {
        case "1" => convertCurrency()
        case "2" => showSupportedCurrencies()
        case "3" =>
          println("Exiting Currency Converter. Goodbye!")
          continue = false
        case _ => println("Invalid option. Try again.")
      }
    }
  }

  def convertCurrency(): Unit = {
    println("Enter source currency code (e.g., USD):")
    val from = readLine().trim.toUpperCase

    println("Enter target currency code (e.g., INR):")
    val to = readLine().trim.toUpperCase

    if (!rates.contains(from) || !rates.contains(to)) {
      println("Unsupported currency code. Use option 2 to view supported currencies.")
      return
    }

    println(s"Enter amount in $from:")
    try {
      val amount = readLine().toDouble
      val usdValue = amount / rates(from)    // Convert to USD first
      val converted = usdValue * rates(to)   // Then convert to target
      println(f"$amount%.2f $from = $converted%.2f $to")
    } catch {
      case _: NumberFormatException => println("Invalid number entered.")
    }
  }

  def showSupportedCurrencies(): Unit = {
    println("\n--- Supported Currencies ---")
    rates.keys.foreach(code => println(s"- $code"))
    println("-----------------------------")
  }
}
