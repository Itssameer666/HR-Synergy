package com.example.hrms.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.hrms.model.AdminInfo;
import com.example.hrms.repo.AdminInfoRepo;

@Configuration
public class DatabaseInitializer {

    @Bean
    public CommandLineRunner initDatabase(AdminInfoRepo airepo) {
        return args -> {
            try {
                if (airepo.count() == 0) {
                    AdminInfo defaultAdmin = new AdminInfo();
                    defaultAdmin.setAdminid("admin");
                    defaultAdmin.setPassword("admin123");
                    airepo.save(defaultAdmin);
                    System.out.println("=================================================");
                    System.out.println(">>> SEEDED DEFAULT ADMIN ACCOUNT: admin / admin123");
                    System.out.println("=================================================");
                }
            } catch (Exception e) {
                System.out.println(">>> DatabaseInitializer note: " + e.getMessage());
            }
        };
    }
}
