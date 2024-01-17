package main.vol1_chlee.ch5.lch.service;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestServiceFactory.class})
public class UserServiceTest {

    @Autowired
    private UserService userService;

}
