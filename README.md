# Scala/Java reverse engineering challenge

## Prerequisites

Build expects CFR decompiler as a `cfr.jar` and FernFlower decompiler as a `fernflower.jar` provided at `lib/` directory.

NB: I had to disable tests to build FernFlower, as there was one failing.

## Task

There is a Java class provided to reverse engineer its Scala original class, knowing that the Java one was obtained by decompilation.
If anything, all what was expressed is in the [Java class](./doc/HashPartitioning.java) file

## Summary

The process of completing the challenge is documented in the [process](./doc/process.md) file with further links.

Results are [HashPartitioning.scala](./src/main/scala/org/apache/spark/sql/catalyst/plans/physical/HashPartitioning.scala) and other classes in the `src` directory that were reconstructed to the best deducible state, using only the provided Java class and no knowledge of Apache Spark, as declared per task definition.

## Notes

Company's reviewer didn't know how to evaluate thought process, be aware to provide what they *think* they asked you to do by that task
