package main.vol1_chlee.ch6.learningtest.proxy;

public interface Hello {
	
	//타겟 인터페이스 : 프록시와 타겟이 구현해야할 인터페이스
	String sayHello(String name);
	String sayHi(String name);
	String sayThankYou(String name);
}
