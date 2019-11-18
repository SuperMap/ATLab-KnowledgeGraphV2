package www.supermap.knowledge.utils;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import www.supermap.knowledge.beans.KnowledgeGraph;

public class ConfigUtilTest {
	
	@Test
	public void testGetInstance() {
		ConfigUtil configUtil = ConfigUtil.getInstance();
	}

	@Test
	public void testReadJsonFile() {
		
	}

	@Test
	public void testWriteJsonFile() {
		
	}

	@Test
	public void testAddKnowledgeGraphToConfigFile() {
		ConfigUtil configUtil = ConfigUtil.getInstance();
		boolean actual = configUtil.addKnowledgeGraphToConfigFile(new KnowledgeGraph("test1", 13));
		Assert.assertEquals(true, actual);
	}

}
