package com.sandbrick.sbp.api.v1.profile

import com.sandbrick.sbp.api.v1.profile.dto.ProfileRequest
import com.sandbrick.sbp.api.v1.profile.dto.ProfileResponse
import com.sandbrick.sbp.mapper.ProfileMapper
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
    private val profileService: ProfileService,
    private val profileMapper: ProfileMapper
) {
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all profiles")
    fun getAll(): List<ProfileResponse> =
        profileService.getAll().map(profileMapper::toResponse)

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get a profile by ID")
    fun getById(@PathVariable id: String): ProfileResponse =
        profileMapper.toResponse(profileService.getById(id))

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new profile")
    fun create(@Valid @RequestBody request: ProfileRequest): ProfileResponse {
        val profile = profileService.create(request)
        return profileMapper.toResponse(profile)
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update an existing profile")
    fun update(@PathVariable id: String, @Valid @RequestBody request: ProfileRequest): ProfileResponse {
        val profile = profileService.update(id, request)
        return profileMapper.toResponse(profile)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a profile by ID")
    fun delete(@PathVariable id: String) =
        profileService.delete(id)
}
