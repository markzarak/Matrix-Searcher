/*
 * A Spring Boot and Thymeleaf enabled application that takes a user's input to
 * create and display a searchable 2D grid filled with random characters.
 * 
 * @author: Mark Zarak, Nov 2020
 */

package ca.sheridancollege;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MatrixSearcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatrixSearcherApplication.class, args);
	}

}
