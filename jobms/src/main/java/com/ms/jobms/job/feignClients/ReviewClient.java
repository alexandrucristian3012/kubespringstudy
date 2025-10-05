package com.ms.jobms.job.feignClients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ms.jobms.job.external.Review;

@FeignClient(name = "reviewms", url="${review-service.url}")
public interface ReviewClient {
	@GetMapping("/reviews")
	List<Review> getReviews(@RequestParam("companyId") Long companyId);
}
