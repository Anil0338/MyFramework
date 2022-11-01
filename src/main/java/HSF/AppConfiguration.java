package HSF;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class AppConfiguration {
	
	String result = "";
	InputStream inputStream = null;
 
		/*	This method is to read data from config.properties file
		 *  @param/type key/String - Pass the key name retrives the value
		 *	@Modified by Dimpal - 12/12/2018
		 */
		public String getPropValues(String key) throws IOException {
			FileInputStream inputStream=null;
			try {
				Properties prop = new Properties();
				inputStream = new FileInputStream("config.properties");
				
					if (inputStream != null) {
						prop.load(inputStream);
						result = prop.getProperty(key);
					}
				// get the property value
				
			} catch (Exception e) {
				System.out.println("Exception: " + e);
			} finally {
				inputStream.close();
			}
			return result;		
		}
		
	
		/*	This method is to write data into config.properties file
		 *  @param/type key/String - key name will be stored in file
		 *  @param/type value/String - value name will be stored in file
		 *  If there is any existing key with same name will overwrite the data
		 *	@Modified by Dimpal - 12/12/2018
		 */
		public void putPropValues(String key, String value) throws IOException {
		
		 try {
			 	File file = new File("config.properties");
			 	
			 	FileInputStream in = new FileInputStream( file );
			 	Properties prop = new Properties();
			 	prop.load(in);
			 	in.close();
			 	
			 	FileOutputStream out = new FileOutputStream( file );
			 	prop.setProperty(key, value);
			 	prop.store(out, value);
				out.close();
		    }
		    catch (Exception e ) {
		        e.printStackTrace();
		    }
		
	}
	
}




