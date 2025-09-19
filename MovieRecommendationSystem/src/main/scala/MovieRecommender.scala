import org.apache.spark.sql.{SparkSession}
import org.apache.spark.mllib.recommendation.{ALS, Rating, MatrixFactorizationModel}
import org.apache.spark.rdd.RDD

object MovieRecommender {

  def main(args: Array[String]): Unit = {
    // Start Spark
    val spark = SparkSession.builder()
      .appName("Movie Recommendation System")
      .master("local[*]") // local mode
      .getOrCreate()

    val sc = spark.sparkContext

    // Load ratings data (userId,movieId,rating)
    val ratingsData = sc.textFile("data/ratings.csv")
    val header = ratingsData.first()
    val ratings: RDD[Rating] = ratingsData
      .filter(_ != header)
      .map(_.split(","))
      .map(parts => Rating(parts(0).toInt, parts(1).toInt, parts(2).toDouble))

    // Load movies (movieId,title)
    val moviesData = sc.textFile("data/movies.csv")
    val moviesHeader = moviesData.first()
    val movies = moviesData
      .filter(_ != moviesHeader)
      .map(_.split(","))
      .map(parts => (parts(0).toInt, parts(1)))
      .collectAsMap()

    // Train ALS model
    val rank = 10
    val numIterations = 10
    val model: MatrixFactorizationModel =
      ALS.train(ratings, rank, numIterations, 0.01)

    println("=== Movie Recommendation System ===")
    var continue = true
    while (continue) {
      println(
        """
          |Choose an option:
          |1: Get Movie Recommendations
          |2: Exit
          |""".stripMargin)

      scala.io.StdIn.readLine().trim match {
        case "1" =>
          println("Enter User ID:")
          val userId = scala.io.StdIn.readInt()

          val recommendations = model.recommendProducts(userId, 5)

          println(s"\nTop 5 Recommendations for User $userId:")
          recommendations.foreach { r =>
            println(s"${movies.getOrElse(r.product, "Unknown")} (Predicted Rating: ${r.rating})")
          }
          println("-------------------------------")

        case "2" =>
          println("Exiting system. Goodbye!")
          continue = false

        case _ => println("Invalid option. Try again.")
      }
    }

    spark.stop()
  }
}
