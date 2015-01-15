Simple Spark Application
==============

A simple Spark application that counts the occurrence of each word in a corpus and then counts the
occurrence of each character in the most popular words.  Includes the same program implemented in
Java and Scala.

To make a jar:
    mvn package

To run spark sample on cluster
./spark-submit --class com.walmart.labs.pcs.normalize.KafkaWordCount uber-SparkStormKafkaTest-0.0.1-SNAPSHOT.jar zookeeperhosts(including port) spark testspark 1

This will run the application in a single local process.  If the cluster is running a Spark standalone
cluster manager, you can replace "--master local" with "--master spark://`<master host>`:`<master port>`".

If the cluster is running YARN, you can replace "--master local" with "--master yarn".


run storm KafkaSpoutTestTopology:

local:
mvn clean install
java -cp target/uber-SparkStormKafkaTest-0.0.1-SNAPSHOT.jar com.walmart.labs.pcs.normalize.KafkaSpoutTestTopology zookeeperhosts(including port)

cluster:
mvn clean package -P cluster
./storm jar uber-SparkStormKafkaTest-0.0.1-SNAPSHOT.jar com.walmart.labs.pcs.normalize.KafkaSpoutTestTopology zookeeperhosts(including port)