package com.example.moviestatistics.demo.repositories

import com.example.moviestatistics.demo.model.RoleEntity
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : PagingAndSortingRepository<RoleEntity, Long>
