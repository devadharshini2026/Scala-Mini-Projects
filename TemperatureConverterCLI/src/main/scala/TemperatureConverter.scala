import scala.io.StdIn._

object TemperatureConverter {
  def main(args: Array[String]): Unit = {
    println("=== Temperature Converter CLI ===")
    
    println("Enter the temperature value:")
    val temp = readLine().toDouble
    
    println("Select the conversion type:")
    println("1: Celsius to Fahrenheit")
    println("2: Fahrenheit to Celsius")
    println("3: Celsius to Kelvin")
    println("4: Kelvin to Celsius")
    println("5: Fahrenheit to Kelvin")
    println("6: Kelvin to Fahrenheit")
    
    val choice = readLine()
    
    val result = choice match {
      case "1" => celsiusToFahrenheit(temp)
      case "2" => fahrenheitToCelsius(temp)
      case "3" => celsiusToKelvin(temp)
      case "4" => kelvinToCelsius(temp)
      case "5" => fahrenheitToKelvin(temp)
      case "6" => kelvinToFahrenheit(temp)
      case _ =>
        println("Invalid choice")
        Double.NaN
    }
    
    if (!result.isNaN) println(s"Converted temperature: $result")
  }

  def celsiusToFahrenheit(c: Double): Double = (c * 9/5) + 32
  def fahrenheitToCelsius(f: Double): Double = (f - 32) * 5/9
  def celsiusToKelvin(c: Double): Double = c + 273.15
  def kelvinToCelsius(k: Double): Double = k - 273.15
  def fahrenheitToKelvin(f: Double): Double = celsiusToKelvin(fahrenheitToCelsius(f))
  def kelvinToFahrenheit(k: Double): Double = celsiusToFahrenheit(kelvinToCelsius(k))
}
