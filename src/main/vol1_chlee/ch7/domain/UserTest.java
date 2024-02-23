package main.vol1_chlee.ch7.domain;

import main.vol1_chlee.ch7.dao.DaoFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DaoFactory.class})
public class UserTest {
	
	User user;
	
	@BeforeEach
	public void setUp() {
		user = new User();
	}
	
	@Test
	public void upgradeLevel() {
		Level[] levels = Level.values();
		for(Level level : levels) {
			if(level.nextLevel() == null) continue;
			
			user.setLevel(level);
			user.upgradeLevel();
			assertEquals(user.getLevel(), level.nextLevel());
		}
	}
	
	@Test
	public void cannotUpgradeLevel() {
		Level[] levels = Level.values();
		for (Level level : levels) {
			if (level.nextLevel() != null) continue;
			
			user.setLevel(level);
			
			//해당 예외가 발생하면 테스트 성공
			assertThrows(IllegalStateException.class, user::upgradeLevel);
		}
	}
}
