package org.apache.spark.sql.catalyst.expressions

class ExpressionProjection(val projectList: Seq[NamedExpression]) {
  def replaceWithAlias(e: Expression): Seq[Any] = ???
}
