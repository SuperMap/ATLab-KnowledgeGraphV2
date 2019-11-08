package www.supermap.knowledge.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import www.supermap.knowledge.beans.Grid;
import www.supermap.knowledge.controller.KnowledgeGraph;

public interface JenaDao {

	public boolean insertGeometrys(KnowledgeGraph targetKnowledgeGraph, List<Grid> girds, int dataSourceId);

	public Map<String, ArrayList<String>> selectInfo(KnowledgeGraph targetKnowledgeGraph, double dLatitude, double dLongitude, double iRadius, String[] arType, String time);

	public boolean createDataSet();
	
	
}
