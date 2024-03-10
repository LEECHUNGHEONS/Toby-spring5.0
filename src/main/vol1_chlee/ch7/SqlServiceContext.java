package main.vol1_chlee.ch7;

import main.vol1_chlee.ch7.lch.sqlservice.OxmSqlService;
import main.vol1_chlee.ch7.lch.sqlservice.SqlRegistry;
import main.vol1_chlee.ch7.lch.sqlservice.SqlService;
import main.vol1_chlee.ch7.lch.sqlservice.updatable.EmbeddedDbSqlRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.sql.DataSource;

@Configuration
public class SqlServiceContext {
    @Autowired
    SqlMapConfig sqlMapConfig;

    @Bean
    public SqlService sqlService() {
        OxmSqlService sqlService = new OxmSqlService();
        sqlService.setUnmarshaller(unmarshaller());
        sqlService.setSqlRegistry(sqlRegistry());
        sqlService.setSqlmap(this.sqlMapConfig.getSqlMapResource());
        return sqlService;
    }

    @Bean
    public SqlRegistry sqlRegistry() {
        EmbeddedDbSqlRegistry sqlRegistry = new EmbeddedDbSqlRegistry();
        sqlRegistry.setDataSource(embeddedDatabase());
        return sqlRegistry;
    }

    @Bean
    public Unmarshaller unmarshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath("main.vol1_chlee.ch7.lch.sqlservice.jaxb");
        return jaxb2Marshaller;
    }

    @Bean
    public DataSource embeddedDatabase() {
        return new EmbeddedDatabaseBuilder()
                .setName("embeddedDatabase")
                .setType(EmbeddedDatabaseType.H2) // 아래 주소도 절대경로로 바꿔주셔야함
                .addScript("file:E:/development2/Toby-spring5.0/src/main/vol1_chlee/ch7/lch/sqlservice/updatable/sqlRegistrySchema.sql")
                .build();
    }

}
