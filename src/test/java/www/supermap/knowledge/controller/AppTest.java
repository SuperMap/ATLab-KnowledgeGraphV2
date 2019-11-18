package www.supermap.knowledge.controller;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

import www.supermap.knowledge.beans.KnowledgeGraph;
import www.supermap.knowledge.utils.ConfigUtil;
import www.supermap.knowledge.utils.Parameter;

public class AppTest {

	@Test
	public void testApp() {
		App app = new App();
	}

	@Test
	public void testGetKnowledgeGraph() {
		App app = new App();
		KnowledgeGraph knowledgeGraph = app.getKnowledgeGraph("test", 13);
		System.out.println(knowledgeGraph);
	}

	@Test
	public void testListAllKnowledgeGraph() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddDataByType() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddAllData() {
		fail("Not yet implemented");
	}

	@Test
	public void testQuery() {
//		App app = new App();
//		app.deleteKnowledge();
	}

}
