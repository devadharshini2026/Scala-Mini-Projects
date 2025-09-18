import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import java.io.{File, FileOutputStream}
import requests.Response

object ParallelFileDownloader {

  def main(args: Array[String]): Unit = {
    println("=== Parallel File Downloader ===")

    println("Enter URLs to download (comma separated):")
    val urls = scala.io.StdIn.readLine().split(",").map(_.trim).toList

    println("Enter folder to save files:")
    val folder = new File(scala.io.StdIn.readLine().trim)
    if (!folder.exists()) folder.mkdirs()

    // Start downloads in parallel using futures
    val futures = urls.map(url => Future {
      downloadFile(url, folder)
    })

    println("Downloading files in parallel...")

    // Wait for all downloads to complete
    val aggregated = Future.sequence(futures)
    aggregated.onComplete {
      case Success(_) => println("All downloads completed successfully!")
      case Failure(ex) => println(s"Error occurred: ${ex.getMessage}")
    }

    // Keep main thread alive until all futures finish
    Await.result(aggregated, scala.concurrent.duration.Duration.Inf)
  }

  def downloadFile(url: String, folder: File): Unit = {
    try {
      val response: Response = requests.get(url)
      val fileName = url.split("/").lastOption.getOrElse("file")
      val file = new File(folder, fileName)
      val fos = new FileOutputStream(file)
      fos.write(response.bytes)
      fos.close()
      println(s"Downloaded: $fileName")
    } catch {
      case e: Exception => println(s"Failed to download $url: ${e.getMessage}")
    }
  }
}
