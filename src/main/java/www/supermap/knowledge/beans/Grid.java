package www.supermap.knowledge.beans;

import java.util.List;

public class Grid {
	
	private long gridId;
	private List<MyGeometry> geometrys;

	public Grid(long gridId) {
		// TODO Auto-generated constructor stub
		this.gridId = gridId;
	}

	public long getGridId() {
		return gridId;
	}

	public List<MyGeometry> getGeometrys() {
		return geometrys;
	}

	public void setGridId(long gridId) {
		this.gridId = gridId;
	}

	public void setGeometrys(List<MyGeometry> geometrys) {
		this.geometrys = geometrys;
	}
	
	

}
