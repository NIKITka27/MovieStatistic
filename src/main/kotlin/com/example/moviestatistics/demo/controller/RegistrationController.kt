package com.example.moviestatistics.demo.controller

import com.example.moviestatistics.demo.model.UserEntity
import com.example.moviestatistics.demo.repositories.RoleRepository
import com.example.moviestatistics.demo.repositories.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.ModelAndView
import java.util.Collections

@Controller
class RegistrationController(
    private var userRepository: UserRepository,
    private var roleRepository: RoleRepository
) {

    fun passwordEncoder() = BCryptPasswordEncoder(12)

    @GetMapping("/registration")
    fun registration(model: Model): ModelAndView {
        model.addAttribute("user", UserEntity())
        return ModelAndView().apply {
            this.viewName = "registration"
        }
    }

    @PostMapping("/registration")
    fun registrationUser(@Validated userEntity: UserEntity, model: Model, result: BindingResult): ModelAndView {
        val modelAndView: ModelAndView = ModelAndView()
        val userEntityFromDb = userRepository.findByEmail(userEntity.email!!)

        when {
            userEntityFromDb != null -> modelAndView.viewName = "registration"
            result.hasErrors() -> modelAndView.viewName = "registration"
            else -> {
                userEntity.roles = Collections.singleton(roleRepository.findById(2L).get())
                userEntity.password = passwordEncoder().encode(userEntity.password)
                userRepository.save(userEntity)
                modelAndView.viewName = "login"
            }
        }

        return modelAndView
    }
}
