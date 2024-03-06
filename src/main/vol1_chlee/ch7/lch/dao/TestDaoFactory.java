package main.vol1_chlee.ch7.lch.dao;

import main.vol1_chlee.ch7.lch.sqlservice.OxmSqlService;
import main.vol1_chlee.ch7.lch.sqlservice.updatable.EmbeddedDbSqlRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.sql.DataSource;

@Configuration
public class TestDaoFactory {
	
	@Bean
	public DataSource dataSource() {
		
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		
		dataSource.setDriverClass(org.mariadb.jdbc.Driver.class);
		dataSource.setUrl("jdbc:mariadb://localhost:3307/toby_study?characterEncoding=UTF-8");
		dataSource.setUsername("root");
		dataSource.setPassword("1234");

		return dataSource;
	}

	@Bean
	public UserDaoJdbc userDao() {
		UserDaoJdbc userDaoJdbc = new UserDaoJdbc();
		userDaoJdbc.setDataSource(dataSource());
		userDaoJdbc.setSqlService(sqlService());
		return userDaoJdbc;
	}

	@Bean
	public OxmSqlService sqlService() {
		OxmSqlService oxmSqlService = new OxmSqlService();
		oxmSqlService.setSqlmap(new ClassPathResource
				("/main/vol1_chlee/ch7/lch/dao/sqlmap.xml", UserDao.class));
		oxmSqlService.setUnmarshaller(unmarshaller());
		oxmSqlService.setSqlRegistry(sqlRegistry());
		return oxmSqlService;
	}

	@Bean
	public Jaxb2Marshaller unmarshaller() {
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setContextPath("main.vol1_chlee.ch7.lch.sqlservice.jaxb");
		return jaxb2Marshaller;
	}

	@Bean
	public EmbeddedDbSqlRegistry sqlRegistry() {
		EmbeddedDbSqlRegistry embeddedDbSqlRegistry = new EmbeddedDbSqlRegistry();
		embeddedDbSqlRegistry.setDataSource(embeddedDatabase());
		return embeddedDbSqlRegistry;
	}

	@Bean
	public DataSource embeddedDatabase() {
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		databasePopulator.addScript(new ClassPathResource("/main/vol1_lch/ch7/lch/sqlservice/updatable/sqlRegistrySchema.sql"));

		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.setName("embeddedDatabase")
				.setScriptEncoding("UTF-8")
				.addScript("classpath:/main/vol1_chlee/ch7/lch/sqlservice/updatable/sqlRegistrySchema.sql")
				.build();
	}

}


