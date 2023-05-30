package uz.qbg.appHotelServise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import uz.qbg.appHotelServise.constants.Constants;

@EnableWebMvc
@SpringBootApplication(scanBasePackages = Constants.BASE_PACKAGE)
public class AppHotelServiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppHotelServiseApplication.class, args);
	}

}
