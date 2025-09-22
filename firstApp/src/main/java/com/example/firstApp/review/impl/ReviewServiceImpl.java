package com.example.firstApp.review.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.firstApp.company.Company;
import com.example.firstApp.company.CompanyService;
import com.example.firstApp.review.Review;
import com.example.firstApp.review.ReviewRepository;
import com.example.firstApp.review.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;
	private final CompanyService companyService;

	public ReviewServiceImpl(ReviewRepository reviewRepository, CompanyService companyService) {
		this.reviewRepository = reviewRepository;
		this.companyService = companyService;
	}

	@Override
	public List<Review> getAllReviews(Long companyId) {
		List<Review> reviews = reviewRepository.findByCompanyId(companyId);
		return reviews;
	}

	@Override
	public boolean addReview(Long companyId, Review review) {
		Company company = companyService.getCompanyById(companyId);
		if (company != null) {
			review.setCompany(company);
			reviewRepository.save(review);
			return true;
		}
		return false;
	}

	@Override
	public Review getReview(Long companyId, Long reviewId) {
		List<Review> reviews = reviewRepository.findByCompanyId(companyId);
		return reviews.stream()
				.filter(review -> review.getId().equals(reviewId))
				.findFirst()
				.orElse(null);
	}

	@Override
	public boolean updateReview(Long companyId, Long reviewId, Review updatedReview) {
		if(companyService.getCompanyById(companyId) != null) {
			updatedReview.setCompany(companyService.getCompanyById(companyId));
			updatedReview.setId(reviewId);
			reviewRepository.save(updatedReview);
			return true;
			
		}
		return false;
	}

	@Override
	public boolean deleteReview(Long id, Long reviewId) {
		if(companyService.getCompanyById(id) != null && reviewRepository.existsById(reviewId)) {
			Review review = reviewRepository.findById(reviewId).orElse(null);
			Company company = review.getCompany();
			company.getReviews().remove(review);
			review.setCompany(null);
			companyService.updateCompany(company.getId(), company);
			reviewRepository.deleteById(reviewId);
			return true;
		}
		return false;
	}

}
