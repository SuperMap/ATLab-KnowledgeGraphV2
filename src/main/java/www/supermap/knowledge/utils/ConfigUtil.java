package www.supermap.knowledge.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import www.supermap.knowledge.beans.KnowledgeGraph;
import www.supermap.knowledge.beans.Parameter;

/**
 * 配置文件工具类
 * 配置文件数据结构：{{"1":knowledge},{"2":knowledge} ...}
 * @author SunYasong
 *
 */
public class ConfigUtil {

	private static final Logger logger = LoggerFactory.getLogger(ConfigUtil.class);
	
	private static ConfigUtil configuration;
	
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
     * 将json字符串写入文件
     * @param fileName
     * @return
     */
    public boolean writeJsonFile(String filePath, String jsonStr) {
		try {
			FileWriter fw = new FileWriter(filePath);
			PrintWriter out = new PrintWriter(fw);
			out.write(jsonStr);
			out.println();
			fw.close();
			out.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
    }
    

	/**
	 * 将知识图谱信息添加到配置文件中
	 * @param knowledgeGraph
	 * @return
	 */
	public boolean addKnowledgeGraphToConfigFile(KnowledgeGraph knowledgeGraph) {
		String jsonString = readJsonFile(Parameter.KNOWLEDGE_CONFIG_FILE_PATH);
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		int knowledgeGraphId = jsonObject==null?0:jsonObject.size();
		String jsonKey = String.valueOf(knowledgeGraphId);
		jsonObject.put(jsonKey, knowledgeGraph);
		return writeJsonFile(Parameter.KNOWLEDGE_CONFIG_FILE_PATH, jsonObject.toJSONString());
	}
}
