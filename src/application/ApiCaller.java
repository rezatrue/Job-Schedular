package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.net.HttpURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

public class ApiCaller {
	private String saveActivityPath = "http://localhost/task-api/log/save.php";
	private String responseString;
	public ApiCaller() {
	}
	
	public void saveLog(LogInfo info) {
		
		try {
			URL url = new URL(saveActivityPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			
			// date must be send as formatted string
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
    	    
			String input = "{\"empid\":\""+ info.getEmpid() +"\",\"taskid\":\""+ info.getTaskid() +"\","
					+ "\"starttime\":\""+ formatter.format(info.getStarttime()) + "\",\"endtime\":\"" + formatter.format(info.getEndtime()) + "\","
					+ "\"keycount\":\""+ info.getKeycount() +"\",\"mousecount\":\""+ info.getMousecount() +"\","
					+ "\"image\":\""+ info.getImage() +"\",\"base64encodedImage\":\""+ info.getBase64encodedImage() + "\"}";
System.out.println("input > > > :" + input);
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			
			/*
			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			*/
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			StringBuilder sb = new StringBuilder();
			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				sb.append(output);
			}
			responseString = sb.toString();
			conn.disconnect();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		// need to fix
		// extracting data from response 
//		JSONObject obj = new JSONObject(responseString);
//	    String res = obj.getString("response");
//	    String tasks = obj.getString("tasks");
//	    System.out.println(res + " : "+ tasks);
		
	}

}
