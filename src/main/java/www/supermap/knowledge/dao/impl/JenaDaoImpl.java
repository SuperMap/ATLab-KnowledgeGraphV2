package www.supermap.knowledge.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import www.supermap.knowledge.beans.Grid;
import www.supermap.knowledge.beans.MyGeometry;
import www.supermap.knowledge.controller.KnowledgeGraph;
import www.supermap.knowledge.dao.JenaDao;

public class JenaDaoImpl implements JenaDao {

	public JenaDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Map<String, ArrayList<String>> selectInfo(KnowledgeGraph targetKnowledgeGraph, double dLatitude,
			double dLongitude, double iRadius, String[] arType, String time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insertGeometrys(KnowledgeGraph targetKnowledgeGraph, List<Grid> girds, int dataSourceId) {
		// TODO Auto-generated method stub
		return false;
	}

}
