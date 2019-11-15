package www.supermap.knowledge.controller;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class AppTest {

	@Test
	public void testApp() {
		App app = new App();
	}

	@Test
	public void testGetKnowledgeGraph() {
		App app = new App();
		app.getKnowledgeGraph("hello", 13);
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
		fail("Not yet implemented");
	}

}
