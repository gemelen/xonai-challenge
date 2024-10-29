/**
 * Understanding decompiled Scala code =========================================
 *
 * To complete this exercise rewrite this Java file as Scala 2 code making it as
 * readable as possible. Assumptions about classes not defined in the file
 * should be made. Refrain from looking at the Apache Spark source code.
 * 
 * IMPORTANT: Add comments explaining your decisions.
 */

package org.apache.spark.sql.catalyst.plans.physical;

import java.lang.invoke.SerializedLambda;
import org.apache.spark.sql.catalyst.InternalRow;
import org.apache.spark.sql.catalyst.expressions.Expression;
import org.apache.spark.sql.catalyst.expressions.ExpressionProjection;
import org.apache.spark.sql.catalyst.expressions.Murmur3Hash;
import org.apache.spark.sql.catalyst.expressions.NamedExpression;
import org.apache.spark.sql.catalyst.expressions.Pmod;
import org.apache.spark.sql.catalyst.expressions.Unevaluable;
import org.apache.spark.sql.catalyst.expressions.codegen.CodegenContext;
import org.apache.spark.sql.catalyst.expressions.codegen.ExprCode;
import org.apache.spark.sql.catalyst.trees.TreeNode;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.IntegerType.;
import scala.Function1;
import scala.MatchError;
import scala.Option;
import scala.Serializable;
import scala.Tuple2;
import scala.collection.IndexedSeq;
import scala.collection.IterableLike;
import scala.collection.Iterator;
import scala.collection.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(
  bytes = "\u0006\u0001\u0005\u0005g\u0001\u0002\u0010 \u0001:B\u0001B\r\u0001\u0003\u0016\u0004%\t!\u0012\u0005\t%\u0002\u0011\t\u0012)A\u0005\r\"A1\u000b\u0001BK\u0002\u0013\u0005A\u000b\u0003\u0005Y\u0001\tE\t\u0015!\u0003V\u0011\u0015I\u0006\u0001\"\u0001[\u0011\u0015q\u0006\u0001\"\u0011F\u0011\u0015y\u0006\u0001\"\u0011a\u0011\u0015!\u0007\u0001\"\u0011f\u0011\u0015a\u0007\u0001\"\u0011n\u0011\u0015\u0019\b\u0001\"\u0011u\u0011\u0015Y\b\u0001\"\u0011}\u0011\u001d\tY\u0001\u0001C\u0001\u0003\u001bAq!a\u0004\u0001\t#\n\t\u0002C\u0005\u0002\u001e\u0001\t\t\u0011\"\u0001\u0002 !I\u0011Q\u0005\u0001\u0012\u0002\u0013\u0005\u0011q\u0005\u0005\n\u0003{\u0001\u0011\u0013!C\u0001\u0003\u007fA\u0011\"a\u0011\u0001\u0003\u0003%\t%!\u0012\t\u0011\u0005]\u0003!!A\u0005\u0002QC\u0011\"!\u0017\u0001\u0003\u0003%\t!a\u0017\t\u0013\u0005\u001d\u0004!!A\u0005B\u0005%\u0004\"CA<\u0001\u0005\u0005I\u0011AA=\u0011%\ti\bAA\u0001\n\u0003\nyhB\u0005\u0002\u0004~\t\t\u0011#\u0001\u0002\u0006\u001aAadHA\u0001\u0012\u0003\t9\t\u0003\u0004Z1\u0011\u0005\u0011Q\u0013\u0005\n\u0003/C\u0012\u0011!C#\u00033C\u0011\"a'\u0019\u0003\u0003%\t)!(\t\u0013\u0005\r\u0006$!A\u0005\u0002\u0006\u0015\u0006\"CA\\1\u0005\u0005I\u0011BA]\u0005AA\u0015m\u001d5QCJ$\u0018\u000e^5p]&twM\u0003\u0002!C\u0005A\u0001\u000f[=tS\u000e\fGN\u0003\u0002#G\u0005)\u0001\u000f\\1og*\u0011A%J\u0001\tG\u0006$\u0018\r\\=ti*\u0011aeJ\u0001\u0004gFd'B\u0001\u0015*\u0003\u0015\u0019\b/\u0019:l\u0015\tQ3&\u0001\u0004ba\u0006\u001c\u0007.\u001a\u0006\u0002Y\u0005\u0019qN]4\u0004\u0001M1\u0001aL\u001b:y\t\u0003\"\u0001M\u001a\u000e\u0003ER!AM\u0012\u0002\u0017\u0015D\bO]3tg&|gn]\u0005\u0003iE\u0012!\"\u0012=qe\u0016\u001c8/[8o!\t1t'D\u0001 \u0013\tAtD\u0001\u0007QCJ$\u0018\u000e^5p]&tw\r\u0005\u00021u%\u00111(\r\u0002\f+:,g/\u00197vC\ndW\r\u0005\u0002>\u00016\taHC\u0001@\u0003\u0015\u00198-\u00197b\u0013\t\teHA\u0004Qe>$Wo\u0019;\u0011\u0005u\u001a\u0015B\u0001#?\u00051\u0019VM]5bY&T\u0018M\u00197f+\u00051\u0005cA$P_9\u0011\u0001*\u0014\b\u0003\u00132k\u0011A\u0013\u0006\u0003\u00176\na\u0001\u0010:p_Rt\u0014\"A \n\u00059s\u0014a\u00029bG.\fw-Z\u0005\u0003!F\u00131aU3r\u0015\tqe(\u0001\u0007fqB\u0014Xm]:j_:\u001c\b%A\u0007ok6\u0004\u0016M\u001d;ji&|gn]\u000b\u0002+B\u0011QHV\u0005\u0003/z\u00121!\u00138u\u00039qW/\u001c)beRLG/[8og\u0002\na\u0001P5oSRtDcA.];B\u0011a\u0007\u0001\u0005\u0006e\u0015\u0001\rA\u0012\u0005\u0006'\u0016\u0001\r!V\u0001\tG\"LG\u000e\u001a:f]\u0006Aa.\u001e7mC\ndW-F\u0001b!\ti$-\u0003\u0002d}\t9!i\\8mK\u0006t\u0017\u0001\u00033bi\u0006$\u0016\u0010]3\u0016\u0003\u0019\u0004\"a\u001a6\u000e\u0003!T!![\u0013\u0002\u000bQL\b/Z:\n\u0005-D'\u0001\u0003#bi\u0006$\u0016\u0010]3\u0002\u0015M\fG/[:gS\u0016\u001c\b\u0007\u0006\u0002b]\")q.\u0003a\u0001a\u0006A!/Z9vSJ,G\r\u0005\u00027c&\u0011!o\b\u0002\r\t&\u001cHO]5ckRLwN\\\u0001\baJ|'.Z2u)\t)T\u000fC\u0003w\u0015\u0001\u0007q/A\u0006qe>TWm\u0019;MSN$\bcA$PqB\u0011\u0001'_\u0005\u0003uF\u0012qBT1nK\u0012,\u0005\u0010\u001d:fgNLwN\\\u0001\u0012GJ,\u0017\r^3TQV4g\r\\3Ta\u0016\u001cGcA?\u0002\u0002A\u0011aG`\u0005\u0003\u007f~\u00111b\u00155vM\u001adWm\u00159fG\"9\u00111A\u0006A\u0002\u0005\u0015\u0011\u0001\u00043jgR\u0014\u0018NY;uS>t\u0007c\u0001\u001c\u0002\b%\u0019\u0011\u0011B\u0010\u0003+\rcWo\u001d;fe\u0016$G)[:ue&\u0014W\u000f^5p]\u0006)\u0002/\u0019:uSRLwN\\%e\u000bb\u0004(/Z:tS>tW#A\u0018\u0002/]LG\u000f\u001b(fo\u000eC\u0017\u000e\u001c3sK:Le\u000e^3s]\u0006dGcA.\u0002\u0014!9\u0011QC\u0007A\u0002\u0005]\u0011a\u00038fo\u000eC\u0017\u000e\u001c3sK:\u0004BaRA\r_%\u0019\u00111D)\u0003\u0015%sG-\u001a=fIN+\u0017/\u0001\u0003d_BLH#B.\u0002\"\u0005\r\u0002b\u0002\u001a\u000f!\u0003\u0005\rA\u0012\u0005\b':\u0001\n\u00111\u0001V\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\"!!\u000b+\u0007\u0019\u000bYc\u000b\u0002\u0002.A!\u0011qFA\u001d\u001b\t\t\tD\u0003\u0003\u00024\u0005U\u0012!C;oG\",7m[3e\u0015\r\t9DP\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BA\u001e\u0003c\u0011\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*\"!!\u0011+\u0007U\u000bY#A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0003\u000f\u0002B!!\u0013\u0002T5\u0011\u00111\n\u0006\u0005\u0003\u001b\ny%\u0001\u0003mC:<'BAA)\u0003\u0011Q\u0017M^1\n\t\u0005U\u00131\n\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR!\u0011QLA2!\ri\u0014qL\u0005\u0004\u0003Cr$aA!os\"A\u0011QM\n\u0002\u0002\u0003\u0007Q+A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0003W\u0002b!!\u001c\u0002t\u0005uSBAA8\u0015\r\t\tHP\u0001\u000bG>dG.Z2uS>t\u0017\u0002BA;\u0003_\u0012\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR\u0019\u0011-a\u001f\t\u0013\u0005\u0015T#!AA\u0002\u0005u\u0013AB3rk\u0006d7\u000fF\u0002b\u0003\u0003C\u0011\"!\u001a\u0017\u0003\u0003\u0005\r!!\u0018\u0002!!\u000b7\u000f\u001b)beRLG/[8oS:<\u0007C\u0001\u001c\u0019'\u0011A\u0012\u0011\u0012\"\u0011\u000f\u0005-\u0015\u0011\u0013$V76\u0011\u0011Q\u0012\u0006\u0004\u0003\u001fs\u0014a\u0002:v]RLW.Z\u0005\u0005\u0003'\u000biIA\tBEN$(/Y2u\rVt7\r^5p]J\"\"!!\"\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!a\u0012\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u000bm\u000by*!)\t\u000bIZ\u0002\u0019\u0001$\t\u000bM[\u0002\u0019A+\u0002\u000fUt\u0017\r\u001d9msR!\u0011qUAZ!\u0015i\u0014\u0011VAW\u0013\r\tYK\u0010\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u000bu\nyKR+\n\u0007\u0005EfH\u0001\u0004UkBdWM\r\u0005\t\u0003kc\u0012\u0011!a\u00017\u0006\u0019\u0001\u0010\n\u0019\u0002\u0017I,\u0017\r\u001a*fg>dg/\u001a\u000b\u0003\u0003w\u0003B!!\u0013\u0002>&!\u0011qXA&\u0005\u0019y%M[3di\u0002"
)
public class HashPartitioning extends Expression implements Partitioning, Unevaluable, Serializable {
  private final Seq<Expression> expressions;
  private final int numPartitions;

