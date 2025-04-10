package com.sandbrick.sbp.service.auth

import com.sandbrick.sbp.domain.User
import com.sandbrick.sbp.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found: $username")

        val authorities = user.roles.map { SimpleGrantedAuthority("ROLE_${it.name}") }

        return org.springframework.security.core.userdetails.User(
            user.username,
            user.password,
            authorities
        )
    }

    fun loadUserEntity(username: String): User {
        return userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found: $username")
    }
}
