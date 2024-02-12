package main.vol1_chlee.ch6.lch.service;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionAdvice implements MethodInterceptor {
    //부가 기능을 부여할 때 필요한 정보, 객체
    PlatformTransactionManager transactionManager;

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    //순수한 부가 기능
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        TransactionStatus status =
                this.transactionManager
                        .getTransaction(new DefaultTransactionDefinition());

        try {
            // Proxy로 받은 콜백 객체를 통해서 타겟의 메소드를 실행한다
            Object ret = invocation.proceed();
            this.transactionManager.commit(status);
            return ret;

        } catch (RuntimeException e) {
            this.transactionManager.rollback(status);
            throw e;
        }
    }
}
