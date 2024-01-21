package main.vol1_chlee.ch5.lch.service;

import main.vol1_chlee.ch5.lch.dao.UserDao;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

public class UserService {

    protected UserLevelUpgradePolicy userLevelUpgrade;

    protected UserDao userDao;

    private PlatformTransactionManager transactionManager;

    public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy userLevelUpgrade) {
        this.userLevelUpgrade = userLevelUpgrade;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

}