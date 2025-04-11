package com.sandbrick.sbp.service

import com.sandbrick.sbp.api.v1.currency.dto.CurrencyRequest
import com.sandbrick.sbp.domain.Currency
import com.sandbrick.sbp.exception.ResourceNotFoundException
import com.sandbrick.sbp.repository.CurrencyRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CurrencyService(
    private val currencyRepository: CurrencyRepository
) {
    @Transactional
    fun create(request: CurrencyRequest): Currency {
        val currency = Currency(
            name = request.name,
            code = request.code,
            symbol = request.symbol
        )
        return currencyRepository.save(currency)
    }

    @Transactional
    fun update(id: String, request: CurrencyRequest): Currency {
        val currency = currencyRepository.findById(id).orElseThrow {
            ResourceNotFoundException("Currency with id $id not found")
        }
        currency.name = request.name
        currency.code = request.code
        currency.symbol = request.symbol
        return currencyRepository.save(currency)
    }

    @Transactional
    fun delete(id: String) {
        currencyRepository.deleteById(id)
    }

    fun getAllPaged(page: Int, size: Int): Page<Currency> {
        val pageable = PageRequest.of(page, size, Sort.by("name").ascending())
        return currencyRepository.findAll(pageable)
    }

    fun getAll(): List<Currency> = currencyRepository.findAll(Sort.by("name").ascending())
}
