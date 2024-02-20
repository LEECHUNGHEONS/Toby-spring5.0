package main.vol1_chlee.ch6.learningtest.proxy;

import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DynamicProxyTest {
	
	@Test
	public void simpleProxy() {
		Hello hello = new HelloTarget(); // 타겟은 인터페이스를 통해서 접근한다
		
		assertEquals(hello.sayHello("Toby"), "Hello Toby");
		assertEquals(hello.sayHi("Toby"), "Hi Toby");
		assertEquals(hello.sayThankYou("Toby"), "Thank You Toby");
		
		
		Hello proxiedHello = new HelloUppercase(new HelloTarget());
		
		assertEquals(proxiedHello.sayHello("Toby"), "HELLO TOBY");
		assertEquals(proxiedHello.sayHi("Toby"), "HI TOBY");
		assertEquals(proxiedHello.sayThankYou("Toby"), "THANK YOU TOBY");
		
	}
	
	@Test
	public void dynamicProxy() {
		Hello dynamicProxy = (Hello)Proxy.newProxyInstance(getClass().getClassLoader(),
															new Class[] { Hello.class },
															new UppercaseHandler(new HelloTarget()));
		
		assertEquals(dynamicProxy.sayHello("Toby"), "HELLO TOBY");
		assertEquals(dynamicProxy.sayHi("Toby"), "HI TOBY");
		assertEquals(dynamicProxy.sayThankYou("Toby"), "THANK YOU TOBY");
	}
	
	@Test
	public void proxyFactoryBean() {
		ProxyFactoryBean pfBean = new ProxyFactoryBean();
		
		// 타겟 오브젝트 지정
		pfBean.setTarget(new HelloTarget());
	
		//advice 지정, 여러개 동시 적용 가능
		pfBean.addAdvice(new UppercaseAdvice());
		
		Hello proxiedHello = (Hello) pfBean.getObject();
		
		assertEquals(proxiedHello.sayHello("Toby"), "HELLO TOBY");
		assertEquals(proxiedHello.sayHi("Toby"), "HI TOBY");
		assertEquals(proxiedHello.sayThankYou("Toby"), "THANK YOU TOBY");
	}
	
	@Test
	public void pointcutAdvisorToProxyFactoryBean() {
		ProxyFactoryBean pfBean = new ProxyFactoryBean();
		
		// 타겟 오브젝트 지정
		pfBean.setTarget(new HelloTarget());
		
		//advice 생성
		UppercaseAdvice advice = new UppercaseAdvice();
		
		//pointcut 생성
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.setMappedName("sayH*"); // sayH 로 시작하는 타겟의 메소드가 호출될 때만 Advisor을 적용함

		// Advisor = Advice + Pointcut
		DefaultPointcutAdvisor advisor =
				new DefaultPointcutAdvisor(pointcut, advice);
	
		// ProxyFactoryBean에 Advisor 주입
		pfBean.addAdvisor(advisor);
		
		// Proxy 생성 -> 타겟 객체가 구현한 인터페이스 타입으로 받음
		Hello proxiedHello = (Hello) pfBean.getObject();
		
		assertEquals(proxiedHello.sayHello("Toby"), "HELLO TOBY");
		assertEquals(proxiedHello.sayHi("Toby"), "HI TOBY");
		
		//pointcut으로 sayH* 를 적용했기 때문에 sayThankYou 메소드에는 부가 기능이 적용되지 않음
		assertEquals(proxiedHello.sayThankYou("Toby"), "Thank You Toby"); 
		
	}
	
	@Test
	public void classNamePointcutAdvisor() {
		//클래스를 선정하는 알고리즘
		NameMatchMethodPointcut classMethodPointcut = new NameMatchMethodPointcut() {
			public ClassFilter getClassFilter() {
				return new ClassFilter() {
					public boolean matches(Class<?> clazz) {
						return clazz.getSimpleName().startsWith("HelloT");
					}
				};
			}
		};
		
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut() {
			public ClassFilter getClassFilter() {
				return ((clazz) -> clazz.getSimpleName().startsWith("HelloT"));
			}
		};
		
		//메소드를 선정하는 알고리즘
		classMethodPointcut.setMappedName("sayH*");
		
		//advisor 적용
		checkAdviced(new HelloTarget(), classMethodPointcut, true);
		
		//advisor 적용 x
		class HelloWorld extends HelloTarget {};
		checkAdviced(new HelloWorld(), classMethodPointcut, false);
		
		//advisor 적용
		class HelloToby extends HelloTarget {};
		checkAdviced(new HelloToby(), classMethodPointcut, true);
		
	
	}
	
	private void checkAdviced(Object target, Pointcut pointcut, boolean adviced) {
		ProxyFactoryBean pfBean = new ProxyFactoryBean();
		pfBean.setTarget(target);
		pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));
		
		//Proxy 객체 준비
		Hello proxiedHello = (Hello) pfBean.getObject();
	
		// advisor 적용 대상
		if (adviced) {
			assertEquals(proxiedHello.sayHello("Toby"), "HELLO TOBY");
			assertEquals(proxiedHello.sayHi("Toby"), "HI TOBY");
			assertEquals(proxiedHello.sayThankYou("Toby"), "Thank You Toby");
		
		} else {
			assertEquals(proxiedHello.sayHello("Toby"), "Hello Toby");
			assertEquals(proxiedHello.sayHi("Toby"), "Hi Toby");
			assertEquals(proxiedHello.sayThankYou("Toby"), "Thank You Toby");
		}
	}
}
