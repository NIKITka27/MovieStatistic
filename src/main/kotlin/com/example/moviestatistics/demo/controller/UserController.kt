package com.example.moviestatistics.demo.controller

import com.example.moviestatistics.demo.model.UserEntity
import com.example.moviestatistics.demo.repositories.UserRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/user")
class UserController(private var userRepository: UserRepository) {

    @GetMapping
    fun getUsers(model: Model) = ModelAndView().apply {
        model.addAttribute("users", userRepository.findAll())
        this.viewName = "userList"
    }

    @GetMapping("/add")
    fun addUser(model: Model) = ModelAndView().apply {
        model.addAttribute("user", UserEntity())
        this.viewName = "addUser"
    }

    @PostMapping("/add")
    fun addUser(@Validated userEntity: UserEntity, result: BindingResult, model: Model) = ModelAndView().apply {
        if (result.hasErrors()) {
            this.viewName = "addUser"
        } else {
            this.viewName = "userList"
            userRepository.save(userEntity)
            model.addAttribute("users", userRepository.findAll())
        }
    }

    @GetMapping("edit/{id}")
    fun updateUser(@PathVariable("id") id: Long, model: Model) = ModelAndView().apply {
        val userEntity: UserEntity = userRepository.findById(id).orElseThrow() {
            RuntimeException("No find user by id: $id")
        }
        model.addAttribute("user", userEntity)
        this.viewName = "updateUser"
    }

    @PostMapping("update/{id}")
    fun updateUser(
        @PathVariable("id") id: Long,
        @Validated userEntity: UserEntity,
        result: BindingResult,
        model: Model
    ) = ModelAndView().apply {
        if (result.hasErrors()) {
            model.addAttribute("user", userEntity)
            this.viewName = "updateUser"
        } else {
            userRepository.save(userEntity)
            model.addAttribute("users", userRepository.findAll())
            this.viewName = "userList"
        }
    }

    @GetMapping("delete/{id}")
    fun deleteUser(@PathVariable("id") id: Long, model: Model) = ModelAndView().apply {
        val userEntity: UserEntity = userRepository.findById(id).orElseThrow() {
            RuntimeException("No find user by id: $id")
        }
        userRepository.delete(userEntity)
        model.addAttribute("films", userRepository.findAll())
        this.viewName = "filmList"
    }
}
