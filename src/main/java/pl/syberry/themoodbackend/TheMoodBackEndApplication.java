package pl.syberry.themoodbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import pl.syberry.themoodbackend.model.EmailSender;

@SpringBootApplication
public class TheMoodBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheMoodBackEndApplication.class, args);
	}

}
