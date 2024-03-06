package main.vol1_chlee.ch7.lch.sqlservice;

import main.vol1_chlee.ch7.lch.sqlservice.exception.SqlRetrievalFailureException;
import main.vol1_chlee.ch7.lch.sqlservice.jaxb.SqlType;
import main.vol1_chlee.ch7.lch.sqlservice.jaxb.Sqlmap;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;

import javax.annotation.PostConstruct;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;

public class OxmSqlService implements SqlService {
	// SqlService 로직을 위임할 객체
	private final BaseSqlService baseSqlService = new BaseSqlService();

	// final로 변경 불가능하며, 두 개의 클래스는 강하게 결합되어 있다
	private final OxmSqlReader oxmSqlReader = new OxmSqlReader();

	// 디폴트 오브젝트로 만들어진 프로퍼티, 필요에 따라 setter으로 변경한다
	private SqlRegistry sqlRegistry = new HashMapSqlRegistry();

	public void setSqlRegistry(SqlRegistry sqlRegistry) {
		this.sqlRegistry = sqlRegistry;
	}

	// OxmSqlService를 통해서 간접적으로 OxmSqlReader에게 DI
	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.oxmSqlReader.setUnmarshaller(unmarshaller);
	}

	public void setSqlmap(Resource sqlmap) {
		this.oxmSqlReader.setSqlmap(sqlmap);
	}

	//SqlService의 구현 코
	@PostConstruct
	public void loadSql() {
		// 실제 작업을 위임할 대상에게 주입
		this.baseSqlService.setSqlReader(this.oxmSqlReader);
		this.baseSqlService.setSqlRegistry(this.sqlRegistry);

		// 초기화 작업 위임
		this.baseSqlService.loadSql();
	}

	@Override
	public String getSql(String key) throws SqlRetrievalFailureException {
		return this.baseSqlService.getSql(key);
	}

	//내부 클래스
	private class OxmSqlReader implements SqlReader{
		private Unmarshaller unmarshaller;
		private Resource sqlmap = new ClassPathResource("/Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'userDao' defined in class path resource [main/vol1_chlee/ch7/lch/service/TestServiceFactory.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [main.vol1_chlee.ch7.lch.dao.UserDao]: Factory method 'userDao' threw exception; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'sqlService': Invocation of init method failed; nested exception is java.lang.IllegalArgumentException: sqlmap.xml을 가져올 수 없습니다.\n" +
				"\tat org.springframework.beans.factory.support.ConstructorResolver.instantiate(ConstructorResolver.java:658)\n" +
				"\tat org.springframework.beans.factory.support.ConstructorResolver.instantiateUsingFactoryMethod(ConstructorResolver.java:486)\n" +
				"\tat org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.instantiateUsingFactoryMethod(AbstractAutowireCapableBeanFactory.java:1352)\n" +
				"\tat org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1195)\n" +
				"\tat org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)\n" +
				"\tat org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)\n" +
				"\tat org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)\n" +
				"\tat org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)\n" +
				"\tat org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)\n" +
				"\tat org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)\n" +
				"\tat org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:953)\n" +
				"\tat org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918)\n" +
				"\tat org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)\n" +
				"\tat org.springframework.test.context.support.AbstractGenericContextLoader.loadContext(AbstractGenericContextLoader.java:127)\n" +
				"\tat org.springframework.test.context.support.AbstractGenericContextLoader.loadContext(AbstractGenericContextLoader.java:60)\n" +
				"\tat org.springframework.test.context.support.AbstractDelegatingSmartContextLoader.delegateLoading(AbstractDelegatingSmartContextLoader.java:275)\n" +
				"\tat org.springframework.test.context.support.AbstractDelegatingSmartContextLoader.loadContext(AbstractDelegatingSmartContextLoader.java:243)\n" +
				"\tat org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)\n" +
				"\tat org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)\n" +
				"\t... 72 more\n" +
				"Caused by: org.springframework.beans.BeanInstantiationException: Failed to instantiate [main.vol1_chlee.ch7.lch.dao.UserDao]: Factory method 'userDao' threw exception; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'sqlService': Invocation of init method failed; nested exception is java.lang.IllegalArgumentException: sqlmap.xml을 가져올 수 없습니다.\n" +
				"\tat org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:185)\n" +
				"\tat org.springframework.beans.factory.support.ConstructorResolver.instantiate(ConstructorResolver.java:653)\n" +
				"\t... 90 more\n" +
				"Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'sqlService': Invocation of init method failed; nested exception is java.lang.IllegalArgumentException: sqlmap.xml을 가져올 수 없습니다.\n" +
				"\tat org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor.postProcessBeforeInitialization(InitDestroyAnnotationBeanPostProcessor.java:160)\n" +
				"\tat org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsBeforeInitialization(AbstractAutowireCapableBeanFactory.java:440)\n" +
				"\tat org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1796)\n" +
				"\tat org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)\n" +
				"\tat org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)\n" +
				"\tat org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)\n" +
				"\tat org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)\n" +
				"\tat org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)\n" +
				"\tat org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)\n" +
				"\tat org.springframework.context.annotation.ConfigurationClassEnhancer$BeanMethodInterceptor.resolveBeanReference(ConfigurationClassEnhancer.java:362)\n" +
				"\tat org.springframework.context.annotation.ConfigurationClassEnhancer$BeanMethodInterceptor.intercept(ConfigurationClassEnhancer.java:334)\n" +
				"\tat main.vol1_chlee.ch7.lch.service.TestServiceFactory$$EnhancerBySpringCGLIB$$8a07838c.sqlService(<generated>)\n" +
				"\tat main.vol1_chlee.ch7.lch.service.TestServiceFactory.userDao(TestServiceFactory.java:92)\n" +
				"\tat main.vol1_chlee.ch7.lch.service.TestServiceFactory$$EnhancerBySpringCGLIB$$8a07838c.CGLIB$userDao$2(<generated>)\n" +
				"\tat main.vol1_chlee.ch7.lch.service.TestServiceFactory$$EnhancerBySpringCGLIB$$8a07838c$$FastClassBySpringCGLIB$$1d473163.invoke(<generated>)\n" +
				"\tat org.springframework.cglib.proxy.MethodProxy.invokeSuper(MethodProxy.java:244)\n" +
				"\tat org.springframework.context.annotation.ConfigurationClassEnhancer$BeanMethodInterceptor.intercept(ConfigurationClassEnhancer.java:331)\n" +
				"\tat main.vol1_chlee.ch7.lch.service.TestServiceFactory$$EnhancerBySpringCGLIB$$8a07838c.userDao(<generated>)\n" +
				"\tat java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n" +
				"\tat java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n" +
				"\tat java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n" +
				"\tat java.base/java.lang.reflect.Method.invoke(Method.java:566)\n" +
				"\tat org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:154)\n" +
				"\t... 91 more\n" +
				"Caused by: java.lang.IllegalArgumentException: sqlmap.xml을 가져올 수 없습니다.\n" +
				"\tat main.vol1_chlee.ch7.lch.sqlservice.OxmSqlService$OxmSqlReader.read(OxmSqlService.java:78)\n" +
				"\tat main.vol1_chlee.ch7.lch.sqlservice.BaseSqlService.loadSql(BaseSqlService.java:24)\n" +
				"\tat main.vol1_chlee.ch7.lch.sqlservice.OxmSqlService.loadSql(OxmSqlService.java:47)\n" +
				"\tat java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n" +
				"\tat java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n" +
				"\tat java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n" +
				"\tat java.base/java.lang.reflect.Method.invoke(Method.java:566)\n" +
				"\tat org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor$LifecycleElement.invoke(InitDestroyAnnotationBeanPostProcessor.java:389)\n" +
				"\tat org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor$LifecycleMetadata.invokeInitMethods(InitDestroyAnnotationBeanPostProcessor.java:333)\n" +
				"\tat org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor.postProcessBeforeInitialization(InitDestroyAnnotationBeanPostProcessor.java:157)\n" +
				"\t... 113 more\n" +
				"\nsqlmap.xml");

		public void setUnmarshaller(Unmarshaller unmarshaller) {
			this.unmarshaller = unmarshaller;
		}

		public void setSqlmap(Resource sqlmap) {
			this.sqlmap = sqlmap;
		}

		@Override
		public void read(SqlRegistry sqlRegistry) {
			try {
				Source source = new StreamSource(sqlmap.getInputStream());
				Sqlmap sqlmap = (Sqlmap) this.unmarshaller.unmarshal(source);

				for(SqlType sql : sqlmap.getSql()) {
					sqlRegistry.registerSql(sql.getKey(), sql.getValue());
				}
			} catch (IOException e) {
				throw new IllegalArgumentException(this.sqlmap.getFilename() + "을 가져올 수 없습니다.");
			}
		}

	}


}
