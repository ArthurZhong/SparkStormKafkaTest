package com.walmart.labs.pcs.normalize

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql._
import org.apache.spark.sql.hive.LocalHiveContext

object SparkHiveExample {
  case class Record(key: Int, value: String)
  
  def main(args: Array[String]) {
    if (args.length < 1) {
      System.err.println("Usage: Hdfs file address")
      System.exit(1)
    }
    val sc = new SparkContext(new SparkConf().setAppName("Spark Hive Example"))
    val sqlContext = new org.apache.spark.sql.hive.HiveContext(sc)
    import sqlContext._
    
    // Queries are expressed in HiveQL
    println("Result of 'SELECT *': ")
    sqlContext.sql("CREATE TABLE IF NOT EXISTS sparkHiveTest (key INT, value STRING)")
    sqlContext.sql("LOAD DATA LOCAL INPATH '/user/pzhong1/tmp/kv1.txt' INTO TABLE sparkHiveTest")
    // Queries are expressed in HiveQL
    sqlContext.sql("FROM sparkHiveTest SELECT key, value").collect().foreach(println)
    
    // Aggregation queries are also supported.
    val count = hql("SELECT COUNT(*) FROM sparkHiveTest").collect().head.getLong(0)
    println(s"COUNT(*): $count")
    
     // The results of SQL queries are themselves RDDs and support all normal RDD functions.  The
    // items in the RDD are of type Row, which allows you to access each column by ordinal.
    val rddFromSql = hql("SELECT key, value FROM sparkHiveTest WHERE key < 10 ORDER BY key")

    println("Result of RDD.map:")
    val rddAsStrings = rddFromSql.map {
      case Row(key: Int, value: String) => s"Key: $key, Value: $value"
    }

    // You can also register RDDs as temporary tables within a HiveContext.
    val rdd = sc.parallelize((1 to 100).map(i => Record(i, s"val_$i")))
    rdd.registerAsTable("records")

    // Queries can then join RDD data with data stored in Hive.
    println("Result of SELECT *:")
    hql("SELECT * FROM records r JOIN sparkHiveTest s ON r.key = s.key").collect().foreach(println)
    
  }

}