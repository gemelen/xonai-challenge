package org.apache.spark.sql.catalyst.expressions

import org.apache.spark.sql.catalyst.InternalRow
import org.apache.spark.sql.catalyst.expressions.codegen.CodegenContext
import org.apache.spark.sql.catalyst.expressions.codegen.ExprCode

trait Unevaluable {

  def foldable(): Boolean = ???

  final def eval(input: InternalRow = null): Object = ???

  final def doGenCode(ctx: CodegenContext, ev: ExprCode): ExprCode = ???

}
