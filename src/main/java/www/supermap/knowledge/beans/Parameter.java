package www.supermap.knowledge.beans;

import java.io.File;

/**
 * 项目中用到的参数
 * @author SunYasong
 *
 */
public final class Parameter {
	/**
	 * 1. App,管理知识图谱配置，主要存储各个知识图谱的存储路径与各网格等级
	 */
	// 配置文件目录
	public static final String KNOWLEDGE_CONFIG_FILE_DIR = "";
	// 配置文件名称
	public static final String KNOWLEDGE_CONFIG_FILE_NAME = "knowledge_config.json";
	//配置文件完整的路径名
	public static final String KNOWLEDGE_CONFIG_FILE_PATH = KNOWLEDGE_CONFIG_FILE_DIR + File.separator + KNOWLEDGE_CONFIG_FILE_NAME;
	
	
	/**
	 * 2. 知识图谱相关
	 */
	//知识图谱存储时用来分割数据源、数据集、记录集id的符号
	public static final String DATA_SOURCE_SET_SEGMENT = "_";
	
	//知识图谱存储每一条记录的id，包括数据源id、数据集id、记录集index的id共三部分，所以值为3
	public static final int ID_PART_NUMBER = 3;
	
	//知识图谱中存储数据源、数据集ID的表,或者叫model
	public static final String DATA_SOURCE_SET_MODEL_NAME = "datasource";
	
	//默认图谱名称及位置
	public static final String DATA_SOURCE_PATH = "sample";
	//默认图谱网格等级
	public static final int GRID_LEVEL = 13;
}
