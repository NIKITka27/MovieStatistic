package com.example.moviestatistics.demo.controller

import com.example.moviestatistics.demo.model.FilmEntity
import com.example.moviestatistics.demo.model.ReviewEntity
import com.example.moviestatistics.demo.repositories.FilmRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/film")
class FilmController(private val filmRepository: FilmRepository) {

    @GetMapping
    fun getFilms(model: Model) = ModelAndView().apply {
        model.addAttribute("films", filmRepository.findAll())
        this.viewName = "filmList"
    }

    @GetMapping("/add")
    fun addFilm(model: Model) = ModelAndView().apply {
        model.addAttribute("film", FilmEntity())
        this.viewName = "addFilm"
    }

    @PostMapping("/add")
    fun addFilm(@Validated filmEntity: FilmEntity, result: BindingResult, model: Model) = ModelAndView().apply {
        if (result.hasErrors()) {
            this.viewName = "addFilm"
        } else {
            this.viewName = "filmList"
            filmRepository.save(filmEntity)
            model.addAttribute("films", filmRepository.findAll())
        }
    }

    @GetMapping("edit/{id}")
    fun updateFilm(@PathVariable("id") id: Long, model: Model) = ModelAndView().apply {
        val filmEntity: FilmEntity = filmRepository.findById(id).orElseThrow() {
            RuntimeException("No find user by id: $id")
        }
        model.addAttribute("film", filmEntity)
        this.viewName = "updateFilm"
    }

    @PostMapping("update/{id}")
    fun updateFilm(
        @PathVariable("id") id: Long,
        @Validated filmEntity: FilmEntity,
        result: BindingResult,
        model: Model
    ) = ModelAndView().apply {
        if (result.hasErrors()) {
            model.addAttribute("film", filmEntity)
            this.viewName = "updateFilm"
        } else {
            filmRepository.save(filmEntity)
            model.addAttribute("films", filmRepository.findAll())
            this.viewName = "filmList"
        }
    }

    @GetMapping("delete/{id}")
    fun deleteFilm(@PathVariable("id") id: Long, model: Model) = ModelAndView().apply {
        val filmEntity: FilmEntity = filmRepository.findById(id).orElseThrow() {
            RuntimeException("No find user by id: $id")
        }
        filmRepository.delete(filmEntity)
        model.addAttribute("films", filmRepository.findAll())
        this.viewName = "filmList"
    }

    @GetMapping("/sort/{page}")
    fun sort(
        @PathVariable("page") page: Int,
        model: Model,
        reviewEntity: ReviewEntity
    ) = ModelAndView().apply {
        val films = filmRepository.findAllWithPaginationAndASCSort(PageRequest.of(page, 3))
        model.addAttribute("films", films)
        this.viewName = "filmList"
    }

    @GetMapping("/sortDec/{page}")
    fun sortDec(
        @PathVariable("page") page: Int,
        model: Model,
        reviewEntity: ReviewEntity
    ) = ModelAndView().apply {
        val films = filmRepository.findAllWithPaginationAndDescSort(PageRequest.of(page, 3))
        model.addAttribute("films", films)
        this.viewName = "filmList"
    }
}
