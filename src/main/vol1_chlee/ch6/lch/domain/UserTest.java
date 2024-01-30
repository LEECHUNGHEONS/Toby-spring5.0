package main.vol1_chlee.ch6.lch.domain;

import main.vol1_chlee.ch6.lch.domain.Level;
import main.vol1_chlee.ch6.lch.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {
    User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    public void upgradeLevel() {
        Level[] levels = Level.values();
        for (Level level : levels) {
            if (level.nextLevel() == null) continue;

            user.setLevel(level);
            user.upgradeLevel();
            assertEquals(user.getLevel(), level.nextLevel());
        }
    }

    @Test
    public void cannotUpgradeLevel(){
        Level[] levels = Level.values();
        for(Level level : levels){
            if (level.nextLevel() != null)continue;

            user.setLevel(level);
            //해당 예외가 발생 시 테스트 성공!
            assertThrows(IllegalStateException.class, user::upgradeLevel);
        }
    }
}
