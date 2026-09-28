package com.exploration;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	Logger logger = LoggerFactory.getLogger(MyApplication.class);
    
    @Value(value = "${spring.kafka.single-topic-name}")
    private String topicName;

	private final KafkaTemplate<String, String> kafkaTemplate;
	private final List<String> messages;

	public MyApplication(KafkaTemplate<String, String> kafkaTemplate, List<String> messages) {
		this.kafkaTemplate = kafkaTemplate;
		this.messages = messages;
	}

	public void sendMessage(String message) {
		CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);
		future.whenComplete((result, ex) -> {
			if (ex == null) {
				String logMsg = String.format("Sent message=[%s] with offset=[%d]",
						message, result.getRecordMetadata().offset());
				System.out.println(logMsg);
				logger.info(logMsg);
			} else {
				String logMsg = String.format("Unable to send message=[%s] due to : %s",
						message, ex.getMessage());
				System.out.println(logMsg);
				logger.error(logMsg);
			}
		});
	}

	@RequestMapping("/")
	public String home() {
        logger.debug("start: home()");
		return "Hello World!";
	}

	@RequestMapping("/send/")
	public String send() {
        logger.debug("start: send()");
		sendMessage("kmessage");
		return "send success.";
	}

	@RequestMapping("/messages/")
	public String messages() {
        logger.debug("start: messages()");
		String out = String.format("num messages: %d\n", messages.size());
		out += messages.stream().collect(Collectors.joining("; "));
		return out;
	}

	@KafkaListener(
		topics = "ktopic", // application.properties.spring.kafka.single-topic-name
		groupId = "11") // application.properties.spring.kafka.group-id
	public void listenGroupFoo(String message) {
		String logMsg = "Received Message in group foo: " + message;
		System.out.println(logMsg);
		logger.info(logMsg);
		messages.add(message);
	}

	public static void main(String[] args) {
		SpringApplication.run(MyApplication.class, args);
	}
}
