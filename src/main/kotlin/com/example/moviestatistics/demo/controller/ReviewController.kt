package com.example.moviestatistics.demo.controller

import com.example.moviestatistics.demo.model.ReviewEntity
import com.example.moviestatistics.demo.repositories.FilmRepository
import com.example.moviestatistics.demo.repositories.ReviewRepository
import com.example.moviestatistics.demo.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import java.time.LocalDate


@Controller
@RequestMapping("/review")
class ReviewController {

    @Autowired
    lateinit var reviewRepository: ReviewRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var filmRepository: FilmRepository

    private var filterMethod = "ALL"
    private var sortDateMethod = "ASC"

    @GetMapping
    fun getReviews(model: Model, pageable: Pageable ): ModelAndView {
        val notePage: Page<ReviewEntity?>? = filterAndSort(pageable)
        val page: PageWrapper<ReviewEntity?> = PageWrapper<ReviewEntity?>(notePage!!, "/review")
        model.addAttribute("reviews", reviewRepository.findAll())
        model.addAttribute("notes", page.content);
        model.addAttribute("sort", sortDateMethod);
        model.addAttribute("filter", filterMethod);
        model.addAttribute("page", page);
        var modelAndView: ModelAndView = ModelAndView()
        modelAndView.viewName = "reviewList"

        return modelAndView;
    }

    @GetMapping("/add")
    fun addReview(model: Model): ModelAndView {
        model.addAttribute("films", filmRepository.findAll())
        model.addAttribute("users", userRepository.findAll())
        model.addAttribute("review", ReviewEntity())
        var modelAndView: ModelAndView = ModelAndView()
        modelAndView.viewName = "addReview"

        return modelAndView;
    }

    @PostMapping("/add")
    fun addReview(user: Long, film: Long, rating: Int, comment: String, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate, model: Model, pageable: Pageable ): ModelAndView {
        val notePage: Page<ReviewEntity?>? = filterAndSort(pageable)
        val page: PageWrapper<ReviewEntity?> = PageWrapper<ReviewEntity?>(notePage!!, "/review")
        val modelAndView: ModelAndView = ModelAndView()
        modelAndView.viewName = "reviewList"
        val review = ReviewEntity()
        review.user = userRepository.findById(user).orElseThrow { throw RuntimeException() }
        review.film = filmRepository.findById(film).orElseThrow { throw RuntimeException() }
        review.rating = rating
        review.comment = comment
        review.date = date
        reviewRepository.save(review)
        model.addAttribute("page", page);
        model.addAttribute("reviews", reviewRepository.findAll())

        return modelAndView;
    }

    @GetMapping("edit/{id}")
    fun updateReview(@PathVariable("id") id: Long, model: Model): ModelAndView {
        val reviewEntity: ReviewEntity = reviewRepository.findById(id).orElseThrow() {
            RuntimeException("No find user by id: $id")
        }
        model.addAttribute("review", reviewEntity)
        val modelAndView: ModelAndView = ModelAndView()
        modelAndView.viewName = "updateReview"

        return modelAndView

    }

    @PostMapping("update/{id}")
    fun updateReview(@PathVariable("id") id: Long, @Validated reviewEntity: ReviewEntity, result: BindingResult, model: Model): ModelAndView {
        val modelAndView: ModelAndView = ModelAndView()
        if (result.hasErrors()) {
            model.addAttribute("review", reviewEntity)
            modelAndView.viewName = "updateReview"
        } else {
            reviewRepository.save(reviewEntity)
            model.addAttribute("reviews", reviewRepository.findAll())
            modelAndView.viewName = "reviewList"
        }

        return modelAndView;
    }

    @GetMapping("delete/{id}")
    fun deleteReview(@PathVariable("id") id: Long, model: Model): ModelAndView {
        val modelAndView: ModelAndView = ModelAndView()
        val reviewEntity: ReviewEntity = reviewRepository.findById(id).orElseThrow() {
            RuntimeException("No find user by id: $id")
        }

        reviewRepository.delete(reviewEntity)
        model.addAttribute("reviews", reviewRepository.findAll())
        modelAndView.viewName = "reviewList"

        return modelAndView

    }

