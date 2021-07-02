package com.example.moviestatistics.demo.security

import com.example.moviestatistics.demo.model.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class UserDetailsImpl(
    private val user: UserEntity
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return user.getAuthority()
    }

    override fun getPassword(): String {
        return user.password!!
    }

    override fun getUsername(): String {
        return user.email!!
    }

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}
