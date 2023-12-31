package main.vol1_chlee.ch2.learningtest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationContextTest.class)
public class JUnitTest {
    @Autowired
    ApplicationContext context;
    static ApplicationContext contextObject = null;

    static Set<JUnitTest> testObjects = new HashSet<JUnitTest>();
    static JUnitTest testObject;

    @Test
    public void test1(){
        //테스트 메소드가 매번 새로운 객체를 생성하는지 확인하고 기존 스태틱 변수에 가지고 있는 객채와 새로 생성된 객체와 비교
        //첫 번째와 세 번쨰의  객체를 비교할 수 없어 , set을 이용
        assertNotSame(this, testObject);
        testObject = this;

        assertFalse(testObjects.contains(this));
        testObjects.add(this);

        assertTrue(contextObject == null || contextObject == this.context);
        contextObject = this.context;
    }

    @Test
    public void test2(){
        assertNotSame(this, testObject);
        testObject = this;

        assertFalse(testObjects.contains(this));
        testObjects.add(this);

        assertTrue(contextObject == null || contextObject == this.context);
        contextObject = this.context;
    }

    @Test
    public void test3(){
        assertNotSame(this, testObject);
        testObject = this;

        assertFalse(testObjects.contains(this));
        testObjects.add(this);

        assertTrue(contextObject == null || contextObject == this.context);
        contextObject = this.context;
    }
}
