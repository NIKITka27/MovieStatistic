package com.example.moviestatistics.demo.repositories

import com.example.moviestatistics.demo.model.FilmEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface FilmRepository : PagingAndSortingRepository<FilmEntity, Long> {

    override fun findById(id: Long): Optional<FilmEntity>

    @Query(
        "select f.id, f.name from film f join review r on f.id = r.film_id Group by f.id order by avg(r.rating) DESC",
        nativeQuery = true
    )
    fun findAllWithPaginationAndDescSort(pageable: Pageable): List<FilmEntity>

    @Query(
        "select f.id, f.name from film f join review r on f.id = r.film_id Group by f.id order by avg(r.rating) ASC",
        nativeQuery = true
    )
    fun findAllWithPaginationAndASCSort(pageable: Pageable): List<FilmEntity>
}
