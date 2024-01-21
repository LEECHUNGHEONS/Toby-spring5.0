package main.vol1_chlee.ch5.lch.service;

import main.vol1_chlee.ch5.lch.dao.UserDao;
import main.vol1_chlee.ch5.lch.domain.Level;
import main.vol1_chlee.ch5.lch.domain.User;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class UserLevelUpgradeImpl implements UserLevelUpgradePolicy {

    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;

    private MailSender mailSender;

    public void setMailSender(MailSender dummyMailSender) {
        this.mailSender = dummyMailSender;
    }

    @Override
    public boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();

        switch(currentLevel) {
            case BASIC : return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
            case SILVER : return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
            case GOLD : return false;
            default : throw new IllegalArgumentException("Unknown Level : " + currentLevel);
        }
    }

    @Override
    public void upgradeLevel(User user, UserDao userDao) {
        user.upgradeLevel();
        userDao.update(user);
        sendUpgradeEMail(user);
    }

    private void sendUpgradeEMail(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("yy8775799@gmail.com");
        mailMessage.setSubject("Upgrade 안내");
        mailMessage.setText("사용자님의 등급이 "+ user.getLevel().name() + "로 업그레이드 되셨습니다 축하드립니다.");

        this.mailSender.send(mailMessage);

    }



}
