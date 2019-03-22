import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.sql.functions._

object SentimentAnalysisOnDemonetization {

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val ssc = new StreamingContext("local[*]","SentimentAnalysis",Seconds(1))
    val lines = ssc.socketTextStream("127.0.0.1",9999,StorageLevel.MEMORY_AND_DISK_SER)

    val spark = SparkSession.builder().master("local[*]").getOrCreate();
    import spark.implicits._

    val rating = spark.read.option("header",false).option("inferSchema",false).textFile("G:\\sparkResources\\AFINN.txt")
    val wordRating = rating.withColumn("words",split($"value","\\s")(0))
                          .withColumn("rating",split($"value","\\s")(1))
                          .drop($"value")

    wordRating.show()

    lines.foreachRDD((rdd,time) => {
      val idt = rdd
        .map(data => {
                            val temp = data.split(",")
                            if(temp.length == 15 ) (temp(0),temp(1))
                            else ("none","none")
                          }).toDF("tweet_id","tweet_text")

      val idText = idt.filter($"tweet_id" =!= "none").filter($"tweet_id".startsWith("\""))

      val idTextWord = idText.withColumn("word",split($"tweet_text"," "))
                              .withColumn("word",explode($"word"))

     // idTextWord.show()

      val idTextRating = idTextWord.join(wordRating,$"word" === $"words").drop($"word")
                .drop($"words").na.fill(0,Array("rating"))

      val textRating = idTextRating.groupBy($"tweet_id",$"tweet_text").agg(avg($"rating").as("rating"))

      println("----- positive ratings ----")
      textRating.filter($"rating" >= 0).show(false)

      println("----- negative ratings ----")
      textRating.filter($"rating" < 0).show(false)
    })

    // Kick it off
    ssc.checkpoint("C:/checkpoint/")
    ssc.start()
    ssc.awaitTermination()

  }

}
