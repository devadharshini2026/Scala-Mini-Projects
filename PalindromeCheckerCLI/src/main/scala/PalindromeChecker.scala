import scala.io.StdIn._

object PalindromeChecker {
  def main(args: Array[String]): Unit = {
    println("=== Palindrome Checker CLI ===")
    println("Enter a word or number to check:")
    val input = readLine().trim

    if (isPalindrome(input)) {
      println(s"'$input' is a palindrome!")
    } else {
      println(s"'$input' is not a palindrome.")
    }
  }

  // Function to check if a string is a palindrome
  def isPalindrome(str: String): Boolean = {
    val cleaned = str.toLowerCase.replaceAll("\\s+", "")
    cleaned == cleaned.reverse
  }
}
