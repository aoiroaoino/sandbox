package com.github.aoiroaoino.sandbox.zookeeper.utils

import java.lang.management.ManagementFactory

object Util {

  def getPID(): String =
    ManagementFactory.getRuntimeMXBean.getName.split('@')(0)
}
