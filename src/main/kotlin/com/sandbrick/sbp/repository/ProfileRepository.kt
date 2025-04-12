package com.sandbrick.sbp.repository

import com.sandbrick.sbp.domain.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ProfileRepository : JpaRepository<Profile, String> {
//    alternative
//    fun findByUser_Id(userId: String): Profile?

    @Query("SELECT p FROM Profile p WHERE p.user.id = :userId")
    fun findByUserId(@Param("userId") userId: String): Profile?
}
