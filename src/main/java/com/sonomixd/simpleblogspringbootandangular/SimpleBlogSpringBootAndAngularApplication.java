package com.sonomixd.simpleblogspringbootandangular;

 import com.sonomixd.simpleblogspringbootandangular.model.Post;
import com.sonomixd.simpleblogspringbootandangular.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SimpleBlogSpringBootAndAngularApplication {



	public static void main(String[] args) {
		SpringApplication.run(SimpleBlogSpringBootAndAngularApplication.class, args);


	}



}
