package main.vol1_chlee.ch7.learningtest.spring.oxm;

import main.vol1_chlee.ch7.lch.dao.TestDaoFactory;
import main.vol1_chlee.ch7.lch.sqlservice.jaxb.SqlType;
import main.vol1_chlee.ch7.lch.sqlservice.jaxb.Sqlmap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestDaoFactory.class})
public class OxmTest {
	@Autowired
	Unmarshaller unmarshaller;
	
	@Test 
	public void unmarshallSqlMap() throws XmlMappingException, IOException  {
		Source xmlSource = new StreamSource(getClass().getResourceAsStream("sqlmap.xml"));
		Sqlmap sqlmap = (Sqlmap)this.unmarshaller.unmarshal(xmlSource);
		
		List<SqlType> sqlList = sqlmap.getSql();
		assertEquals(sqlList.size(), 3);
		assertEquals(sqlList.get(0).getKey(), "add");
		assertEquals(sqlList.get(0).getValue(), "insert");
		assertEquals(sqlList.get(1).getKey(), "get");
		assertEquals(sqlList.get(1).getValue(), "select");
		assertEquals(sqlList.get(2).getKey(), "delete");
		assertEquals(sqlList.get(2).getValue(), "delete");
	}
}
