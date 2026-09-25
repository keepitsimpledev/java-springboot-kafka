package com.exploration;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class MyApplication {
    
    @Value(value = "${spring.kafka.single-topic-name}")
    private String topicName;

	private final KafkaTemplate<String, String> kafkaTemplate;

	public MyApplication(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage(String message) {
		CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);
		future.whenComplete((result, ex) -> {
			if (ex == null) {
				System.out.println("Sent message=[" + message + 
				"] with offset=[" + result.getRecordMetadata().offset() + "]");
			} else {
				System.out.println("Unable to send message=[" + 
					message + "] due to : " + ex.getMessage());
			}
		});
	}

	@RequestMapping("/")
	public String home() {
		return "Hello World!";
	}

	@RequestMapping("/send/")
	public String send() {
		sendMessage("kmessage");
		return "send success.";
	}

	@KafkaListener(
		topics = "ktopic", // application.properties.spring.kafka.single-topic-name
		groupId = "11") // application.properties.spring.kafka.group-id
	public void listenGroupFoo(String message) {
		System.out.println("Received Message in group foo: " + message);
	}

	public static void main(String[] args) {
		SpringApplication.run(MyApplication.class, args);
	}

}
