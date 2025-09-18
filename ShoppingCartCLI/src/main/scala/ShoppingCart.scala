import scala.io.StdIn._

case class Product(id: Int, name: String, price: Double)

object ShoppingCart {
  val products: Map[Int, Product] = Map(
    1 -> Product(1, "Laptop", 50000),
    2 -> Product(2, "Smartphone", 20000),
    3 -> Product(3, "Headphones", 2000),
    4 -> Product(4, "Keyboard", 1500)
  )

  var cart: Map[Int, Int] = Map() // Product ID -> Quantity

  def main(args: Array[String]): Unit = {
    println("=== Shopping Cart CLI ===")
    var continue = true

    while (continue) {
      println("\nChoose an option:")
      println("1: View Products")
      println("2: Add to Cart")
      println("3: View Cart")
      println("4: Remove from Cart")
      println("5: Checkout")
      println("6: Exit")

      readLine().trim match {
        case "1" => viewProducts()
        case "2" => addToCart()
        case "3" => viewCart()
        case "4" => removeFromCart()
        case "5" => checkout()
        case "6" =>
          println("Exiting Shopping Cart. Goodbye!")
          continue = false
        case _ => println("Invalid option. Try again.")
      }
    }
  }

  def viewProducts(): Unit = {
    println("\nAvailable Products:")
    products.foreach { case (id, product) =>
      println(s"${product.id}: ${product.name} - ₹${product.price}")
    }
  }

  def addToCart(): Unit = {
    println("Enter Product ID to add to cart:")
    val id = readLine().toInt
    if (products.contains(id)) {
      println("Enter quantity:")
      val qty = readLine().toInt
      cart += (id -> (cart.getOrElse(id, 0) + qty))
      println(s"${products(id).name} added to cart.")
    } else {
      println("Product not found.")
    }
  }

  def viewCart(): Unit = {
    if (cart.isEmpty) println("Your cart is empty.")
    else {
      println("\nYour Cart:")
      var total = 0.0
      cart.foreach { case (id, qty) =>
        val product = products(id)
        val cost = product.price * qty
        total += cost
        println(s"${product.name} x $qty = ₹$cost")
      }
      println(s"Total: ₹$total")
    }
  }

  def removeFromCart(): Unit = {
    println("Enter Product ID to remove from cart:")
    val id = readLine().toInt
    if (cart.contains(id)) {
      cart -= id
      println(s"${products(id).name} removed from cart.")
    } else println("Product not in cart.")
  }

  def checkout(): Unit = {
    if (cart.isEmpty) println("Your cart is empty.")
    else {
      viewCart()
      println("\nProceed to checkout? (yes/no)")
      val confirm = readLine().trim.toLowerCase
      if (confirm == "yes") {
        println("Checkout successful! Thank you for shopping.")
        cart = Map()
      } else println("Checkout cancelled.")
    }
  }
}
