package com.walmart.labs.pcs.normalize

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.mllib.util.MLUtils

object SparkMllibExample {
  
  def main(args: Array[String]) {
      
      val sc = new SparkContext(new SparkConf().setAppName("Spark MLlib Example"))
      
      if (args.length != 1) {
          System.err.println("Usage: java SparkMllibExample [inputFileName]")
          System.exit(1)
      }

      // Load and parse the data file.
      // Cache the data since we will use it again to compute training error.
      val data = MLUtils.loadLibSVMFile(sc, args(0)).cache()
      
      // Train a DecisionTree model.
      //  Empty categoricalFeaturesInfo indicates all features are continuous.
      val numClasses = 2
      val categoricalFeaturesInfo = Map[Int, Int]()
      val impurity = "gini"
      val maxDepth = 5
      val maxBins = 100
    
      val model = DecisionTree.trainClassifier(data, numClasses, categoricalFeaturesInfo, impurity, maxDepth, maxBins)
      
      // Evaluate model on training instances and compute training error
      val labelAndPreds = data.map { point =>
          val prediction = model.predict(point.features)
          (point.label, prediction)
      }
      
      val trainErr = labelAndPreds.filter(r => r._1 != r._2).count.toDouble / data.count
      println("Training Error = " + trainErr)
      println("Learned classification tree model:\n" + model)
  }
}