  public static Option<Tuple2<Seq<Expression>, Object>> unapply(final HashPartitioning x$0) {
    return HashPartitioning$.MODULE$.unapply(x$0);
  }

  public static Function1<Tuple2<Seq<Expression>, Object>, HashPartitioning> tupled() {
    return HashPartitioning$.MODULE$.tupled();
  }

  public static Function1<Seq<Expression>, Function1<Object, HashPartitioning>> curried() {
    return HashPartitioning$.MODULE$.curried();
  }

  public final boolean foldable() {
    return Unevaluable.foldable$(this);
  }

  public final Object eval(final InternalRow input) {
    return Unevaluable.eval$(this, input);
  }

  public final InternalRow eval$default$1() {
    return Unevaluable.eval$default$1$(this);
  }

  public final ExprCode doGenCode(final CodegenContext ctx, final ExprCode ev) {
    return Unevaluable.doGenCode$(this, ctx, ev);
  }

  public final boolean satisfies(final Distribution required) {
    return Partitioning.satisfies$(this, required);
  }

  public Seq<Partitioning> flatten() {
    return Partitioning.flatten$(this);
  }

  public Seq<Expression> expressions() {
    return this.expressions;
  }

  public int numPartitions() {
    return this.numPartitions;
  }

  public Seq<Expression> children() {
    return this.expressions();
  }

