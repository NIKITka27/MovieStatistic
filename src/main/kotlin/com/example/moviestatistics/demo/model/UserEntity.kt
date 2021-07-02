package com.example.moviestatistics.demo.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "user")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column
    var name: String? = null,

    @Column
    var email: String? = null,

    @Column
    var password: String? = null,

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "roles_id")]
    )
    var roles: MutableSet<RoleEntity> = mutableSetOf(),

    @OneToMany(
        mappedBy = "user",
        fetch = FetchType.LAZY,
        orphanRemoval = true,
        cascade = [CascadeType.ALL]
    )
    val reviewEntityUser: MutableList<ReviewEntity> = mutableListOf()
) {
    fun getAuthority(): MutableSet<GrantedAuthority> {
        return roles.stream().map { GrantedAuthority { name } }.collect(Collectors.toSet())
    }

}
