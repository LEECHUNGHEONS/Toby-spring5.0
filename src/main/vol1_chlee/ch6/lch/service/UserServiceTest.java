package main.vol1_chlee.ch6.lch.service;

import main.vol1_chlee.ch6.lch.dao.UserDao;
import main.vol1_chlee.ch6.lch.domain.Level;
import main.vol1_chlee.ch6.lch.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.*;

import static main.vol1_chlee.ch6.lch.service.UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER;
import static main.vol1_chlee.ch6.lch.service.UserServiceImpl.MIN_RECOMMEND_FOR_GOLD;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestServiceFactory.class})
public class UserServiceTest {

    @Autowired
    private UserDao userDao;
    @Autowired private UserService userService;
    @Autowired @Qualifier("testUserService")
    private UserService testUserService;

    @Autowired private PlatformTransactionManager transactionManager;
    @Autowired private MailSender mailSender;

    List<User> users;

    @BeforeEach
    public void setUp() {

        users = Arrays.asList(
                new User("user1", "user1", "p1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER -1, 0, "user1@go.kr"),
                new User("user2", "user2", "p2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0, "yy8775799@gmail.com"),
                new User("user3", "user3", "p3", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD -1, "user3@go.kr"),
                new User("user4", "user4", "p4", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD, "y8775799@gmail.com"),
                new User("user5", "user5", "p5", Level.GOLD, 100, 100, "user5@go.kr")
        );

    }

    //User의 Level과 인수로 받은 Level의 값을 비교하는 메소드
    //어떤 레벨로 바뀌는지가 아니라, 다음 레벨로 바뀔 것인지를 확인한다.
    private void checkLevel(User user, boolean upgraded) {
        Optional<User> optionalUser = userDao.get(user.getId());

        if(optionalUser != null) {
            User userUpdate = optionalUser.get();

            if(upgraded) {
                System.out.println(user.getId() + " : Level 업그레이드 되었음");
                assertEquals(userUpdate.getLevel(), user.getLevel().nextLevel());

            } else {
                System.out.println(user.getId() + " : Level 업그레이드 되지 않음");
                assertEquals(userUpdate.getLevel(), user.getLevel());
            }

        }
    }

    //Mockito를 사용한 테스트 코드
    @Test
    @DirtiesContext
    public void mockUpgradeLevels() throws Exception {
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        //mockito를 사용한 Mock 객체 생성 및 주입
        UserDao mockUserDao = mock(UserDao.class);
        when(mockUserDao.getAll()).thenReturn(this.users);
        userServiceImpl.setUserDao(mockUserDao);

        MailSender mockMailSender = mock(MailSender.class);
        userServiceImpl.setMailSender(mockMailSender);

        userServiceImpl.upgradeLevels();

        //mockUserDao의 update 메소드가 2번 실행되는지 검증, 인수로 User타입의 객체를 임의로(any)로 전달함
        verify(mockUserDao, times(2)).update(any(User.class));

        //mockUserDao가 update 메소드를 실행할 때 인자로 users.get(1)를 인스턴스로 전달하는지 확인함
        verify(mockUserDao).update(users.get(1));
        assertEquals(users.get(1).getLevel(), Level.SILVER);

        //mockUserDao가 update 메소드를 실행할 때 인자로 users.get(3)를 인스턴스로 전달하는지 확인함
        verify(mockUserDao).update(users.get(3));
        assertEquals(users.get(3).getLevel(), Level.GOLD);

        //ArgumentCaptor 객체가 SimpleMailMessage 인스턴스를 저장할 수 있도록 설정함
        ArgumentCaptor<SimpleMailMessage> mailMessageArg =
                ArgumentCaptor.forClass(SimpleMailMessage.class);

        //mockMailSender의 send 메소드가 2번 실행되었는지,
        //send() 메소드가 실행될 때 SimpleMailMessage 객체가 전달되었는지 확인
        verify(mockMailSender, times(2)).send(mailMessageArg.capture());
        List<SimpleMailMessage> mailMessages = mailMessageArg.getAllValues();

        assertEquals(mailMessages.get(0).getTo()[0], users.get(1).getEmail());
        assertEquals(mailMessages.get(1).getTo()[0], users.get(3).getEmail());

    }

    // upgradeLevels() 테스트에 사용될 MailSender Mock 객체
    static class MockMailSender implements MailSender {
        private List<String> requests = new ArrayList<String>();

        public List<String> getRequests() {
            return requests;
        }

        public void send(SimpleMailMessage mailMessage) throws MailException {
            requests.add(mailMessage.getTo()[0]);
        }

        public void send(SimpleMailMessage[] mailMessage) throws MailException {
        }
    }

    //upgradeLevels() 테스트에 사용되어지는 UserDao Mock 객체
    static class MockUserDao implements UserDao {
        //업그레이드 후보와 업그레이드 된 결과를 저장할 변수
        private List<User> users;
        private List<User> updated = new ArrayList();

        //생성자
        private MockUserDao(List<User> users) {
            this.users = users;
        }

        private List<User> getUpdated(){
            return this.updated;
        }

        public List<User> getAll(){
            return this.users;
        }

        public void update(User user) {
            updated.add(user);
        }

        //사용되지 않는 기능, UnsupportedOperationException을 발생시키는 것이 좋다
        public void add(User user) { throw new UnsupportedOperationException(); }
        public void deleteAll() { throw new UnsupportedOperationException(); }
        public Optional<User> get(String id) { throw new UnsupportedOperationException(); }
        public int getCount() { throw new UnsupportedOperationException(); }
    }

    @Test
    public void upgradeLevels() throws Exception {
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        MockUserDao mockUserDao = new MockUserDao(this.users);
        userServiceImpl.setUserDao(mockUserDao);

        MockMailSender mockMailSender = new MockMailSender();
        userServiceImpl.setMailSender(mockMailSender);

        userServiceImpl.upgradeLevels();

        List<User> updated = mockUserDao.getUpdated();
        assertEquals(updated.size(), 2);
        checkUserAndLevel(updated.get(0), "user2", Level.SILVER);
        checkUserAndLevel(updated.get(1), "user4", Level.GOLD);

        List<String> request = mockMailSender.getRequests();
        assertEquals(request.size(), 2);
        assertEquals(request.get(0), users.get(1).getEmail());
        assertEquals(request.get(1), users.get(3).getEmail());
    }

    //== id와 level을 확인하는 헬퍼 메소드 ==//
    private void checkUserAndLevel(User updated, String expectedId, Level expectedLevel) {
        assertEquals(updated.getId(), expectedId);
        assertEquals(updated.getLevel(), expectedLevel);
    }


    /*  User 클래스에서 level 필드를 BASIC으로 초기화 하는 방법도 있지만 처음 가입할 때 무의미한 필드기에 코드낭비..
       그래서 UserSevice에도 add() 메소드를 만들어두고 사용자가 등록될 때 레벨이 정해진 경우와
       레벨이 없는 경우에 대비해 add()를 만들어 호출하게끔 하고
       레벨이 null 값이면 기본 레벨인 BASIC으로 세팅해서 가입되게끔 하는 것
       이 로직을 테스트 하는 것이다.
    */
    @Test
    public void add() {
        userDao.deleteAll();

        User userWithLevel = users.get(4); //GOLD
        User userWithOutLevel = users.get(0); //BASIC
        userWithOutLevel.setLevel(null); //BASIC -> NULL, 비어있으면 다시 BASIC으로 설정되어야함.

        //GOLD -> GOLD 그대로 유지
        userService.add(userWithLevel);

        //Null -> BASIC 처음 가입 유저는 BASIC으로 설정
        userService.add(userWithOutLevel);

        //DB에 저장된 것을 불러와서 저장한 값이랑 비교함.
        Optional<User> optionalLevelUser = userDao.get(userWithLevel.getId());
        if(optionalLevelUser != null) {
            User userWithLevelRead = optionalLevelUser.get();
            assertEquals(userWithLevelRead.getLevel(), userWithLevel.getLevel());
        }

        Optional<User> optionalOutLevelUser = userDao.get(userWithOutLevel.getId());
        if(optionalOutLevelUser != null) {
            User userWithOutLevelRead = optionalOutLevelUser.get();
            assertEquals(userWithOutLevelRead.getLevel(), userWithOutLevel.getLevel());
        }
    }

    //예외 발생 시 작업 취소 여부 테스트
    @Test @DirtiesContext
    public void upgradeAllorNothing() throws Exception {
        userDao.deleteAll();

        for (User user : users) {
            this.testUserService.add(user);
        }

        try {
            this.testUserService.upgradeLevels();
            //테스트가 제대로 동작하게 하기 위한 안전장치, 로직을 잘못짜서 upgradeLevels() 메소드가 통과되도 무조건 실패함.
            //fail("TestUserServiceException expected");
        } catch (TestUserServiceImpl.TestUserServiceException e) {
            System.out.println("TestUserServiceException 예외 발생함");
        } finally {
            checkLevel(users.get(1), false);
        }
    }

    @Test
    public void advisorAutoProxyCreator() {
        //주입받는 객체가 proxy 객체인지 검증
        assertTrue(userService instanceof java.lang.reflect.Proxy);
        assertTrue(testUserService instanceof java.lang.reflect.Proxy);
    }

    //@Test
    @Test
    public void sendEmailToGmail() throws UnsupportedEncodingException {
        String host = "smtp.gmail.com";
        int port = 587;
        String username = "yy8775799@gmail.com";
        String password = "fryo rhef XXXX XXXX"; // 본인만의 구글계정 보안으로 변경-> 2단계 인증에서 앱 비밀번호

        // 수진자 이메일 주소
        String toAddress = "yy8775799@gmail.com";

        // 메일 속성 설정
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.prot", port);

        // 인증 객체 생성
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };

        // 세션 생성
        Session session = Session.getInstance(props, authenticator);
        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(username));
            message.setSubject(MimeUtility.encodeText("업그레이드 안내", "UTF-8", "B"));
            message.setText("사용자의 등급 안내 테스트", "UTF-8");

            // 메일 전송
            Transport.send(message);

            System.out.println("이메일 전송이 성공입니다!");
        } catch (Exception e) {
            System.out.println("이메일 전송 실패. 실패 원인 !! : " + e.getMessage());
//			fail("This sendEmailToFmail test is failed!!");
        }
    }



}
