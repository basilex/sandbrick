package com.sandbrick.sbp.api.v1.role

import com.sandbrick.sbp.api.v1.role.dto.request.RoleRequest
import com.sandbrick.sbp.api.v1.role.dto.response.RoleResponse
import com.sandbrick.sbp.service.role.RoleService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/roles")
class RoleController(
    private val roleService: RoleService
) {
    @GetMapping
    fun getAll(): List<RoleResponse> = roleService.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): RoleResponse = roleService.getById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody request: RoleRequest): RoleResponse =
        roleService.create(request)

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @Valid @RequestBody request: RoleRequest): RoleResponse =
        roleService.update(id, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: String) = roleService.delete(id)
}
