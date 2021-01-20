package com.llb.cxy.core.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Component
public class PropertiesUtilsApplicationXML {
	
	private static Properties props;

    static {
    	try {
    		String path = new File("").getCanonicalPath() + File.separator + "application.yml";
    		if(new File(path).exists()) {
    			props = new Properties();
    			props.load(new FileInputStream(path));
    		} else {
    			Resource resource = new ClassPathResource("/application.yml");
    			props = PropertiesLoaderUtils.loadProperties(resource);
    		}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取属性
     * @param key
     * @return
     */
    public static String getProperty(String key){

        return props == null ? null :  props.getProperty(key);

    }

    /**
     * 获取属性
     * @param key 属性key
     * @param defaultValue 属性value
     * @return
     */
    public static String getProperty(String key,String defaultValue){

         return props == null ? null : props.getProperty(key, defaultValue);

    }

    /**
     * 获取properyies属性
     * @return
     */
    public static Properties getProperties(){
        return props;
    }
    
}
