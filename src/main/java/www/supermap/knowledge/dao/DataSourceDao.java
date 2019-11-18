package www.supermap.knowledge.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.supermap.data.Recordset;

import www.supermap.knowledge.beans.GeoInfo;
import www.supermap.knowledge.beans.MyGeometry;

public interface DataSourceDao {

	/**
	 * 输入有swmu工作空间和udb数据源两种情况
	 * 
	 * @param dataFile
	 *            smwu或udb文件
	 * @return 所有数据源名称集合,hashMap中k为数据源，v为数据源中的数据集
	 */
	public HashMap<String, ArrayList<String>> selectAllDataSourceByFile(String dataFile);

	/**
	 * 获取特定数据集的所有实体,每个实体中包括geometry和primaryID(也就是数据集的记录id)
	 * 
	 * @param dataSourceName
	 * @param dataSetName
	 * @return 实体集合
	 */
	public List<MyGeometry> selectDataByDataSetName(String dataSourceName, String dataSetName);

	/**
	 * 根据数据源名称、数据集名称、记录集index来查询Recordset
	 * 
	 * @param dataSourceName
	 * @param dataSetName
	 * @param recordId
	 * @return
	 */
	public GeoInfo selectRecordById(String dataSourceName, String dataSetName, int recordId);

}