    @GetMapping("maxRating/{id}")
    fun getMaxRating(
            @PathVariable("id") id: Long,
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) from: LocalDate,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) to: LocalDate,
            model: Model
    ): ModelAndView {

        val user = userRepository.findById(id).get()
        val maxRating = reviewRepository.findByRatingMax(user, from, to)

        print(maxRating)
        model.addAttribute("user", user)
        model.addAttribute("maxRating", maxRating)
        var modelAndView: ModelAndView = ModelAndView()
            modelAndView.viewName = "maxRating"
        return modelAndView
    }

    @GetMapping("minRating/{id}")
    fun getMinRating(
            @PathVariable("id") id: Long,
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) from: LocalDate,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) to: LocalDate,
            model: Model
    ): ModelAndView {

        val user = userRepository.findById(id).get()
        val minRating = reviewRepository.findByRatingMin(user, from, to)

        print(minRating)
        model.addAttribute("minRating", minRating)
        var modelAndView: ModelAndView = ModelAndView()
        modelAndView.viewName = "minRating"
        return modelAndView
    }

    @GetMapping("avgRating/{id}")
    fun getAvgRating(
            @PathVariable("id") id: Long,
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) from: LocalDate,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) to: LocalDate,
            model: Model
    ): ModelAndView {

        val user = userRepository.findById(id).get()
        val avgRating = reviewRepository.findByRatingAvg(user, from, to)

        print(avgRating)
        model.addAttribute("avgRating", avgRating)
        var modelAndView: ModelAndView = ModelAndView()
        modelAndView.viewName = "avgRating"
        return modelAndView
    }

    @GetMapping("statistic/{id}")
    fun getStaistic(
        @PathVariable("id") id: Long,

        model: Model
    ): ModelAndView {

        val user = userRepository.findById(id).get()
        val minRating = reviewRepository.findByRatingMin(user)
        val maxRating = reviewRepository.findByRatingMax(user)
        val avgRating = reviewRepository.findByRatingAvg(user)
        print(maxRating)
        model.addAttribute("user", user)
        model.addAttribute("maxRating", maxRating)
        model.addAttribute("minRating", minRating)
        model.addAttribute("avgRating", avgRating)
        var modelAndView: ModelAndView = ModelAndView()
        modelAndView.viewName = "statisticsUser"
        return modelAndView
    }

    @GetMapping("/filter/{filter}")
    fun filterChoose(@PathVariable filter: String): String? {
        filterMethod = filter
        return "redirect:/review"
    }

    @GetMapping("/sort/{sortDate}")
    fun sortChoose(@PathVariable sortDate: String): String? {
        sortDateMethod = sortDate
        return "redirect:/review"
    }
    private fun filterAndSort(pageable: Pageable): Page<ReviewEntity?>? {
        var page: Page<ReviewEntity?>? = null
            when (filterMethod) {
            "ALL" -> when (sortDateMethod) {
                "ASC" -> page = reviewRepository.findAllByOrderByDateAsc(pageable)
                "DESC" -> page = reviewRepository.findAllByOrderByDateDesc(pageable)
            }
            "TRUE" -> when (sortDateMethod) {
                "ASC" -> page = reviewRepository.findAllByDoneTrueOrderByDateAsc(pageable)
                "DESC" -> page = reviewRepository.findAllByDoneTrueOrderByDateDesc(pageable)
            }
            "FALSE" -> when (sortDateMethod) {
                "ASC" -> page = reviewRepository.findAllByDoneFalseOrderByDateAsc(pageable)
                "DESC" -> page = reviewRepository.findAllByDoneFalseOrderByDateDesc(pageable)
            }
        }
        return page
    }

}