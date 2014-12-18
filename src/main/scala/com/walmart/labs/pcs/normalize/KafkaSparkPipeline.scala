package com.walmart.labs.pcs.normalize

import com.walmart.labs.pcs.normalize.RestfulService.NormalizationService
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.codehaus.jackson.map.ObjectMapper

/**
 * Created by pzhong1 on 12/8/14.
 */
object KafkaSparkPipeline {
  def main(args: Array[String]) {
    if (args.length < 4) {
      System.err.println("Usage: KafkaWordCount <zkQuorum> <group> <topics> <numThreads>")
      System.exit(1)
    }

    val Array(zkQuorum, group, topics, numThreads) = args
    val sparkConf = new SparkConf().setAppName("KafkaSparkPipeline")
    val ssc = new StreamingContext(sparkConf, Seconds(2))
    ssc.checkpoint("checkpoint")

    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap).map(_._2)
    val normalizedResults = lines.map(line => line)
    normalizedResults.saveAsTextFiles("data/normalizedResults");

    ssc.start()
    ssc.awaitTermination()
  }
}
