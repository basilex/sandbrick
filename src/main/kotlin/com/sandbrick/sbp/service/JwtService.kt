package com.sandbrick.sbp.service

import com.sandbrick.sbp.config.AppProperties
import com.sandbrick.sbp.domain.User
import com.sandbrick.sbp.domain.auth.TokenType
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService(
    private val appProperties: AppProperties
) {

    private val secretKey: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)
    private val expirationMs: Long = 1000 * 60 * 60 * 24 // 24 hours

    fun generateToken(user: User, type: TokenType): String {
        val claims = mutableMapOf<String, Any>(
            "roles" to user.roles.map { it.name },
            "type" to type.name.lowercase()
        )
        val now = Date()
        val expiration = when (type) {
            TokenType.ACCESS -> Date(now.time + 1000 * 60 * appProperties.token.accessMinutes)
            TokenType.REFRESH -> Date(now.time + 1000 * 60 * 60 * 24 * appProperties.token.refreshDays)
        }
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(user.username)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(secretKey)
            .compact()
    }

    fun extractUsername(token: String): String? {
        return extractClaims(token)?.subject
    }

    fun extractRoles(token: String): List<String> {
        return extractClaims(token)
            ?.get("roles", List::class.java)
            ?.map { it.toString() } ?: emptyList()
    }

    fun isTokenValid(token: String, username: String): Boolean {
        val claims = extractClaims(token)
        return claims?.subject == username && !claims.isExpired()
    }

    private fun extractClaims(token: String): Claims? {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (ex: Exception) {
            null
        }
    }

    private fun Claims.isExpired(): Boolean {
        return expiration.before(Date())
    }
}
