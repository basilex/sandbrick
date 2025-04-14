package com.sandbrick.sbp.util

import java.net.InetAddress
import java.util.concurrent.atomic.AtomicInteger

/**
 * Xid – Compact, unique identifier inspired by MongoDB's ObjectId.
 * Format: [timestamp (8 hex)] + [machine ID (6 hex)] + [counter (6 hex)]
 */
object Xid {
    private val machineId: String = generateMachineId()
    private val counter = AtomicInteger(0)

    /**
     * Generates a unique 20-character identifier.
     * Combines Unix timestamp, machine-specific hash, and atomic counter.
     */
    fun generate(): String {
        val timestamp = (System.currentTimeMillis() / 1000).toInt()
        val count = counter.incrementAndGet() and 0xFFFFFF
        return "%08x%s%06x".format(timestamp, machineId, count)
    }

    /**
     * Generates a 6-character machine ID based on the hashed hostname.
     * Ensures stable uniqueness across machines.
     */
    private fun generateMachineId(): String {
        val hostname = try {
            InetAddress.getLocalHost().hostName
        } catch (ex: Exception) {
            "unknown"
        }
        return Integer.toHexString(hostname.hashCode())
            .take(6)
            .padStart(6, '0')
    }
}
