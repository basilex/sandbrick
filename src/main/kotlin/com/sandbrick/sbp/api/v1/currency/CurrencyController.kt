package com.sandbrick.sbp.api.v1.currency

import com.sandbrick.sbp.api.v1.currency.dto.CreateCurrencyRequest
import com.sandbrick.sbp.api.v1.currency.dto.UpdateCurrencyRequest
import com.sandbrick.sbp.service.CurrencyService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/currencies")
class CurrencyController(
    private val service: CurrencyService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid request: CreateCurrencyRequest) =
        service.create(request)

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody @Valid request: UpdateCurrencyRequest) =
        service.update(id, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: String) =
        service.delete(id)

    @GetMapping
    fun getAll() = service.getAll()
}
