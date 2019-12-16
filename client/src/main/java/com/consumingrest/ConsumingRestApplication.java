package com.consumingrest;

import com.consumingrest.domain.SimpleLotteryTicket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ConsumingRestApplication {

	private static final Logger log = LoggerFactory.getLogger(ConsumingRestApplication.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ConsumingRestApplication.class);
	//	app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			ResponseEntity<SimpleLotteryTicket[]> response = restTemplate.getForEntity(
					"http://localhost:8080/lottery/ticket", SimpleLotteryTicket[].class);
			SimpleLotteryTicket[] simpleLotteryTickets = response.getBody();
			log.info(simpleLotteryTickets.toString());

			int count = 0;
			for(SimpleLotteryTicket simpleLotteryTicket : simpleLotteryTickets) {
				log.info("status of ticket: " + simpleLotteryTicket.getStatusChecked());
				count++;
			}
			log.info("count of tickets: "+ count);
		};
	}
}
