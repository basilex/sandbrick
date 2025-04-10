
package com.sandbrick.sbp.repository

import com.sandbrick.sbp.domain.Country
import org.springframework.data.jpa.repository.JpaRepository

interface CountryRepository : JpaRepository<Country, String>
