package com.sandbrick.sbp.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class XidTest {

    @Test
    fun `generated Xid should be 20 characters long`() {
        val xid = Xid.generate()
        assertEquals(20, xid.length, "Xid must be 20 characters long")
    }

    @Test
    fun `generated Xid should be unique`() {
        val generated = mutableSetOf<String>()
        repeat(10000) {
            val xid = Xid.generate()
            assertFalse(generated.contains(xid), "Duplicate Xid found: $xid")
            generated.add(xid)
        }
    }

    @Test
    fun `Xid should contain only hex characters`() {
        val xid = Xid.generate()
        assertTrue(xid.matches(Regex("^[0-9a-fA-F]{20}$")), "Xid should be hexadecimal")
    }
}
