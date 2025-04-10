package com.sandbrick.sbp.domain.country

import com.sandbrick.sbp.domain.base.BaseAuditEntity
import com.sandbrick.sbp.domain.currency.Currency
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.*

@Entity
@Table(name = "country")
class Country(
    @Id val id: UUID = UUID.randomUUID(),

    @field:NotBlank(message = "Name must not be empty")
    @field:Size(min = 2, max = 255, message = "Name must be between 2 and 255 characters")
    @Column(nullable = false, unique = true)
    var name: String,

    @field:NotBlank(message = "ISO2 must not be empty")
    @field:Size(min = 2, max = 2, message = "ISO2 must be exactly 2 characters")
    @Column(nullable = false, unique = true)
    var iso2: String,

    @field:NotBlank(message = "ISO3 must not be empty")
    @field:Size(min = 3, max = 3, message = "ISO3 must be exactly 3 characters")
    @Column(nullable = false, unique = true)
    var iso3: String,

    @field:NotBlank(message = "Code must not be empty")
    @field:Size(min = 1, max = 10, message = "Code must be between 1 and 10 characters")
    @Column(nullable = false, unique = true)
    var code: String,

    @ManyToMany
    @JoinTable(
        name = "country_currency",
        joinColumns = [JoinColumn(name = "country_id")],
        inverseJoinColumns = [JoinColumn(name = "currency_id")]
    )
    val currencies: MutableSet<Currency> = mutableSetOf()
) : BaseAuditEntity()
