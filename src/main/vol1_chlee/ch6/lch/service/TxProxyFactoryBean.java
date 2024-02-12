package main.vol1_chlee.ch6.lch.service;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Proxy;

public class TxProxyFactoryBean implements FactoryBean<Object> {
    //TransactionHandler를 생성할 때 필요

    Object target;
    PlatformTransactionManager transactionManager;
    String pattern;

    //프록시가 구현할 인터페이스의 Class
    Class<?> serviceInterface;

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setTransactionManager(
            PlatformTransactionManager transactionManger) {
        this.transactionManager = transactionManger;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setServiceInterface(Class<?> serviecInterface) {
        this.serviceInterface = serviecInterface;
    }

    //FactoryBean 인터페이스의 정의된 getObject() 메소드 구현
    @Override
    public Object getObject() throws Exception {
        TransactionHandler txHandler = new TransactionHandler();
        txHandler.setTarget(target);
        txHandler.setTransactionManager(transactionManager);
        txHandler.setPattern(pattern);
        return Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[] { serviceInterface },
                txHandler);
    }

    @Override
    public Class<?> getObjectType() {
        return serviceInterface;
    }

    @Override
    public boolean isSingleton() {
        // isSingleton()는 싱글톤 객체인지 여부를 결정하는 것 즉 컨테이너에서 한번만 생성되는지 여부를 결정
        // 그러나 밑에서 return false 라고 돼 있기에 싱글톤 객체로 생성을 하지 않음 , FactoryBean을 통해 생성된 객체는 새로운 인스턴스가 반환됨
        return false;
    }
}
