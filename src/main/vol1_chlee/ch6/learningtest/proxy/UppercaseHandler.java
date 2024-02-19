package main.vol1_chlee.ch6.learningtest.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler {

	Object target;
	
	public UppercaseHandler(Hello target) {
		this.target = target;
	}
	
	@Override  
	//Object proxy : proxy 객체이다
	//Method method : proxy와 target 구현한 인터페이스에 정의되어있는 Method에 대한 메타데이터 -> 어떤 프록시의 메소드와 타겟의 메소드를 연결
	//Object [] args : 프록시 메소드와 타겟 메소드의 인자로 들어갈 정보, 객체
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//타겟으로 위임, 인터페이스의 모든 메소드 호출에 적용
		Object ret = (String)method.invoke(target, args);
		
		// 리턴 타입 확인 후 부가기능 제공
		if(ret instanceof String && method.getName().startsWith("say")) {
			return ((String)ret).toUpperCase();
		
		} else {
			return ret;
		}
	}
}
