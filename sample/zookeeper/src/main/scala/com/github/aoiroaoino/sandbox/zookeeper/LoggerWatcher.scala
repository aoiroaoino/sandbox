package com.github.aoiroaoino.sandbox.zookeeper

import org.apache.zookeeper.Watcher
import org.apache.zookeeper.WatchedEvent
import org.apache.zookeeper.Watcher.Event.KeeperState._

trait LoggerWatcher {
  private val logger = org.slf4j.LoggerFactory.getLogger(this.getClass)

  private def applyWatcher(proc: WatchedEvent => Unit): Watcher =
    new Watcher {
      def process(event: WatchedEvent): Unit = proc(event)
    }

  def info(): Watcher =
    applyWatcher { e => logger.info(e.toString) }

  def warn(): Watcher =
    applyWatcher { e => logger.warn(e.toString) }

  def error(): Watcher =
    applyWatcher { e => logger.error(e.toString) }

  def log(): Watcher = applyWatcher { e =>
    e.getState match {
      case AuthFailed        => logger.error("AuthFailed")
      case ConnectedReadOnly => logger.info("ConnectedReadOnly")
      case Disconnected      => logger.info("Disconnected")
      case Expired           => logger.error("Expired")
      // case NoSyncConnected   => logger.error("NoSyncConnected")
      case SaslAuthenticated => logger.info("SaslAuthenticated")
      case SyncConnected     => logger.info("SyncConnected")
      case _ /* Unknown */   => logger.error("Unknown Error")
    }
  }
}
