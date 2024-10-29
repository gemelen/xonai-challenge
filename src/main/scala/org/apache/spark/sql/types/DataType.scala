package org.apache.spark.sql.types

sealed abstract class DataType
case class IntegerType() extends DataType
case object IntegerType  extends DataType
