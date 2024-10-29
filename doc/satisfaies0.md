Control flow for the `satisfies0` function isn't trivial for the untrained eye, its reconstruction took me a few steps.
Here I'm trying to document them:

1. Initial step - reconstruct it as it goes, using if-else, with the understanding that it won't be an idiomatic code at all. 
The flow looks like

```
 super.satisfies0 == false =>
   StatefulOpClusteredDistribution => <code>
   not StatefulOpClusteredDistribution:
     ClusteredDistribution but not StatefulOpClusteredDistribution => <code>
     not ClusteredDistribution nor StatefulOpClusteredDistribution => false
 super.satisfies0 == true => <skip>
```

and Java code from the provided file looks like


```
    boolean var10000;
    if (!Partitioning.satisfies0$(this, required)) {
      boolean var2;
      if (!(required instanceof StatefulOpClusteredDistribution)) {
        if (required instanceof ClusteredDistribution) {
          ...
          var2 = ...
        } else {
          var2 = false;
        }
      } else {
        ...
        var2 = ...
      }

      if (!var2) {
        var10000 = false;
        return var10000;
      }
    }

    var10000 = true;
    return var10000;
```

2. Reconstructed function at first looks like this:

Scala code

```
    val res = if (!super.satisfies0(required)) {
      if (!(required.isInstanceOf[StatefulOpClusteredDistribution])) {
        if (required.isInstanceOf[ClusteredDistribution]) {
          // "ClusteredDistribution but not StatefulOpClusteredDistribution"
          val distribution: ClusteredDistribution = required.asInstanceOf[ClusteredDistribution]
          val requiredClustering: Seq[Expression] = distribution.clustering
          val requireAllClusterKeys: Boolean      = distribution.requireAllClusterKeys

          if (requireAllClusterKeys) {
            distribution.areAllClusterKeysMatched(this.expressions)
          } else { this.expressions.forall(x => requiredClustering.exists(y => y.semanticEquals(x))) }
        } else {
          // "not ClusteredDistribution nor StatefulOpClusteredDistribution"
          false
        }
      } else {
        // "StatefulOpClusteredDistribution"
        val distribution: StatefulOpClusteredDistribution = required.asInstanceOf[StatefulOpClusteredDistribution]
        this.expressions.length == distribution.expressions.length && this.expressions
          .zip(distribution.expressions)
          .forall(_ match {
            case tt: Tuple2[Expression, Expression] =>
              val l = tt._1
              val r = tt._2
              l.semanticEquals(r)
          })
      }
    } else true

    res
```

which turns into a FernFlower (CFR's version just has better labels) version like

```
      boolean var10000;
      if (!Partitioning.satisfies0$(this, required)) {
         if (!(required instanceof StatefulOpClusteredDistribution)) {
            if (required instanceof ClusteredDistribution) {
               ...
               var10000 = ...
            } else {
               var10000 = false;
            }
         } else {
            var10000 = ...
         }
      } else { var10000 = true; }

      boolean res = var10000;
      return res;
```

3. It is obvious that Scala origin with its Java result differs in a way that could not be fixed by adding a variable or moving expressions, as it at least lacks an additional condition. Keeping in mind that nested if-else expressions are not Scala idiomatic, nor easy to model and grasp, the next step is to reintroduce more idiomatic approach by using pattern matching.
We already saw such an example in the code, and by the fact of lacking the MatchError exception here, we should add an exhaustive match expression.

After that Scala code looks like (note that we inverse first predicate and change logical operator, because we started with the `NOT (super.satisfies0 == true) AND nested code`)

```
    (super.satisfies0(required)) || {
      required match {
        case c: ClusteredDistribution =>
          val distribution: ClusteredDistribution = c
          val requiredClustering: Seq[Expression] = distribution.clustering
          val requireAllClusterKeys: Boolean      = distribution.requireAllClusterKeys

          if (requireAllClusterKeys) { distribution.areAllClusterKeysMatched(this.expressions)
          } else { this.expressions.forall(x => requiredClustering.exists(y => y.semanticEquals(x))) }
        case StatefulOpClusteredDistribution(expressions) =>
          this.expressions.length == expressions.length && this.expressions
            .zip(expressions) .forall(_ match {
              case tt: Tuple2[Expression, Expression] => val l = tt._1 val r = tt._2 l.semanticEquals(r)
            })
        case _ => false
      }
    }
```

and translates into 

```
      boolean var10;
      if (!Partitioning.satisfies0$(this, required)) {
         if (required instanceof ClusteredDistribution) {
            ...
            var10 = ...
         } else if (required instanceof StatefulOpClusteredDistribution) {
            ...
            var10 = ...
         } else { var10 = false; }

         if (!var10) {
            var10 = false;
            return var10;
         }
      }

      var10 = true;
      return var10;
```

which is what we expected to achieve.
