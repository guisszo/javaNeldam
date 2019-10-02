package sn.neldamoney.neldam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class NeldamApplication extends SpringBootServletInitializer implements CommandLineRunner {


    public static void main(String[] args) {

        SpringApplication.run(NeldamApplication.class, args);
    }
    @Autowired
    PasswordEncoder passwordEncoder;
    public void run(String... args) throws Exception {
        System.out.println(passwordEncoder.encode("passer"));
    }

}