  public boolean nullable() {
    return false;
  }

  public DataType dataType() {
    return .MODULE$;
  }

  public boolean satisfies0(final Distribution required) {
    boolean var10000;
    if (!Partitioning.satisfies0$(this, required)) {
      boolean var2;
      if (!(required instanceof StatefulOpClusteredDistribution)) {
        if (required instanceof ClusteredDistribution) {
          ClusteredDistribution var5 = (ClusteredDistribution)required;
          Seq requiredClustering = var5.clustering();
          boolean requireAllClusterKeys = var5.requireAllClusterKeys();
          var2 = requireAllClusterKeys ? var5.areAllClusterKeysMatched(this.expressions()) : this.expressions().forall((x) -> {
            return BoxesRunTime.boxToBoolean($anonfun$satisfies0$2(requiredClustering, x));
          });
        } else {
          var2 = false;
        }
      } else {
        StatefulOpClusteredDistribution var4 = (StatefulOpClusteredDistribution)required;
        var2 = this.expressions().length() == var4.expressions().length() && ((IterableLike)this.expressions().zip(var4.expressions(), scala.collection.Seq..MODULE$.canBuildFrom())).forall((x0$1) -> {
          return BoxesRunTime.boxToBoolean($anonfun$satisfies0$1(x0$1));
        });
      }

      if (!var2) {
        var10000 = false;
        return var10000;
      }
    }

    var10000 = true;
    return var10000;
  }

