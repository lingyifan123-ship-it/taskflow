package com.lingyifan.taskflow;

import com.lingyifan.taskflow.domain.User;
import com.lingyifan.taskflow.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@MapperScan("com.lingyifan.taskflow.mapper")
public class TaskFlowApplication {
    public static void main(String[] args) { SpringApplication.run(TaskFlowApplication.class, args); }

    @Bean
    CommandLineRunner seedAdmin(UserMapper users, PasswordEncoder encoder) {
        return args -> {
            if (users.selectByUsername("admin") == null) {
                users.insert(new User(null, "admin", encoder.encode("Admin123!"), "ADMIN"));
            }
        };
    }
}
