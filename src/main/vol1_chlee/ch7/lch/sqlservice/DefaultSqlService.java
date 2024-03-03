package main.vol1_chlee.ch7.lch.sqlservice;

public class DefaultSqlService extends BaseSqlService {
	// 생성자를 통해서 디폴트로 사용할 객체를 생성하고 의존관계를 설정한다
	public DefaultSqlService() {
		setSqlReader(new JaxbXmlSqlReader());
		setSqlRegistry(new HashMapSqlRegistry());
	}
	//== 초기화 메소드를 사용하는 방법 ==//
//	@PostConstruct
//	public void loadDefault() {
//		
//		if (super.sqlReader == null) {
//			setSqlReader(new JaxbXmlSqlReader());
//		}
//		
//		if (super.sqlRegistry == null) {
//			setSqlRegistry(new HashMapSqlRegistry());
//		}
//	}
}
