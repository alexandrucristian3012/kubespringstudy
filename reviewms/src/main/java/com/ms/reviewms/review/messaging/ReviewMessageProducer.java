package com.ms.reviewms.review.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.ms.reviewms.review.Review;
import com.ms.reviewms.review.dto.ReviewMessage;

@Service
public class ReviewMessageProducer {
	private final RabbitTemplate rabbitTemplate;

	public ReviewMessageProducer(RabbitTemplate rabbitTemplate) {
		super();
		this.rabbitTemplate = rabbitTemplate;
	}
	
	public void sendMessage(Review review) {
		ReviewMessage reviewMessage = new ReviewMessage();
		reviewMessage.setId(review.getId());
		reviewMessage.setTitle(review.getTitle());
		reviewMessage.setDescription(review.getDescription());
		reviewMessage.setRating(review.getRating());
		reviewMessage.setCompanyId(review.getCompanyId());
		rabbitTemplate.convertAndSend("companyRatingQueue", reviewMessage);
	}
}
