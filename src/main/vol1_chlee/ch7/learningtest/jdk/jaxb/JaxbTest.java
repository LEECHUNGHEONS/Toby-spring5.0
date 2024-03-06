package main.vol1_chlee.ch7.learningtest.jdk.jaxb;

import main.vol1_chlee.ch7.lch.sqlservice.jaxb.SqlType;
import main.vol1_chlee.ch7.lch.sqlservice.jaxb.Sqlmap;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JaxbTest {
	
	@Test
	public void readSqlmap() throws JAXBException, IOException {
		
		// 바인딩용 클래스들의 위치, context를 생성할 때 필요하다
		String contextPath = Sqlmap.class.getPackage().getName();
		
		// context 생성
		JAXBContext context = JAXBContext.newInstance(contextPath);
		
		// 언마샬러 생성
		Unmarshaller unmarshaller = context.createUnmarshaller();
		
		// xml 파일을 읽어서 언마샬을 진행하면 매핑된 오브젝트를 리턴한다
		String path = "E:\\development2\\Toby-spring5.0\\src\\main\\vol1_chlee\\ch7\\learningtest\\jdk\\jaxb\\sqlmap.xml";
		FileReader reader = new FileReader(path);
		Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(reader);

		List<SqlType> sqlList = sqlmap.getSql();
		
		//assertEquals(sqlList.size(), 6);
		assertEquals(sqlList.get(0).getKey(), "add");
		assertEquals(sqlList.get(0).getValue(), "insert");
		assertEquals(sqlList.get(1).getKey(), "get");
		assertEquals(sqlList.get(1).getValue(), "select");
		assertEquals(sqlList.get(2).getKey(), "delete");
		assertEquals(sqlList.get(2).getValue(), "delete");
		
	}

}
