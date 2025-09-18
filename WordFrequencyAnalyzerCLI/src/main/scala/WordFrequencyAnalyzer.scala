import scala.io.StdIn._
import scala.io.Source

object WordFrequencyAnalyzer {

  def main(args: Array[String]): Unit = {
    println("=== Word Frequency Analyzer CLI ===")
    
    println("Enter the file path to analyze:")
    val filePath = readLine().trim

    val file = new java.io.File(filePath)
    if (!file.exists()) {
      println("File not found. Please check the path.")
      return
    }

    val text = Source.fromFile(file).getLines().mkString(" ").toLowerCase
    val words = text.replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+")
    
    val wordFrequency = words.groupBy(identity).view.mapValues(_.length).toMap
    val sortedFrequency = wordFrequency.toSeq.sortBy(-_._2)

    println("\nWord Frequencies:")
    sortedFrequency.foreach { case (word, count) =>
      println(s"$word : $count")
    }
  }
}
