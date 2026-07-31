package com.jobs_portal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/ai")
@CrossOrigin
public class AIController {

	@Value("${openrouter.api.key}")
	private String apiKey;

	@GetMapping("/suggest")
	public String chat(@RequestParam String skill) {

		String url = "https://openrouter.ai/api/v1/chat/completions";

		RestTemplate rt = new RestTemplate();

		// System Prompt
		Map<String, String> system = new HashMap<>();
		system.put("role", "system");
		system.put("content",
				"You are a friendly chatbot. \n" + "Rules:\n" + "-Reply ONLY to user's current message\n"
						+ "-DO NOT include examples in your answer" + "DO NOT mention 'AI' or 'user'"
						+ "keep replies understanding(more than 1 line usually)\n" + "BE natural and human like \n"
						+ "stay relavent to the question only \n" + "Use emojies");

		// User Prompt
		Map<String, String> user = new HashMap<>();
		user.put("role", "user");
		user.put("content", skill);

		// Messages
		List<Map<String, String>> messages = new ArrayList<>();
		messages.add(system);
		messages.add(user);

		// Request Body
		Map<String, Object> body = new HashMap<>();
		body.put("model", "deepseek/deepseek-chat");
		body.put("messages", messages);
		body.put("temperature", 0.7);
		body.put("max_tokens", 70);

		// Headers
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(apiKey);
		headers.setContentType(MediaType.APPLICATION_JSON);

		headers.add("HTTP-Referer", "http://localhost:8080");
		headers.add("X-Title", "AI Career Portal");

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

		try {

			ResponseEntity<Map> response = rt.postForEntity(url, entity, Map.class);

			List choices = (List) response.getBody().get("choices");

			Map choice = (Map) choices.get(0);

			Map message = (Map) choice.get("message");

			return message.get("content").toString();

		} catch (Exception e) {

			e.printStackTrace();
			return "Error : " + e.getMessage();
		}
	}

}