  public Partitioning project(final Seq<NamedExpression> projectList) {
    ExpressionProjection projection = new ExpressionProjection(projectList);
    Seq projectedExprs = (Seq)this.expressions().map((expression) -> {
      return projection.replaceWithAlias(expression);
    }, scala.collection.Seq..MODULE$.canBuildFrom());
    Object var10000;
    if (projectedExprs.exists((x$7) -> {
      return BoxesRunTime.boxToBoolean($anonfun$project$2(x$7));
    })) {
      var10000 = new UnknownPartitioning(this.numPartitions());
    } else {
      label25: {
        Seq newExpressions = (Seq)projectedExprs.map((x$8) -> {
          return (Expression)x$8.head();
        }, scala.collection.Seq..MODULE$.canBuildFrom());
        Seq var5 = this.expressions();
        if (newExpressions == null) {
          if (var5 == null) {
            break label25;
          }
        } else if (newExpressions.equals(var5)) {
          break label25;
        }

        var10000 = new HashPartitioning(newExpressions, this.numPartitions());
        return (Partitioning)var10000;
      }

      var10000 = this;
    }

    return (Partitioning)var10000;
  }

  public ShuffleSpec createShuffleSpec(final ClusteredDistribution distribution) {
    return new HashShuffleSpec(this, distribution);
  }

  public Expression partitionIdExpression() {
    return new Pmod(new Murmur3Hash(this.expressions()), org.apache.spark.sql.catalyst.expressions.Literal..MODULE$.apply(BoxesRunTime.boxToInteger(this.numPartitions())), org.apache.spark.sql.catalyst.expressions.Pmod..MODULE$.apply$default$3());
  }

  public HashPartitioning withNewChildrenInternal(final IndexedSeq<Expression> newChildren) {
    return this.copy(newChildren, this.copy$default$2());
  }

  public HashPartitioning copy(final Seq<Expression> expressions, final int numPartitions) {
    return new HashPartitioning(expressions, numPartitions);
  }

  public Seq<Expression> copy$default$1() {
    return this.expressions();
  }

  public int copy$default$2() {
    return this.numPartitions();
  }

  public String productPrefix() {
    return "HashPartitioning";
  }

  public int productArity() {
    return 2;
  }

  public Object productElement(final int x$1) {
    Object var10000;
    switch (x$1) {
      case 0:
        var10000 = this.expressions();
        break;
      case 1:
        var10000 = BoxesRunTime.boxToInteger(this.numPartitions());
        break;
      default:
        throw new IndexOutOfBoundsException(Integer.toString(x$1));
    }

    return var10000;
  }

