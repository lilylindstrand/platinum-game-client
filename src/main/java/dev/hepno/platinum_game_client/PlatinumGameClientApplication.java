package dev.hepno.platinum_game_client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlatinumGameClientApplication implements CommandLineRunner {

	private UdpClient client;

	public static void main(String[] args) {
		SpringApplication.run(PlatinumGameClientApplication.class, args);
	}

	@Override
	public void run(String[] args) throws Exception {
		client = new UdpClient();
		client.run();
	}

}
