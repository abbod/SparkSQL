package com.mayank.spark.example

import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession

object Patient {

  System.setProperty("hadoop.home.dir", "C:\\winutils");


  def main(args: Array[String]) {

    val sc=
      SparkSession.builder()
       .appName("SQL-Basic")
        .master("local[4]")
        .getOrCreate()

   val df = sc.read.format("com.databricks.spark.csv").option("header", "true").option("inferSchema", "true").load("inpatientCharges.csv")
    df.show()
    df.printSchema()

    println(df.groupBy("ProviderState").avg("AverageTotalPayments").orderBy("ProviderState").count())

    //Problem Statement 1: Find the amount of Average Covered Charges per state.

    df.groupBy("ProviderState").avg("AverageCoveredCharges").show

    //Problem Statement 2: Find the amount of Average Total Payments charges per state.

    df.groupBy("ProviderState").avg("AverageTotalPayments").show

    //Problem Statement 3: Find the amount of Average Medicare Payments charges per state.

    df.groupBy("ProviderState").avg("AverageMedicarePayments").show

    //Problem Statement 4: Find out the total number of Discharges per state and for each disease.

    df.groupBy("ProviderState","DRGDefinition").sum("TotalDischarges").show
    df.groupBy("ProviderState","DRGDefinition").sum("TotalDischarges").orderBy(desc(sum("TotalDischarges").toString)).show
  }
}
