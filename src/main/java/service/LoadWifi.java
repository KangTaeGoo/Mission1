package service;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import service.WifiService;
import wifi.Wifi;

import java.io.BufferedReader;


public class LoadWifi {


	public long LoadWifi() throws Exception {
		long totalCount = 0;
		List<Wifi> list = new ArrayList<>();
		JSONParser parser = new JSONParser();
		
		
		String key = "4b77676c476b746738366451757953";

		for(int i = 0 ; i < 15 ; i++) {
			StringBuilder sb = new StringBuilder();
			StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); 
			urlBuilder.append("/" +  URLEncoder.encode(key,"UTF-8") );
			urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") ); 
			urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8")); 
			urlBuilder.append("/" + URLEncoder.encode(String.valueOf(1+(i*1000)),"UTF-8")); 
			urlBuilder.append("/" + URLEncoder.encode(String.valueOf(1000+(i*1000)),"UTF-8")); 

			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			System.out.println("Response code: " + conn.getResponseCode()); 

			BufferedReader rd;

			if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			String line;
			
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();
			conn.disconnect();

			JSONObject jsonObj = (JSONObject)parser.parse(sb.toString());
			JSONObject TbPublicWifiInfo = (JSONObject) jsonObj.get("TbPublicWifiInfo");
			totalCount = (long) TbPublicWifiInfo.get("list_total_count");
			JSONArray row = (JSONArray) TbPublicWifiInfo.get("row");
			
			for (int j = 0; j < row.size(); j++) {
				JSONObject obj = (JSONObject) row.get(j);
				Wifi wifi = new Wifi();
				wifi.setMgrNo(String.valueOf(obj.get("X_SWIFI_MGR_NO")));
				wifi.setWrdofc(String.valueOf(obj.get("X_SWIFI_WRDOFC")));
				wifi.setMainNm(String.valueOf(obj.get("X_SWIFI_MAIN_NM")));
				wifi.setAdres1(String.valueOf(obj.get("X_SWIFI_ADRES1")));
				wifi.setAdres2(String.valueOf(obj.get("X_SWIFI_ADRES2")));
				wifi.setInstlFloor(String.valueOf(obj.get("X_SWIFI_INSTL_FLOOR")));
				wifi.setInstlTy(String.valueOf(obj.get("X_SWIFI_INSTL_TY")));
				wifi.setInstlMby(String.valueOf(obj.get("X_SWIFI_INSTL_MBY")));
				wifi.setSvcSe(String.valueOf(obj.get("X_SWIFI_SVC_SE")));
				wifi.setCmcwr(String.valueOf(obj.get("X_SWIFI_CMCWR")));
				wifi.setCnstcYear(String.valueOf(obj.get("X_SWIFI_CNSTC_YEAR")));
				wifi.setInoutDoor(String.valueOf(obj.get("X_SWIFI_INOUT_DOOR")));
				wifi.setRemars3(String.valueOf(obj.get("X_SWIFI_REMARS3")));
				wifi.setLnt(Double.parseDouble(String.valueOf(obj.get("LAT"))));
				wifi.setLat(Double.parseDouble(String.valueOf(obj.get("LNT"))));
				wifi.setWorkDttm(String.valueOf(obj.get("WORK_DTTM")));
				
				list.add(wifi);
				
			}
			
		
		}
		
		
		WifiService wifiService = new WifiService();
		wifiService.register(list);
		return totalCount;


	}


}