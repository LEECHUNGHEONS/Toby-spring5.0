package main.vol1_chlee.ch5.lch.service;

import main.vol1_chlee.ch5.lch.dao.UserDao;
import main.vol1_chlee.ch5.lch.domain.Level;
import main.vol1_chlee.ch5.lch.domain.User;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

public class UserService {

    protected UserLevelUpgradePolicy userLevelUpgrade;

    protected UserDao userDao;

    private PlatformTransactionManager transactionManager;


    //  UserLevelUpgradeImpl로 di (더미테스트도 중간에 껴져있음)
    public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy userLevelUpgrade) {
        this.userLevelUpgrade = userLevelUpgrade;
    }

    //UserDao di 주입
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    //PlatformTransactionManger의 인터페이스 구현체 DataSourceTransactionManager도 di 받음
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void upgradeLevels() throws Exception {
        // 트랜잭션 서비스 추상화인 PlatformTransactionManager 를 이용해 트랜잭션 경계설정을 한다 (여기에 동기화도 같이 내장)
        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            List<User> users = userDao.getAll();
            for(User user : users) {
                if (canUpgradeLevel(user)) {
                    upgradeLevel(user);
                }
            }
            transactionManager.commit(status); //-> 정상적으로 작업을 마칠 경우 커밋

        } catch (Exception e) {

            transactionManager.rollback(status); //-> 정상적으로 작업을 마치지 않을 경우 롤백
            throw e;
        }
    }

    // 업글이 실제로 반영되는 메소드
    protected void upgradeLevel(User user) {
        userLevelUpgrade.upgradeLevel(user, userDao);
    }

    // 업글이 가능한지 여부 확인 메소드
    private boolean canUpgradeLevel(User user) {
        return userLevelUpgrade.canUpgradeLevel(user);
    }


    // 처음 사용자에게 베이직 레벨 부여
    public void add (User user){
        if(user.getLevel()== null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }
    
    

}