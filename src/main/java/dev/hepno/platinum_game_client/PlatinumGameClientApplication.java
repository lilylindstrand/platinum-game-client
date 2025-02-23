package dev.hepno.platinum_game_client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Getter
public class PlatinumGameClientApplication implements CommandLineRunner {

	@Setter
	private Session session;

	private UdpClient client;
	private Authenticator authenticator;

	public static void main(String[] args) {
		SpringApplication.run(PlatinumGameClientApplication.class, args);
	}

	@Override
	public void run(String[] args) throws Exception {
		client = new UdpClient();
		client.run();
		authenticator = new Authenticator(this);
		authenticator.loginProcess();
	}

}
