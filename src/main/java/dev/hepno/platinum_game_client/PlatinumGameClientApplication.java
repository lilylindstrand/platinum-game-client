package dev.hepno.platinum_game_client;

import lombok.Getter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Getter
public class PlatinumGameClientApplication implements CommandLineRunner {

	private UdpClient client;
	private Authenticator authenticator;

	public static void main(String[] args) {
		SpringApplication.run(PlatinumGameClientApplication.class, args);
	}

	@Override
	public void run(String[] args) throws Exception {
		client = new UdpClient();
		client.run();
		authenticator = new Authenticator();
		authenticator.loginProcess();
	}

}
