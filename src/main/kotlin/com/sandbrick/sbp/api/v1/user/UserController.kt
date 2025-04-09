package com.sandbrick.sbp.api.v1.user

import com.sandbrick.sbp.api.v1.user.dto.UserRequest
import com.sandbrick.sbp.api.v1.user.dto.UserResponse
import com.sandbrick.sbp.service.UserService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User", description = "Users control")
class UserController(
    private val userService: UserService
) {
    @GetMapping
    fun getAll(): List<UserResponse> = userService.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): UserResponse =
        userService.getById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody request: UserRequest): UserResponse =
        userService.create(request)

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @Valid @RequestBody request: UserRequest): UserResponse =
        userService.update(id, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: String) = userService.delete(id)
}
