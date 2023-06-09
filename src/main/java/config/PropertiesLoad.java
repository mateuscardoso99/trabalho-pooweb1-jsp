package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//clsse Properties pode ser usado para recuperar as propriedades do sistema e armazenar e recuperar dados do tipo string do arquivo de propriedades
public class PropertiesLoad {
	public static Properties loadProperties(){
        Properties config = new Properties();
        try{
            InputStream inputStream = PropertiesLoad.class.getClassLoader().getResourceAsStream("application.properties");
            config.load(inputStream);
            inputStream.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return config;
    }
}