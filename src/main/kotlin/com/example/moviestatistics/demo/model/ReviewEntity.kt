package com.example.moviestatistics.demo.model

import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "review")
class ReviewEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: UserEntity? = null,

    @ManyToOne
    @JoinColumn(name = "film_id")
    var film: FilmEntity? = null,

    @Column
    var rating: Int? = null,

    @Column
    var comment: String? = null,

    @Column(name = "created_at")
    var date: LocalDate? = null,

    @Column(name = "done")
    var done: Boolean? = true
)
