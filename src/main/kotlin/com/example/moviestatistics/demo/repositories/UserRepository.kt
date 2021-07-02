package com.example.moviestatistics.demo.repositories

import com.example.moviestatistics.demo.model.UserEntity
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : PagingAndSortingRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
}
