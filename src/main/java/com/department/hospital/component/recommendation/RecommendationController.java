package com.department.hospital.component.recommendation;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;


@Profile("!test")
@RestController
@RequiredArgsConstructor
public class RecommendationController {

	private final static String RECOMMENDATION_PROMPT = """
			Can you recommend the doctor and the department based on the following patient's illness details - %s
			""";

	private final ChatClient chatClient;
	private final RecommendationService recommendationService;

	@PostMapping("/question")
	public RecommendationResponseDto recommendation(@RequestBody
	RecommendationRequestDto recommendationRequestDto) {
		final Prompt prompt = new Prompt(RECOMMENDATION_PROMPT.formatted(recommendationRequestDto.illnessDetails()));
		final String recommendation = chatClient.prompt(prompt).call().content();
		return new RecommendationResponseDto(recommendation);
	}

	@PostMapping("/load")
	public void loadDocuments() {
		recommendationService.loadDocuments();
	}
}