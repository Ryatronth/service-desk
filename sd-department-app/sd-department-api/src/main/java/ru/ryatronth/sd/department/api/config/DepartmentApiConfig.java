package ru.ryatronth.sd.department.api.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import ru.ryatronth.sd.property.reader.EnableConfigFiles;

@Configuration
@EnableFeignClients
@EnableConfigFiles("department-client.yml")
public class DepartmentApiConfig {}
