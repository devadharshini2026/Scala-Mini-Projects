import java.io.File
import java.nio.file.{Files, Paths, StandardCopyOption}
import scala.io.StdIn._

object FileOrganizer {

  val fileTypes: Map[String, String] = Map(
    "jpg" -> "Images",
    "jpeg" -> "Images",
    "png" -> "Images",
    "gif" -> "Images",
    "pdf" -> "Documents",
    "doc" -> "Documents",
    "docx" -> "Documents",
    "txt" -> "Documents",
    "mp3" -> "Music",
    "wav" -> "Music",
    "mp4" -> "Videos",
    "mkv" -> "Videos",
    "zip" -> "Archives",
    "rar" -> "Archives"
  )

  def main(args: Array[String]): Unit = {
    println("=== File Organizer ===")
    println("Enter the path of the folder to organize:")
    val folderPath = readLine().trim

    val folder = new File(folderPath)
    if (!folder.exists() || !folder.isDirectory) {
      println("Invalid folder path.")
      return
    }

    organizeFiles(folder)
    println("Files organized successfully!")
  }

  def organizeFiles(folder: File): Unit = {
    val files = folder.listFiles()
    if (files == null) return

    for (file <- files if file.isFile) {
      val ext = getFileExtension(file.getName)
      val category = fileTypes.getOrElse(ext, "Others")
      val categoryDir = new File(folder, category)

      if (!categoryDir.exists()) categoryDir.mkdir()

      val targetPath = Paths.get(categoryDir.getAbsolutePath, file.getName)
      Files.move(file.toPath, targetPath, StandardCopyOption.REPLACE_EXISTING)

      println(s"Moved ${file.getName} -> ${category}/")
    }
  }

  def getFileExtension(fileName: String): String = {
    val dotIndex = fileName.lastIndexOf(".")
    if (dotIndex != -1 && dotIndex < fileName.length - 1)
      fileName.substring(dotIndex + 1).toLowerCase
    else ""
  }
}
