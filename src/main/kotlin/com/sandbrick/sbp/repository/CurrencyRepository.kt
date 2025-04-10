
package com.sandbrick.sbp.repository

import com.sandbrick.sbp.domain.Currency
import org.springframework.data.jpa.repository.JpaRepository

interface CurrencyRepository : JpaRepository<Currency, String> {
    fun findByCode(code: String): Currency?
    fun existsByCode(code: String): Boolean
}