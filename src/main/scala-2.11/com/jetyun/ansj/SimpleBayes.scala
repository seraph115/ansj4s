package com.jetyun.ansj

import org.apache.spark.mllib.classification.NaiveBayes
import org.apache.spark.mllib.classification.NaiveBayesModel
import org.apache.spark.SparkContext
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.mllib.regression.LabeledPoint

object SimpleBayes {

  def main(args: Array[String]) {

    val sc = new SparkContext("local[2]", "First Spark App")
    val data = sc.textFile("/Users/seraph/Code/workspace/ansj4s/input/sample_naive_bayes_data.txt")
    
    val parsedData = data.map { line =>
      val parts = line.split(',')
      LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(' ').map(_.toDouble)))
    }
    
    // Split data into training (60%) and test (40%).
    val splits = parsedData.randomSplit(Array(0.6, 0.4), seed = 11L)
    val training = splits(0)
    val test = splits(1)

    val model = NaiveBayes.train(training, lambda = 1.0, modelType = "multinomial")

    val predictionAndLabel = test.map(p => (model.predict(p.features), p.label))
    val accuracy = 1.0 * predictionAndLabel.filter(x => x._1 == x._2).count() / test.count()
    println("Accuracy: " + accuracy)

    // Save and load model
    // model.save(sc, "model")
    // val sameModel = NaiveBayesModel.load(sc, "model")
    
    val tempV1 = Vectors.dense(0.0, 4.0 ,0.0)
    println("Results01: " + model.predict(tempV1))
    
    val tempV2 = Vectors.dense(0.0, 0.0, 2.0)
    println("Results02: " + model.predict(tempV2))
    
    val tempV3 = Vectors.dense(2.0, 0.0 ,0.0)
    println("Results03: " + model.predict(tempV3))
  }

}