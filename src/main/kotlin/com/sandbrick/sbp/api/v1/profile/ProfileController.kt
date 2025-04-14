package com.sandbrick.sbp.api.v1.profile

import com.sandbrick.sbp.api.v1.profile.dto.ProfileRequest
import com.sandbrick.sbp.api.v1.profile.dto.ProfileResponse
import com.sandbrick.sbp.mapper.ProfileMapper
import com.sandbrick.sbp.service.ProfileService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
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
    @Operation(
        summary = "Get all profiles",
        responses = [ApiResponse(responseCode = "200", description = "List of profiles")]
    )
    fun getAll(): List<ProfileResponse> =
        profileService.getAll().map(profileMapper::toResponse)

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Get a profile by ID",
        parameters = [Parameter(name = "id", description = "Profile ID", example = "abc123")],
        responses = [
            ApiResponse(responseCode = "200", description = "Profile found"),
            ApiResponse(responseCode = "404", description = "Profile not found")
        ]
    )
    fun getById(@PathVariable id: String): ProfileResponse =
        profileMapper.toResponse(profileService.getById(id))

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Create a new profile",
        requestBody = RequestBody(
            required = true,
            description = "Profile creation payload",
            content = [Content(schema = Schema(implementation = ProfileRequest::class))]
        ),
        responses = [ApiResponse(responseCode = "201", description = "Profile created successfully")]
    )
    fun create(@Valid @RequestBody request: ProfileRequest): ProfileResponse =
        profileMapper.toResponse(profileService.create(request))

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @profileSecurity.isOwner(#id, authentication.name)")
    @Operation(
        summary = "Update an existing profile",
        parameters = [Parameter(name = "id", description = "Profile ID to update")],
        requestBody = RequestBody(
            required = true,
            description = "Updated profile information",
            content = [Content(schema = Schema(implementation = ProfileRequest::class))]
        ),
        responses = [
            ApiResponse(responseCode = "200", description = "Profile updated"),
            ApiResponse(responseCode = "403", description = "Forbidden"),
            ApiResponse(responseCode = "404", description = "Profile not found")
        ]
    )
    fun update(@PathVariable id: String, @Valid @RequestBody request: ProfileRequest): ProfileResponse =
        profileMapper.toResponse(profileService.update(id, request))

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @profileSecurity.isOwner(#id, authentication.name)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Delete a profile by ID",
        parameters = [Parameter(name = "id", description = "Profile ID to delete")],
        responses = [
            ApiResponse(responseCode = "204", description = "Profile deleted successfully"),
            ApiResponse(responseCode = "403", description = "Forbidden"),
            ApiResponse(responseCode = "404", description = "Profile not found")
        ]
    )
    fun delete(@PathVariable id: String) =
        profileService.delete(id)
}
