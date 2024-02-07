package main.vol1_chlee.ch6.lch.service;

import main.vol1_chlee.ch6.lch.dao.UserDao;
import main.vol1_chlee.ch6.lch.domain.Level;
import main.vol1_chlee.ch6.lch.domain.User;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.List;

public class UserServiceImpl implements UserService{

    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;

    private MailSender mailSender;

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


    public void setMailSender(MailSender dummyMailSender) {
        this.mailSender = dummyMailSender;
    }



    // 사용자를 db에 추가하고 디폴트로 basic 레벨 부여
    public void add(User user) {
        if (user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }

    // 사용자 레벨을 업그레이드 해주는 로직
    public void upgradeLevels() {
        List<User> users = userDao.getAll();
        for (User user : users) {
            if (canUpgradeLevel(user)) {
                upgradeLevel(user);
            }
        }
    }


    //업그레이드가 가능할 때 업그레이드를 가능하게 끔 해주는 로직
    protected void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
        sendUpgradeEMail(user);
    }

    // 업그레이드가 가능한지 확인하는 로직
    private boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        switch(currentLevel) {
            case BASIC: return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
            case SILVER: return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
            case GOLD: return false;
            default: throw new IllegalArgumentException("Unknown Level: " + currentLevel);
        }
    }


    private void sendUpgradeEMail(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("lchcompanylch.com");
        mailMessage.setSubject("Upgrade 안내");
        mailMessage.setText("사용자님의 등급이 "+ user.getLevel().name() +"로 업그레이드 되셨습니다. 축하드립니다.");

        this.mailSender.send(mailMessage);

    }
}
