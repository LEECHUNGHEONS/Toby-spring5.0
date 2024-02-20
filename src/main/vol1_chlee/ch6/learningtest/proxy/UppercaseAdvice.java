package main.vol1_chlee.ch6.learningtest.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

//템플릿
public class UppercaseAdvice implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		//타겟 클래스의 메소드 호출
		//invocation은 ProxyFactoryBean에서 Proxy 객체가 생성되고
		//invoke() 메소드가 호출될 때 생성되어 콜백 오브젝트로 템플릿 역할을 하는 Advice에게 전달된다
		String ret = (String) invocation.proceed();
		
		//부여할 부가 기능
		return ret.toUpperCase();
	}

}
