package com.sandbrick.sbp.api.v1.user

import com.sandbrick.sbp.api.v1.user.dto.UserDetailedResponse
import com.sandbrick.sbp.api.v1.user.dto.UserRequest
import com.sandbrick.sbp.api.v1.user.dto.UserSummaryResponse
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
    @GetMapping("/summary")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get summary info for all users")
    fun getSummary(): List<UserSummaryResponse> =
        userService.getAllSummaries()

    @GetMapping("/detailed")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get detailed info for all users")
    fun getAll(): List<UserDetailedResponse> =
        userService.getAllDetailed()

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get user details by ID (admin only)")
    fun getById(@PathVariable id: String): UserDetailedResponse =
        userService.getById(id)

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get current authenticated user info")
    fun getCurrentUser(@AuthenticationPrincipal user: UserDetails): UserDetailedResponse =
        userService.findByUsername(user.username)

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new user (admin only)")
    fun create(@Valid @RequestBody request: UserRequest): UserDetailedResponse =
        userService.create(request)

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.name")
    @Operation(summary = "Update user (admin or self)")
    fun update(
        @PathVariable id: String,
        @Valid @RequestBody request: UserRequest
    ): UserDetailedResponse = userService.update(id, request)

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a user by ID")
    fun delete(@PathVariable id: String) =
        userService.delete(id)
}
