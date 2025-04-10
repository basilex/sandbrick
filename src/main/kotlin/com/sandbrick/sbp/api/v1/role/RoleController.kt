package com.sandbrick.sbp.api.v1.role

import com.sandbrick.sbp.api.v1.role.dto.request.RoleRequest
import com.sandbrick.sbp.api.v1.role.dto.response.RoleResponse
import com.sandbrick.sbp.mapper.RoleMapper
import com.sandbrick.sbp.service.role.RoleService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/roles")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Role", description = "Role management operations")
class RoleController(
    private val roleService: RoleService,
    private val roleMapper: RoleMapper
) {
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get a role by ID")
    fun getById(@PathVariable id: String): RoleResponse =
        roleMapper.toResponse(roleService.getById(id))

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new role")
    fun create(@Valid @RequestBody request: RoleRequest): RoleResponse =
        roleMapper.toResponse(roleService.create(request))

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update an existing role")
    fun update(@PathVariable id: String, @Valid @RequestBody request: RoleRequest): RoleResponse =
        roleMapper.toResponse(roleService.update(id, request))

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a role by ID")
    fun delete(@PathVariable id: String) =
        roleService.delete(id)

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all roles with pagination")
    fun getAllPaged(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Page<RoleResponse> =
        roleService.getAllPaged(page, size).map(roleMapper::toResponse)

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all roles (no pagination)")
    fun getAll(): List<RoleResponse> =
        roleService.getAll().map(roleMapper::toResponse)
}
