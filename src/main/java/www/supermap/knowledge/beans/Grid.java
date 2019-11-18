package www.supermap.knowledge.beans;

import java.util.List;

public class Grid {

	private long gridId;
	private List<MyGeometry> myGeometrys;

	public Grid(long gridId, List<MyGeometry> myGeometrys) {
		// TODO Auto-generated constructor stub
		this.gridId = gridId;
		this.myGeometrys = myGeometrys;
	}

	public long getGridId() {
		return gridId;
	}

	public List<MyGeometry> getMyGeometrys() {
		return myGeometrys;
	}

	public void setGridId(long gridId) {
		this.gridId = gridId;
	}

	public void setGeometrys(List<MyGeometry> myGeometrys) {
		this.myGeometrys = myGeometrys;
	}

	@Override
	public String toString() {
		return "Grid [gridId=" + gridId + ", geometrys=" + myGeometrys + "]";
	}

}
