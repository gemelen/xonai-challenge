package org.apache.spark.sql.catalyst.expressions

class Pmod(val hash: Murmur3Hash, literal: Literal, pmod: Pmod = Pmod.default) extends Expression {

  override def canEqual(that: Any): Boolean = ???

  override def productElement(n: Int): Any = ???

  override def productArity: Int = ???

}

object Pmod {
  def `default`(): Pmod = ???
}
