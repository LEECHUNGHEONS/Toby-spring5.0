package main.vol1_chlee.ch7.lch.service;

import main.vol1_chlee.ch7.lch.dao.UserDao;
import main.vol1_chlee.ch7.lch.domain.Level;
import main.vol1_chlee.ch7.lch.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
	public static final int MIN_RECCOMEND_FOR_GOLD = 30;
	
    @Autowired private UserDao userDao;
    @Autowired private MailSender mailSender;
	
	//UserDao 주입
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	// MailSender 주입
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	//사용자를 레벨을 업그레이드
	public void upgradeLevels() {
		List<User> users = userDao.getAll();
		for (User user : users) {
			if (canUpgradeLevel(user)) {
				upgradeLevel(user);
			}
		}
	}
	
	//업그레이드가 가능한지 확인
	private boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();
		switch(currentLevel) {                                   
		case BASIC: return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER); 
		case SILVER: return (user.getRecommend() >= MIN_RECCOMEND_FOR_GOLD);
		case GOLD: return false;
		default: throw new IllegalArgumentException("Unknown Level: " + currentLevel); 
		}
	}
	
	//업그레이드가 가능할 때 실제로 값을 변경
	protected void upgradeLevel(User user) {
		user.upgradeLevel();
		userDao.update(user);
		sendUpgradeEMail(user);
	}
	
	
	//처음 가입 하는 사용자에게 디폴트로 BASIC Level 부여
	public void add(User user) {
		if (user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}
	
	private void sendUpgradeEMail(User user) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		
		mailMessage.setTo(user.getEmail());
		mailMessage.setFrom("useradmin@ksug.org");
		mailMessage.setSubject("Upgrade 안내");
		mailMessage.setText("사용자님의 등급이 "+ user.getLevel().name());
			
		this.mailSender.send(mailMessage);
	}	
	
	public void deleteAll() { 	userDao.deleteAll(); }
	public Optional<User> get(String id) {
		return userDao.get(id); 
	}
	public List<User> getAll() { return userDao.getAll(); }
	public void update(User user) { userDao.update(user); }
}
