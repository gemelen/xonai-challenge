package org.apache.spark.sql.catalyst.plans.physical

class HashShuffleSpec(val partitioning: Partitioning, val distribution: Distribution) extends ShuffleSpec
