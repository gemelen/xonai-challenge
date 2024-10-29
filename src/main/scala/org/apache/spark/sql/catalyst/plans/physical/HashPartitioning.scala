package org.apache.spark.sql.catalyst.plans.physical

import scala.MatchError
import scala.collection.Seq

import org.apache.spark.sql.catalyst.expressions.Expression
import org.apache.spark.sql.catalyst.expressions.ExpressionProjection
import org.apache.spark.sql.catalyst.expressions.Literal
import org.apache.spark.sql.catalyst.expressions.Murmur3Hash
import org.apache.spark.sql.catalyst.expressions.NamedExpression
import org.apache.spark.sql.catalyst.expressions.Pmod
import org.apache.spark.sql.catalyst.expressions.Unevaluable
import org.apache.spark.sql.catalyst.plans
import org.apache.spark.sql.types.DataType
import org.apache.spark.sql.types.IntegerType

case class HashPartitioning(expressions: Seq[Expression], numPartitions: Int)
    extends Expression
    with Partitioning
    with Unevaluable
    with Serializable {

  def children: Seq[Expression] = expressions
  def nullable: Boolean         = false
  def dataType(): DataType      = IntegerType

  override def satisfies0(required: Distribution): Boolean = {
    (super.satisfies0(required)) || {
      required match {
        case c: ClusteredDistribution =>
          val distribution: ClusteredDistribution = c
          val requiredClustering: Seq[Expression] = distribution.clustering
          val requireAllClusterKeys: Boolean      = distribution.requireAllClusterKeys

          if (requireAllClusterKeys) {
            distribution.areAllClusterKeysMatched(this.expressions)
          } else { this.expressions.forall(x => requiredClustering.exists(y => y.semanticEquals(x))) }
        case StatefulOpClusteredDistribution(expressions) =>
          this.expressions.length == expressions.length && this.expressions
            .zip(expressions)
            .forall(_ match {
              case tt: Tuple2[Expression, Expression] =>
                val l = tt._1
                val r = tt._2
                l.semanticEquals(r)
            })
        case _ => false
      }
    }
  }

  override def project(projectList: Seq[NamedExpression]): Partitioning = ???

  def createShuffleSpec(distribution: ClusteredDistribution): ShuffleSpec = new HashShuffleSpec(this, distribution)

  def partitionIdExpression: Expression = new Pmod(new Murmur3Hash(this.expressions), Literal(this.numPartitions))

  def withNewChildrenInternal(newChildren: IndexedSeq[Expression]): HashPartitioning = this.copy(newChildren)

}
