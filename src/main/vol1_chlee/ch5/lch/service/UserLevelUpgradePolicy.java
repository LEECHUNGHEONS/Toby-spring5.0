package main.vol1_chlee.ch5.lch.service;

import main.vol1_chlee.ch5.lch.dao.UserDao;
import main.vol1_chlee.ch5.lch.domain.User;

public interface UserLevelUpgradePolicy {

    boolean canUpgradeLevel(User user);
    void upgradeLevel(User user, UserDao userDao);
}
