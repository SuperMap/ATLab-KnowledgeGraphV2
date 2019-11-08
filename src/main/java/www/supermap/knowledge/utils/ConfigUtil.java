package www.supermap.knowledge.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.supermap.data.DatasourceConnectionInfo;

public class ConfigUtil {

	private static final Logger logger = LoggerFactory.getLogger(ConfigUtil.class);
	
	private static ConfigUtil configuration;
	
	// 默认数据源配置文件位置
	private String defaultDataSourceConfigurationFilePath = "config\\dataSource.json";
	
	// 默认知识图谱配置文件
	private String defaultKnowledgeGraphConfigFilePath = "config\\knowledgeConfig.json";
	
	
	public ConfigUtil() {
		// TODO Auto-generated constructor stub
	}

	//单例
	public static ConfigUtil getInstance(){
		try{
			if(configuration == null){
				synchronized (ConfigUtil.class) {
					if(configuration == null){
						configuration = new ConfigUtil();
					}
				}
			}
			
		} catch(Exception e){
			logger.debug("单例类获取失败");
		}
		
		return configuration;
	}
	
	
	
	public String getDefaultDataSourceConfigurationFilepath() {
		return defaultDataSourceConfigurationFilePath;
	}

	public void setDefaultDataSourceConfigurationFilepath(String defaultDataSourceConfigurationFilepath) {
		this.defaultDataSourceConfigurationFilePath = defaultDataSourceConfigurationFilepath;
	}

	
	/**
     * 读取json文件，返回json串
     * @param fileName
     * @return
     */
    public String readJsonFile(String fileName) {
        String jsonStr = "";
        File jsonFile = new File(fileName);
        
        // 文件不存在则创建
        if(!jsonFile.exists()){
			try {
				jsonFile.createNewFile();
				return null;
			} catch (IOException e) {
				logger.debug(fileName+"创建失败");
			}
		}
        
        try {
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("读取json文件失败");
            return null;
        }
    }
	
    
	/**
	 * 从dataSourceConfigurationFile中获得全局id
	 * @return
	 */
	public int getDataSourceConnectionId(DatasourceConnectionInfo datasourceConnectionInfo){
		//读取json配置文件，与传进来的datasourceConnectionInfo进行对比，返回一个数，全局代表该连接
		//待完善
		
//		String jsonString = readJsonFile(defaultDataSourceConfigurationFilePath);
//		JSONObject jsonObject = JSONObject.parseObject(jsonString);
//		if(jsonObject == null){
//			logger.debug("json文件转换后的对象为空");
//			
//		}
		
		return 1;
	}
}