  public Iterator<Object> productIterator() {
    return scala.runtime.ScalaRunTime..MODULE$.typedProductIterator(this);
  }

  public boolean canEqual(final Object x$1) {
    return x$1 instanceof HashPartitioning;
  }

  public boolean equals(final Object x$1) {
    boolean var6;
    if (this != x$1) {
      label55: {
        boolean var2;
        if (x$1 instanceof HashPartitioning) {
          var2 = true;
        } else {
          var2 = false;
        }

        if (var2) {
          label38: {
            label37: {
              HashPartitioning var4 = (HashPartitioning)x$1;
              Seq var10000 = this.expressions();
              Seq var5 = var4.expressions();
              if (var10000 == null) {
                if (var5 != null) {
                  break label37;
                }
              } else if (!var10000.equals(var5)) {
                break label37;
              }

              if (this.numPartitions() == var4.numPartitions() && var4.canEqual(this)) {
                var6 = true;
                break label38;
              }
            }

            var6 = false;
          }

          if (var6) {
            break label55;
          }
        }

        var6 = false;
        return var6;
      }
    }

    var6 = true;
    return var6;
  }

  // $FF: synthetic method
  // $FF: bridge method
  public TreeNode withNewChildrenInternal(final IndexedSeq newChildren) {
    return this.withNewChildrenInternal(newChildren);
  }

  // $FF: synthetic method
  public static final boolean $anonfun$satisfies0$1(final Tuple2 x0$1) {
    if (x0$1 != null) {
      Expression l = (Expression)x0$1._1();
      Expression r = (Expression)x0$1._2();
      boolean var1 = l.semanticEquals(r);
      return var1;
    } else {
      throw new MatchError(x0$1);
    }
  }

  // $FF: synthetic method
  public static final boolean $anonfun$satisfies0$3(final Expression x$22, final Expression x$6) {
    return x$6.semanticEquals(x$22);
  }

  // $FF: synthetic method
  public static final boolean $anonfun$satisfies0$2(final Seq requiredClustering$1, final Expression x) {
    return requiredClustering$1.exists((x$6) -> {
      return BoxesRunTime.boxToBoolean($anonfun$satisfies0$3(x, x$6));
    });
  }

  // $FF: synthetic method
  public static final boolean $anonfun$project$2(final Seq x$7) {
    return x$7.isEmpty();
  }

  public HashPartitioning(final Seq<Expression> expressions, final int numPartitions) {
    this.expressions = expressions;
    this.numPartitions = numPartitions;
    Partitioning.$init$(this);
    Unevaluable.$init$(this);
  }

  // $FF: synthetic method
  private static Object $deserializeLambda$(SerializedLambda var0) {
    return Class.lambdaDeserialize<invokedynamic>(var0);
  }
}


package org.apache.spark.sql.catalyst.plans.physical;

import org.apache.spark.sql.catalyst.expressions.Expression;
import scala.Option;
import scala.Serializable;
import scala.Some;
import scala.Tuple2;
import scala.None.;
import scala.collection.Seq;
import scala.runtime.AbstractFunction2;
import scala.runtime.BoxesRunTime;

public final class HashPartitioning$ extends AbstractFunction2<Seq<Expression>, Object, HashPartitioning> implements Serializable {
  public static HashPartitioning$ MODULE$;

  static {
    new HashPartitioning$();
  }

  public final String toString() {
    return "HashPartitioning";
  }

  public HashPartitioning apply(final Seq<Expression> expressions, final int numPartitions) {
    return new HashPartitioning(expressions, numPartitions);
  }

  public Option<Tuple2<Seq<Expression>, Object>> unapply(final HashPartitioning x$0) {
    return (Option)(x$0 == null ? .MODULE$ : new Some(new Tuple2(x$0.expressions(), BoxesRunTime.boxToInteger(x$0.numPartitions()))));
  }

  private Object readResolve() {
    return MODULE$;
  }

  // $FF: synthetic method
  // $FF: bridge method
  public Object apply(final Object v1, final Object v2) {
    return this.apply((Seq)v1, BoxesRunTime.unboxToInt(v2));
  }

  private HashPartitioning$() {
    MODULE$ = this;
  }
}
