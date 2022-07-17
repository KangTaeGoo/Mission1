package service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import wifi.Wifi;


public class WifiService {
	public List<Wifi> aroundWifi(String my_Lat, String my_Lnt){
		double MyLat = Double.parseDouble(my_Lat);
		double MyLnt = Double.parseDouble(my_Lnt);
		
		
		List<Wifi> wifiList = new ArrayList<>();
		Connection conn = getConnection();
		PreparedStatement pstat = null;
		ResultSet rs = null;

		String sql = "select * ,"
				+ "		(6371 * acos ( "
				+ "     cos(radians("+MyLat+")) "
				+ "           *cos(radians(LAT)) "
				+ "           *cos(radians(LNT) - radians("+MyLnt+")) "
				+ "           +sin(radians("+MyLat+")) "
				+ "           *sin(radians(LAT)) "
				+ "           ) "
				+ "        )AS dist "
				+ " from wifi order by dist LIMIT 20; ";
		

		try {
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();

			while(rs.next()){
				double dist = rs.getDouble("dist");
				String mgrNo = rs.getString("X_SWIFI_MGR_NO");
				String wrdofc = rs.getString("X_SWIFI_WRDOFC");
				String mainNm = rs.getString("X_SWIFI_MAIN_NM");
				String adres1 = rs.getString("X_SWIFI_ADRES1");
				String adres2 = rs.getString("X_SWIFI_ADRES2");
				String instlFloor = rs.getString("X_SWIFI_INSTL_FLOOR");
				String instlTy = rs.getString("X_SWIFI_INSTL_TY");
				String instlMby = rs.getString("X_SWIFI_INSTL_MBY");
				String svcSe = rs.getString("X_SWIFI_SVC_SE");
				String cmcwr = rs.getString("X_SWIFI_CMCWR");
				String cnstcYear = rs.getString("X_SWIFI_CNSTC_YEAR");
				String inoutDoor = rs.getString("X_SWIFI_INOUT_DOOR");
				String remars3 = rs.getString("X_SWIFI_REMARS3");
				double lat = rs.getDouble("LAT");
				double lnt = rs.getDouble("LNT");
				String workDttm = rs.getString("WORK_DTTM");

				Wifi wifi = new Wifi();
				wifi.setDist(dist);
				wifi.setMgrNo(mgrNo);
				wifi.setWrdofc(wrdofc);
				wifi.setMainNm(mainNm);
				wifi.setAdres1(adres1);
				wifi.setAdres2(adres2);
				wifi.setInstlFloor(instlFloor);
				wifi.setInstlTy(instlTy);
				wifi.setInstlMby(instlMby);
				wifi.setSvcSe(svcSe);
				wifi.setCmcwr(cmcwr);
				wifi.setCnstcYear(cnstcYear);
				wifi.setInoutDoor(inoutDoor);
				wifi.setRemars3(remars3);
				wifi.setLat(lat);
				wifi.setLnt(lnt);
				wifi.setWorkDttm(workDttm);

				wifiList.add(wifi);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			close(conn);
			close(pstat);
			close(rs);
		}
		return wifiList;	
	}

	public void register(List<Wifi> wifiList) throws SQLException {

		Connection conn = getConnection();		
		PreparedStatement pstat = null;

		try {
			conn.setAutoCommit(false);

			String sql = "INSERT INTO wifi"
					+ " (X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, X_SWIFI_ADRES2, "
					+ "	X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, LAT, LNT, WORK_DTTM) "
					+ " VALUES"
					+ " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ";

			pstat = conn.prepareStatement(sql);
			int cnt = 0;
			for(Wifi wifi : wifiList) {
			

				pstat.setString(1, wifi.getMgrNo());
				pstat.setString(2, wifi.getWrdofc());
				pstat.setString(3, wifi.getMainNm());
				pstat.setString(4, wifi.getAdres1());
				pstat.setString(5, wifi.getAdres2());
				pstat.setString(6, wifi.getInstlTy());
				pstat.setString(7, wifi.getInstlMby());
				pstat.setString(8, wifi.getSvcSe());
				pstat.setString(9, wifi.getCmcwr());
				pstat.setString(10, wifi.getCnstcYear());
				pstat.setString(11, wifi.getInoutDoor());
				pstat.setDouble(12, wifi.getLat());
				pstat.setDouble(13, wifi.getLnt());
				pstat.setString(14, wifi.getWorkDttm());
				cnt++;
				pstat.addBatch();
				pstat.clearParameters();
				if(cnt % 1000 == 0) {
					pstat.executeBatch();
					pstat.clearBatch();
					conn.commit();

				}
				//			
				//				 int affected = pstat.executeUpdate();
				//
				//		            if(affected > 0){
				//	                System.out.println(" 저장 성공 ");
				//		            }else{
				//		            System.out.println(" 저장 실패 ");
				//		            }
			}
			pstat.executeBatch();
			pstat.clearParameters();
			conn.commit();
			System.out.println(" 저장 성공 ");

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstat);
			close(conn);
			
		}


	}



	public Connection getConnection() {

		String url = "jdbc:mariadb://localhost:3306/wifidb";
		String dbUserId = "testuser";
		String dbPassword = "zerobase";

		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");
			System.out.println("Successfully Connection!");

		} catch (ClassNotFoundException e) {		
			e.printStackTrace();
		}

		try {
			conn = DriverManager.getConnection(url,dbUserId,dbPassword);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}

	public void close(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void close(PreparedStatement pstat) {
		try {
			if(pstat != null && !pstat.isClosed()){
				pstat.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close(ResultSet rs) {
		try {
			if(rs !=null && !rs.isClosed()){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
