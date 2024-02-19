package main.vol1_chlee.ch6.learningtest.proxy;

//프록시 객체
public class HelloUppercase implements Hello {
	
	//타겟 객체를 필드로 가지고 있다 = HelloTarget
	Hello hello;
	
	public HelloUppercase(Hello hello) {
		this.hello = hello;
	}
	
	
	@Override
	public String sayHello(String name) {
		//기존의 기능에 새로운 기능을 추가해서 위임한다
		// toUppercase() 라는 부가기능을 추가해주고
		// 타겟의 메소드를 호출하는 역할
		return hello.sayHello(name).toUpperCase();
	}

	@Override
	public String sayHi(String name) {
		return hello.sayHi(name).toUpperCase();
	}

	@Override
	public String sayThankYou(String name) {
		return hello.sayThankYou(name).toUpperCase();
	}

}
