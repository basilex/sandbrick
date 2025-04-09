
package com.sandbrick.sbp.api.v1.country

import com.sandbrick.sbp.api.v1.country.dto.CreateCountryRequest
import com.sandbrick.sbp.api.v1.country.dto.UpdateCountryRequest
import com.sandbrick.sbp.service.CountryService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/countries")
@Tag(name = "Country", description = "Country control")
class CountryController(
    private val countryService: CountryService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid request: CreateCountryRequest) =
        countryService.create(request)

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody @Valid request: UpdateCountryRequest) =
        countryService.update(id, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: String) =
        countryService.delete(id)

    @GetMapping
    fun getAll() = countryService.getAll()
}
