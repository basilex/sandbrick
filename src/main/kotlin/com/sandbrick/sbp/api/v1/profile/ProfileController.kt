package com.sandbrick.sbp.api.v1.profile

import com.sandbrick.sbp.api.v1.profile.dto.ProfileRequest
import com.sandbrick.sbp.api.v1.profile.dto.ProfileResponse
import com.sandbrick.sbp.service.ProfileService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/profiles")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Profile", description = "User profile management operations")
class ProfileController(
    private val profileService: ProfileService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create a user profile")
    fun create(@Valid @RequestBody request: ProfileRequest): ProfileResponse =
        profileService.create(request)

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get a user profile by ID")
    fun getById(@PathVariable id: String): ProfileResponse =
        profileService.getById(id)

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update an existing user profile")
    fun update(@PathVariable id: String, @Valid @RequestBody request: ProfileRequest): ProfileResponse =
        profileService.update(id, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @Operation(summary = "Delete a profile by ID")
    fun delete(@PathVariable id: String) =
        profileService.delete(id)

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @Operation(summary = "Get all user profiles")
    fun getAll(): List<ProfileResponse> =
        profileService.getAll()
}
