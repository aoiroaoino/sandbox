package com.github.aoiroaoino.sandbox.zookeeper

import org.apache.zookeeper.ZooKeeper
import org.apache.zookeeper.Watcher
import org.apache.zookeeper.WatchedEvent
import org.apache.zookeeper.ZooDefs.Ids._
import org.apache.zookeeper.ZooDefs.Perms._
import org.apache.zookeeper.data.ACL
import org.apache.zookeeper.CreateMode

import com.github.aoiroaoino.sandbox.zookeeper.watcher.LoggerWatcher

object ZKConfig {
  val rootPath = "/sandbox/zktest"
  val host: String = "localhost"
  val port: Int    = 2181
}

case class ZKClient private (private val underlying: ZooKeeper) {
  def apply(host: String, port: Int) = ZKClient(new ZooKeeper(host, port, null))
  def connection = underlying
}

object ZKClient {
  def default() = ZKClient(new ZooKeeper("localhost", 2181, null))
}

trait ZKClientBase {
  import scala.collection.JavaConversions._

  def prefix: String
  def client: ZKClient

  def fqdn(p: String): String = List(ZKConfig.rootPath, prefix, p).mkString("/")

  def acls = List(new ACL(ALL, ANYONE_ID_UNSAFE))

  def add(path: String, data: Array[Byte]): Unit =
    client.connection.create(List(ZKConfig.rootPath, prefix, path).mkString("/"), data, acls, CreateMode.PERSISTENT)

  def exists(path: String): Boolean =
    if (client.connection.exists(fqdn(path), true) != null) true else false
}

case class ZooKeeperClient(host: String, port: Int, timeout: Int = 10000) extends LoggerWatcher {
  import scala.concurrent.Future

  val connString = s"""$host:$port"""

  def connectionStatus(): WatcherCont[Unit] =
    WatcherCont { _ =>
      new ZooKeeper(connString, timeout, log())
      Future.successful(Unit)
    }


}
