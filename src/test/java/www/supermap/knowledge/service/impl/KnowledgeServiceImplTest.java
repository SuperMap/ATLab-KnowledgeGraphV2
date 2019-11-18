package www.supermap.knowledge.service.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import www.supermap.knowledge.beans.GeoInfo;
import www.supermap.knowledge.beans.KnowledgeGraph;
import www.supermap.knowledge.dao.impl.JenaDaoImpl;

public class KnowledgeServiceImplTest {

	@Test
	public void testQueryByType() {
		String dataStorePath = "test";
		int gridLevel = 13;
		String dataSourceName = "SampleData\\sample.udb";
		String[] arTypes = {"停车场_1"};
		KnowledgeServiceImpl knowledgeServiceImpl = new KnowledgeServiceImpl();
		KnowledgeGraph knowledgeGraph = new KnowledgeGraph(dataStorePath, gridLevel);
		
		Map<String, List<GeoInfo>> infos = knowledgeServiceImpl.queryByType(knowledgeGraph, 106.553833, 29.6001, 1000, arTypes);
		for (Entry<String, List<GeoInfo>> entry : infos.entrySet()) {
			System.out.println(entry.getKey());
			for (GeoInfo geoInfo : entry.getValue()) {
				System.out.println(geoInfo);
			}
			System.out.println();
		}
		System.out.println(infos.size());
	}

	@Test
	public void testAddDataByType() {
		String dataStorePath = "test";
		int gridLevel = 13;
		String dataSourceName = "SampleData\\sample.udb";
		String[] arTypes = {"停车场_1"};
		KnowledgeServiceImpl knowledgeServiceImpl = new KnowledgeServiceImpl();
		KnowledgeGraph knowledgeGraph = new KnowledgeGraph(dataStorePath, gridLevel);
		boolean actual = knowledgeServiceImpl.addDataByType(knowledgeGraph, dataSourceName, arTypes);
		System.out.println(actual);
	}

}
