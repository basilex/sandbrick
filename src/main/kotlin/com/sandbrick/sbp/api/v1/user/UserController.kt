package com.sandbrick.sbp.api.v1.user

import com.sandbrick.sbp.api.v1.user.dto.UserRequest
import com.sandbrick.sbp.api.v1.user.dto.UserResponse
import com.sandbrick.sbp.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "User", description = "User management operations")
class UserController(
    private val userService: UserService
) {
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get user by ID (admin only)")
    fun getById(@PathVariable id: String): UserResponse = userService.getById(id)

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get current user info")
    fun getCurrentUser(@AuthenticationPrincipal user: UserDetails): UserResponse =
        userService.findByUsername(user.username)

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new user (admin only)")
    fun create(@Valid @RequestBody request: UserRequest): UserResponse =
        userService.create(request)

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.name")
    @Operation(summary = "Update a user by ID (admin or self)")
    fun update(
        @PathVariable id: String,
        @Valid @RequestBody request: UserRequest
    ): UserResponse = userService.update(id, request)

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a user by ID (admin only)")
    fun delete(@PathVariable id: String) = userService.delete(id)

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all users (admin only)")
    fun getAll(): List<UserResponse> = userService.getAll()
}
