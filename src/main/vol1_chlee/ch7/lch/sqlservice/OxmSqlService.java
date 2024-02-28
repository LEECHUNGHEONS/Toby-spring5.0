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
	
	// final로 변경 불가능하며, 두 개의 클래스는 강하게 결합
	private final OxmSqlReader oxmSqlReader = new OxmSqlReader();
	
	// 디폴트 오브젝트로 만들어진 프로퍼티, 필요에 따라 setter으로 변경
	private SqlRegistry sqlRegistry = new HashMapSqlRegistry();
	
	public void setSqlRegistry(SqlRegistry sqlRegistry) {
		this.sqlRegistry = sqlRegistry;
	}
	
	//OxmSqlService를 통해서 간접적으로 OxmSqlReader에게 DI
	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.oxmSqlReader.setUnmarshaller(unmarshaller);
	}
	
	public void setSqlmap(Resource sqlmap) {
		this.oxmSqlReader.setSqlmap(sqlmap);
	}
	
	//SqlService의 구현
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
		private Resource sqlmap = new ClassPathResource("/sqlmap.xml");
		
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
