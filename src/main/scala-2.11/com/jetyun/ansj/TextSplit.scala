/**
 *
 */
package com.jetyun.ansj

import java.util.Arrays
import scala.Range
import org.ansj.splitWord.analysis.NlpAnalysis
import org.ansj.util.FilterModifWord
import org.apache.spark.SparkContext
import org.ansj.recognition.NatureRecognition

/**
 * @author seraph
 *
 */
object TextSplit {

  def main(args: Array[String]) {

    val inputpath = "/Users/seraph/Code/workspace/ansj4s/input/gname.txt"
    val outputpath = "/Users/seraph/Code/workspace/ansj4s/output/gname"
 
    val sc = new SparkContext("local[2]", "First Spark App")
    val patitions = 1

    val text = sc.textFile(inputpath, patitions).map { x =>

      val temp = NlpAnalysis.parse(x)
      // val temp = ToAnalysis.parse(x)

      FilterModifWord.insertStopWords(Arrays.asList("r", "n"))
      FilterModifWord.insertStopNatures("w", null, "ns", "r", "u", "e")
      val filter = FilterModifWord.modifResult(temp)
      
      new NatureRecognition(filter).recognition();
      println(filter);

      val word = for (i <- Range(0, filter.size())) yield filter.get(i).getName + "/" + filter.get(i).getNatureStr
      word.mkString("\t")
    }
    text.saveAsTextFile(outputpath)
  }

}