package com.example.firstApp.review;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/companies/{id}")
public class ReviewController {
	private ReviewService reviewService;

	public ReviewController(ReviewService reviewService) {
		super();
		this.reviewService = reviewService;
	}

	@GetMapping("/reviews")
	public ResponseEntity<List<Review>> getAllReviews(@PathVariable Long id) {
		return new ResponseEntity<List<Review>>(reviewService.getAllReviews(id), HttpStatus.OK);
	}

	@PostMapping("/reviews")
	public ResponseEntity<String> addReview(@PathVariable Long id, @RequestBody Review review) {
		if (reviewService.addReview(id, review)) {
			return new ResponseEntity<String>("Review added succesfully.", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Review not saved, company not found", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/reviews/{reviewId}")
	public ResponseEntity<Review> getReview(@PathVariable Long id, @PathVariable Long reviewId) {
		return new ResponseEntity<>(reviewService.getReview(id, reviewId), HttpStatus.OK);
	}

	@PutMapping("/reviews/{reviewId}")
	public ResponseEntity<String> updateReview(@PathVariable Long id, @PathVariable Long reviewId,
			@RequestBody Review review) {

		if (reviewService.updateReview(id, reviewId, review)) {
			return new ResponseEntity<String>("Review updated succesfully.", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Review not saved, company not found", HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/reviews/{reviewId}")
	public ResponseEntity<String> deleteReview(@PathVariable Long id, @PathVariable Long reviewId) {
		if (reviewService.deleteReview(id, reviewId)) {
			return new ResponseEntity<String>("Review deleted succesfully.", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Review not deleted, company not found", HttpStatus.NOT_FOUND);
	}
}
