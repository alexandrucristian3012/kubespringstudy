package com.ms.reviewms.review;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
	private ReviewService reviewService;

	public ReviewController(ReviewService reviewService) {
		super();
		this.reviewService = reviewService;
	}

	@GetMapping()
	public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId) {
		return new ResponseEntity<List<Review>>(reviewService.getAllReviews(companyId), HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<String> addReview(@RequestParam Long companyId, @RequestBody Review review) {
		if (reviewService.addReview(companyId, review)) {
			return new ResponseEntity<String>("Review added succesfully.", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Review not saved, company not found", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{reviewId}")
	public ResponseEntity<Review> getReview(@PathVariable Long reviewId) {
		return new ResponseEntity<>(reviewService.getReview(reviewId), HttpStatus.OK);
	}

	@PutMapping("/{reviewId}")
	public ResponseEntity<String> updateReview(@PathVariable Long reviewId,
			@RequestBody Review review) {

		if (reviewService.updateReview(reviewId, review)) {
			return new ResponseEntity<String>("Review updated succesfully.", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Review not saved, company not found", HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{reviewId}")
	public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
		if (reviewService.deleteReview(reviewId)) {
			return new ResponseEntity<String>("Review deleted succesfully.", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Review not deleted, company not found", HttpStatus.NOT_FOUND);
	}
}
