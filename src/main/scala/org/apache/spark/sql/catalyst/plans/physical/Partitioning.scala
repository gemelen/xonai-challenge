package org.apache.spark.sql.catalyst.plans.physical

import org.apache.spark.sql.catalyst.expressions.NamedExpression
import org.apache.spark.sql.types.DataType

trait Partitioning {

  def flatten(): Seq[Partitioning]                             = ???
  final def satisfies(required: Distribution): Boolean         = ???
  def satisfies0(required: Distribution): Boolean              = ???
  def project(projectList: Seq[NamedExpression]): Partitioning = ???

}
