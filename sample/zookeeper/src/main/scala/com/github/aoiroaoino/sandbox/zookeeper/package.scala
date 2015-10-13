package com.github.aoiroaoino.sandbox

import scala.concurrent.Future
import scalaz.{ContT, Cont}

package object zookeeper {

  type WatcherCont[A] = ContT[Future, Unit, A]

  object WatcherCont {
    def apply[A](f: (A => Future[Unit]) => Future[Unit]): WatcherCont[A] =
      ContT.apply[Future, Unit, A](f)
  }
}
