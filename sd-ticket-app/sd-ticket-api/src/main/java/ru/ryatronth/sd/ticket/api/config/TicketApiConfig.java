package ru.ryatronth.sd.ticket.api.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import ru.ryatronth.sd.property.reader.EnableConfigFiles;

@Configuration
@EnableFeignClients(basePackages = "ru.ryatronth.sd.ticket.api")
@EnableConfigFiles("ticket-client.yml")
public class TicketApiConfig {
}
