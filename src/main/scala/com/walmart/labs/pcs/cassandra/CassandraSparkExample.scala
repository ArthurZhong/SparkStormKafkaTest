package com.walmart.labs.pcs.cassandra

import com.datastax.spark.connector._
import org.apache.spark.{SparkContext, SparkConf}
import com.datastax.spark.connector.cql.CassandraConnector
/*
 * Created by pzhong1 on 12/1/14.
 */
object CassandraSparkExample {
  def main(args: Array[String]) {
    System.setProperty("spark.cassandra.query.retry.count", "1")
    val conf = new SparkConf(true).set("spark.cassandra.connection.host", "dev-cass01.sv.walmartlabs.com")
      .set("spark.cassandra.connection.native.port", "9042")
      .set("spark.cassandra.connection.keep_alive_ms", "500")
    val sc = new SparkContext("spark://Peide.local:7077", "testCasssandraConnector", conf)
    val rdd = sc.cassandraTable("PGAudit", "Audit").select("column1", "column2", "column3", "value").where("column1 = ?","request").take(2)
    //println(rdd.count)
    //println(rdd.first)
    rdd.toArray.foreach(println)
//    CassandraConnector(conf).withSessionDo { session =>
//      session.execute("select * from \"PGAudit\".\"Audit\" limit 1;")
//    }
  }
}
