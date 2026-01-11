package ru.ryatronth.sd.ticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.ryatronth")
public class TicketServiceApplication {

  static void main(String[] args) {
    SpringApplication.run(TicketServiceApplication.class, args);
  }

}
