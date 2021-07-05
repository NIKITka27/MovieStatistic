package com.example.moviestatistics.demo.repositories

import com.example.moviestatistics.demo.model.ReviewEntity
import com.example.moviestatistics.demo.model.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
interface ReviewRepository : PagingAndSortingRepository<ReviewEntity, Long> {

    override fun findById(id: Long): Optional<ReviewEntity>

    @Query("SELECT avg(rating) from ReviewEntity WHERE (user = :user) and date > :from and date < :to ")
    fun findByRatingAvg(
        @Param("user") userEntity: UserEntity,
        @Param("from") from: LocalDate,
        @Param("to") to: LocalDate
    ): Double

    @Query("SELECT max (rating) from ReviewEntity WHERE (user = :user) and date > :from and date < :to ")
    fun findByRatingMax(
        @Param("user") userEntity: UserEntity,
        @Param("from") from: LocalDate,
        @Param("to") to: LocalDate
    ): Double

    @Query("SELECT min (rating) from ReviewEntity WHERE (user = :user) and date > :from and date < :to ")
    fun findByRatingMin(
        @Param("user") userEntity: UserEntity,
        @Param("from") from: LocalDate,
        @Param("to") to: LocalDate
    ): Double

    @Query("SELECT min (rating) from ReviewEntity WHERE (user = :user)")
    fun findByRatingMin(
        @Param("user") userEntity: UserEntity
    ): Double

    @Query("SELECT max (rating) from ReviewEntity WHERE (user = :user)")
    fun findByRatingMax(
        @Param("user") userEntity: UserEntity
    ): Double

    @Query("SELECT avg (rating) from ReviewEntity WHERE (user = :user)")
    fun findByRatingAvg(
        @Param("user") userEntity: UserEntity,
    ): Double

    fun findAllByOrderByDateAsc(pageable: Pageable?): Page<ReviewEntity?>?
    fun findAllByOrderByDateDesc(pageable: Pageable?): Page<ReviewEntity?>?
    fun findAllByDoneTrueOrderByDateAsc(pageable: Pageable?): Page<ReviewEntity?>?
    fun findAllByDoneTrueOrderByDateDesc(pageable: Pageable?): Page<ReviewEntity?>?
    fun findAllByDoneFalseOrderByDateAsc(pageable: Pageable?): Page<ReviewEntity?>?
    fun findAllByDoneFalseOrderByDateDesc(pageable: Pageable?): Page<ReviewEntity?>?
}
