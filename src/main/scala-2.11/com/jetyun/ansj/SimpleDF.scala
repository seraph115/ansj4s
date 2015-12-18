package com.jetyun.ansj

import org.apache.spark.SparkContext

object SampleDF {

  def main(args: Array[String]) {

    val sc = new SparkContext("local[2]", "First Spark App")
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    
    val df = sqlContext.read.json("/Users/seraph/Code/workspace/ansj4s/input/people.json")
    df.show()
    df.printSchema()
    df.select("name").show()
    df.select(df("name"), df("age") + 1).show()
    df.filter(df("age") > 21).show()
    df.groupBy("age").count().show()
    
    df.registerTempTable("people")
    val teenagers = sqlContext.sql("SELECT name, age FROM people WHERE age >= 13 AND age <= 19")
    teenagers.map(t => "Name: " + t(0)).collect().foreach(println)
    teenagers.map(t => "Name: " + t.getAs[String]("name")).collect().foreach(println)
    teenagers.map(_.getValuesMap[Any](List("name", "age"))).collect().foreach(println)
    
    val parquetDF = sqlContext.read.load("/Users/seraph/Code/workspace/ansj4s/input/users.parquet")
    parquetDF.printSchema()
    parquetDF.select("name", "favorite_color").collect().foreach(println)
    // parquetDF.select("name", "favorite_color").write.save("/Users/seraph/Code/workspace/ansj4s/output/namesAndFavColors.parquet")

    val jdbcDF = sqlContext.read.format("jdbc").options(Map("url" -> "jdbc:mysql://127.0.0.1:3306/test?user=root&password=","dbtable" -> "test.dogs")).load()
    jdbcDF.registerTempTable("dogs")
    val dogs = sqlContext.sql("SELECT id, name FROM dogs")
    dogs.map(t => "Name: " + t(1)).collect().foreach(println)
    dogs.show()
    
  }

}