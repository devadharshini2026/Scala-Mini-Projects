import scala.io.StdIn._

object TextEncryptor {

  def encrypt(text: String, shift: Int): String = {
    text.map {
      case c if c.isLetter =>
        val base = if (c.isUpper) 'A' else 'a'
        ((c - base + shift) % 26 + base).toChar
      case other => other
    }
  }

  def decrypt(text: String, shift: Int): String = {
    encrypt(text, 26 - (shift % 26))
  }

  def main(args: Array[String]): Unit = {
    var running = true

    while (running) {
      println("\n=== Text Encryption/Decryption CLI ===")
      println("1. Encrypt Text")
      println("2. Decrypt Text")
      println("3. Exit")
      print("Choose an option: ")
      val choice = readLine().trim

      choice match {
        case "1" =>
          print("Enter text to encrypt: ")
          val input = readLine()
          print("Enter shift (e.g., 3): ")
          val shift = readInt()
          val encrypted = encrypt(input, shift)
          println(s"Encrypted Text: $encrypted")

        case "2" =>
          print("Enter text to decrypt: ")
          val input = readLine()
          print("Enter shift used for encryption: ")
          val shift = readInt()
          val decrypted = decrypt(input, shift)
          println(s"Decrypted Text: $decrypted")

        case "3" =>
          println("Exiting... Goodbye!")
          running = false

        case _ =>
          println("Invalid choice. Try again.")
      }
    }
  }
}
