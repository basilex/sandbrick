package com.sandbrick.sbp.util

import java.util.concurrent.atomic.AtomicInteger

object Xid {
    private val machineId = generateMachineId()
    private val counter = AtomicInteger(0)

    fun generate(): String {
        val timestamp = System.currentTimeMillis() / 1000
        val count = counter.incrementAndGet() and 0xFFFFFF
        return "%08x%s%06x".format(timestamp, machineId, count)
    }

    private fun generateMachineId(): String {
        val hostname = try {
            java.net.InetAddress.getLocalHost().hostName
        } catch (e: Exception) {
            "unknown"
        }
        return Integer.toHexString(hostname.hashCode()).take(6).padStart(6, '0')
    }
}
