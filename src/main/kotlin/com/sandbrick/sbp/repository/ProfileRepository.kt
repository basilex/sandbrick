package com.sandbrick.sbp.repository

import com.sandbrick.sbp.domain.Profile
import org.springframework.data.jpa.repository.JpaRepository

interface ProfileRepository : JpaRepository<Profile, String> {
    fun findByUser_Id(userId: String): Profile?
}
