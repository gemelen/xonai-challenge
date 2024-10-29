package org.apache.spark.sql.catalyst.plans.physical

import org.apache.spark.sql.catalyst.expressions.Expression

abstract class Distribution
case class ClusteredDistribution(expressions: Seq[Expression]) extends Distribution {
  def clustering: Seq[Expression]                              = ???
  def requireAllClusterKeys: Boolean                           = ???
  def areAllClusterKeysMatched(expr: Seq[Expression]): Boolean = ???
}
case class StatefulOpClusteredDistribution(expressions: Seq[Expression]) extends Distribution
