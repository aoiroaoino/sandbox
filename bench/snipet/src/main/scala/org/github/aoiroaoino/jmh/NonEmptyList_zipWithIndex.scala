package com.github.aoiroaoino.jmh

import org.openjdk.jmh.annotations._

import scalaz.NonEmptyList

class NonEmptyList_zipWithIndex {

  def nel = NonEmptyList(0, Stream.from(1).take(10000 - 1): _*)

  // https://github.com/julien-truffaut/Monocle/blob/v1.1.1/core/src/main/scala/monocle/std/NonEmptyList.scala#L30
  def monocle_zipWithIndex(n: NonEmptyList[Int]): NonEmptyList[(Int, Int)] =
    n.zip(NonEmptyList(0, Stream.from(1).take(n.size): _*))

  @Benchmark
  def monocle_v1_1_1_imp(): NonEmptyList[(Int, Int)] = {
    monocle_zipWithIndex(nel)
  }

  @Benchmark
  def scalaz_v7_1_4_imp(): NonEmptyList[(Int, Int)] = {
    nel.zipWithIndex
  }
}
