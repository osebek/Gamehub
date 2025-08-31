package cz.ondra.gamehub;

import org.springframework.boot.SpringApplication;

public class TestGamehubApplication {

	public static void main(String[] args) {
		SpringApplication.from(GamehubApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
