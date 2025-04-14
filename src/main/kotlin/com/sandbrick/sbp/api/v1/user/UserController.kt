package com.sandbrick.sbp.api.v1.user

import com.sandbrick.sbp.api.v1.user.dto.UserDetailedResponse
import com.sandbrick.sbp.api.v1.user.dto.UserRequest
import com.sandbrick.sbp.api.v1.user.dto.UserSummaryResponse
import com.sandbrick.sbp.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
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
    @Operation(
        summary = "Get summary info for all users",
        description = "Returns a brief overview (ID, username, roles) of all users. Admin only."
    )
    fun getSummary(): List<UserSummaryResponse> =
        userService.getAllSummaries()

    @GetMapping("/detailed")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Get detailed info for all users",
        description = "Returns detailed user data including contact info. Admin only."
    )
    fun getAll(): List<UserDetailedResponse> =
        userService.getAllDetailed()

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Get user details by ID",
        description = "Fetch a specific user's full details. Admin only."
    )
    fun getById(
        @Parameter(description = "User ID", example = "c7f0e0a1e9r0vlu1")
        @PathVariable id: String
    ): UserDetailedResponse =
        userService.getById(id)

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @Operation(
        summary = "Get current authenticated user info",
        description = "Returns information about the currently logged in user."
    )
    fun getCurrentUser(
        @AuthenticationPrincipal user: UserDetails
    ): UserDetailedResponse =
        userService.findByUsername(user.username)

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Create a new user",
        description = "Allows an admin to create a new user."
    )
    fun create(
        @Valid @RequestBody request: UserRequest
    ): UserDetailedResponse =
        userService.create(request)

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.name")
    @Operation(
        summary = "Update user",
        description = "Allows an admin or the user themself to update the user profile."
    )
    fun update(
        @Parameter(description = "User ID", example = "c7f0e0a1e9r0vlu1")
        @PathVariable id: String,
        @Valid @RequestBody request: UserRequest
    ): UserDetailedResponse =
        userService.update(id, request)

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Delete a user by ID",
        description = "Deletes the specified user. Admin only."
    )
    fun delete(
        @Parameter(description = "User ID", example = "c7f0e0a1e9r0vlu1")
        @PathVariable id: String
    ) =
        userService.delete(id)
}
