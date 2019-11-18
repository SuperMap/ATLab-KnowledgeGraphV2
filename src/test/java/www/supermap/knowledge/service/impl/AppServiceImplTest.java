package www.supermap.knowledge.service.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

import www.supermap.knowledge.beans.KnowledgeGraph;
import www.supermap.knowledge.service.AppService;

public class AppServiceImplTest {
	
	AppService appService = new AppServiceImpl();
	@Test
	public void testGetAllKnowledgeGraph() {
		List<KnowledgeGraph> allKnowledgeGraph = appService.getAllKnowledgeGraph();
		for (KnowledgeGraph knowledgeGraph : allKnowledgeGraph) {
			System.out.println(knowledgeGraph);
		}
	}
	

	@Test
	public void testGetKnowledgeGraph() {
		KnowledgeGraph knowledgeGraph = appService.getKnowledgeGraph("test1", 14);
		System.out.println(knowledgeGraph);
	}

}
