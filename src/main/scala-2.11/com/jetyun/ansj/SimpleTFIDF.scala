package com.jetyun.ansj

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.mllib.feature.IDF
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD

object SimpleTFIDF {
  
  def main(args: Array[String]) {
    
    val sc = new SparkContext("local[2]", "First Spark App")

    val filePath = "/Users/seraph/code/workspace/ansj4s/output/results/part-00001"
    
    // Load documents (one per line).
    val documents: RDD[Seq[String]] = sc.textFile(filePath).map(_.split(" ").toSeq)

    val hashingTF = new HashingTF()
    val tf: RDD[Vector] = hashingTF.transform(documents)

    tf.cache()
    val idf = new IDF().fit(tf)
    val tfidf: RDD[Vector] = idf.transform(tf)
    tfidf.collect().foreach{x =>
      val v:Vector = x
      println(x)
    }
  }
  
}