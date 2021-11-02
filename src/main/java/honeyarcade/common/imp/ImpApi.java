package honeyarcade.common.imp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ImpApi {

	public static HashMap<String, Object> sendPostJson(String targetUrl, HashMap paramMap) throws Exception {

		HashMap<String, Object> returnMap = null;
		URL url = new URL(targetUrl);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

		con.setRequestMethod("POST"); 
		con.setRequestProperty("content-type", "application/json");
		con.setRequestProperty("Accept-Charset", "UTF-8");
		if(paramMap == null) {
			con.setDoOutput(false); 
		}else {
			con.setDoOutput(true);
		}
		if(paramMap != null) { 
			OutputStream os = con.getOutputStream();
			os.flush();
			os.close();
		}
		if (con.getResponseCode() == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
			String inputLine;  
			StringBuffer response = new StringBuffer();  
			while ((inputLine = in.readLine()) != null) {  
				response.append(inputLine);  
			}  
			in.close();
			ObjectMapper mapper = new ObjectMapper();
			returnMap = mapper.readValue(response.toString(), new TypeReference<HashMap<String, Object>>() {});
		}
		return returnMap;
	}

}
