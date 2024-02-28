package main.vol1_chlee.ch7.lch.service;

import main.vol1_chlee.ch7.lch.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public interface UserService {
	void add(User user);
	void deleteAll();
	void update(User user);
	
	@Transactional(readOnly=true)
	Optional<User> get(String id);
	
	@Transactional(readOnly=true)
	List<User> getAll();
	
	void upgradeLevels();
}
