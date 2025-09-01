package cz.ondra.gamehub;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import cz.ondra.gamehub.db.repository.GameInfoRepository;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class GamehubApplicationTests {

	@Autowired
	private GameInfoRepository repo;

	@Test
	void contextLoads() {

		var infos = repo.findAll();
		System.out.println("cau");
	}

}
