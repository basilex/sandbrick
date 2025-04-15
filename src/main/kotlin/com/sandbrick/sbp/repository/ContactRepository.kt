package com.sandbrick.sbp.repository

import com.sandbrick.sbp.domain.Contact
import com.sandbrick.sbp.domain.contact.ContactType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ContactRepository : JpaRepository<Contact, String> {
    @Query(
        value = """
        SELECT * FROM contact 
         WHERE content = :content 
           AND type = CAST(:type AS contact_type)
    """,
        nativeQuery = true
    )
    fun findByContentAndType(
        @Param("content") content: String,
        @Param("type") type: String
    ): Contact?


    // alternative (but I don't like underscores in names)
    // fun findAllByUser_Id(userId: String): List<Contact>

    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
    fun findAllByUserId(@Param("userId") userId: String): List<Contact>

    @Query("""
    SELECT c FROM Contact c
     WHERE (:userId IS NULL OR c.user.id = :userId)
       AND (:type IS NULL OR c.type = :type)
       AND (:preferrable IS NULL OR c.preferrable = :preferrable)
     """)
    fun findByFilters(
        @Param("userId") userId: String?,
        @Param("type") type: ContactType?,
        @Param("preferrable") preferrable: Boolean?,
        pageable: Pageable
    ): Page<Contact>
}
