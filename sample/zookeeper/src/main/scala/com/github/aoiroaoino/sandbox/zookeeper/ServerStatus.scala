package com.github.aoiroaoino.sandbox.zookeeper

import com.github.aoiroaoino.sandbox.zookeeper.utils.Util

class BidServerStatus extends ZKClientBase {

  val prefix = "status"
  val client = ZKClient.default

  val pid = Util.getPID

  def addStatus() = add(pid, "active".getBytes)

  def existsStatus(): Unit = {
    println(exists(pid))
  }

}
