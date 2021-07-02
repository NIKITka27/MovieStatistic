package com.example.moviestatistics.demo.controller

import com.example.moviestatistics.demo.security.UserDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/admin")
class AdminController {

    @Autowired
    private val userService: UserDetailsServiceImpl? = null

    @GetMapping
    fun userList(model: Model): String? {
        model.addAttribute("allUsers", userService?.allUsers())
        return "admin"
    }

    @PostMapping
    fun deleteUser(
        @RequestParam(required = true, defaultValue = "") userId: Long?,
        @RequestParam(required = true, defaultValue = "") action: String,
        model: Model?
    ): String? {
        if (action == "delete") {
            userService?.deleteUser(userId)
        }
        return "redirect:/admin"
    }

//    @GetMapping("/admin/gt/{userId}")
//    fun gtUser(@PathVariable("userId") userId: Long?, model: Model): String? {
//        model.addAttribute("allUsers", userService.usergtList(userId))
//        return "admin"
//    }

}