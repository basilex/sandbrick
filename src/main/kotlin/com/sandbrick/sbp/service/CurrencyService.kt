package com.sandbrick.sbp.service

import com.sandbrick.sbp.api.v1.currency.dto.CurrencyRequest
import com.sandbrick.sbp.api.v1.currency.dto.CurrencyResponse
import com.sandbrick.sbp.domain.currency.Currency
import com.sandbrick.sbp.exception.DuplicateEntityException
import com.sandbrick.sbp.exception.ResourceNotFoundException
import com.sandbrick.sbp.repository.CurrencyRepository
import org.springframework.stereotype.Service

@Service
class CurrencyService(
    private val repository: CurrencyRepository
) {
    fun create(request: CurrencyRequest): CurrencyResponse {
        if (repository.existsByCode(request.code)) {
            throw DuplicateEntityException("Currency with code ${request.code} already exists")
        }
        val currency = Currency(
            code = request.code.uppercase(),
            name = request.name,
            symbol = request.symbol
        )
        return repository.save(currency).toResponse()
    }

    fun update(id: String, request: CurrencyRequest): CurrencyResponse {
        val currency = repository.findById(id).orElseThrow { ResourceNotFoundException("Currency with id $id not found") }
        val updated = currency.copy(
            name = request.name,
            symbol = request.symbol
        )
        return repository.save(updated).toResponse()
    }

    fun delete(id: String) {
        if (!repository.existsById(id)) {
            throw ResourceNotFoundException("Currency with id $id not found")
        }
        repository.deleteById(id)
    }

    fun getAll(): List<CurrencyResponse> = repository.findAll().map { it.toResponse() }

    private fun Currency.toResponse() = CurrencyResponse(id.toString(), code, name, symbol)
}
