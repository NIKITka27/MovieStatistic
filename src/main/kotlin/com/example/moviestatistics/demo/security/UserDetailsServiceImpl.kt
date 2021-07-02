package com.example.moviestatistics.demo.security

import com.example.moviestatistics.demo.model.UserEntity
import com.example.moviestatistics.demo.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext


@Service
class UserDetailsServiceImpl(private var userRepository: UserRepository) : UserDetailsService {
    @PersistenceContext
    private val em: EntityManager? = null

    override fun loadUserByUsername(email: String): UserDetails {
        val userEntity: UserEntity = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("user not found")
        return UserDetailsImpl(userEntity)
    }
    fun findUserById(userId: Long?): UserEntity? {
        val userFromDb: Optional<UserEntity> = userRepository.findById(userId!!)
        return userFromDb.orElse(UserEntity())
    }
    fun allUsers(): MutableIterable<UserEntity> {
        return userRepository.findAll()
    }

    fun deleteUser(userId: Long?): Boolean {
        if (userRepository.findById(userId!!).isPresent) {
            userRepository.deleteById(userId)
            return true
        }
        return false
    }

//    fun usergtList(idMin: Long?): List<UserRepository?>? {
//        return em?.createQuery("SELECT u FROM user u WHERE u.id > :paramId", UserRepository::class.java)
//            ?.setParameter("paramId", idMin)?.resultList
//    }
}
