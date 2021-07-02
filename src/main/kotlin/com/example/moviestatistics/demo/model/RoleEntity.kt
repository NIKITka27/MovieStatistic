package com.example.moviestatistics.demo.model

import org.springframework.security.core.GrantedAuthority
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.Table
import javax.persistence.Transient

@Entity
@Table(name = "role")
data class RoleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(name = "name")
    val roleName: String?,

    @Transient
    @ManyToMany(mappedBy = "roles")
    val userEntities: MutableSet<UserEntity>? = null
) : GrantedAuthority {

    override fun getAuthority(): String? {
        return roleName
    }
}
