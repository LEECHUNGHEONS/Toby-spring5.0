package main.vol1_chlee.ch7.lch.service;


import main.vol1_chlee.ch7.lch.domain.User;

import java.util.List;

public class TestUserServiceImpl extends UserServiceImpl {
	
	private String id = "user4";
	
	//예외를 내부 클래스로 선언하여 사용한다
	static class TestUserServiceException extends RuntimeException{};

	@Override
	protected void upgradeLevel(User user) {
		//테스트에서 생성자로 주입한 ID와 User 객체의 아이디가 같으면 오류를 발생시킨다.
		if (user.getId().equals(this.id)) throw new TestUserServiceException();
		super.upgradeLevel(user);
	}
	
	@Override
	public List<User> getAll() {
		for(User user : super.getAll()) {
			super.update(user);
		}
		return null;
	}
}
