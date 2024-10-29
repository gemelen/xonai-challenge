package org.apache.spark.sql.catalyst.expressions

abstract class Expression extends Product {
  def semanticEquals(that: Expression): Boolean = ???
  override def toString(): String               = ???
  override def hashCode(): Int                  = ???
}
