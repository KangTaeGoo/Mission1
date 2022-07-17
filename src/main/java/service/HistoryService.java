package service;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import wifi.History;


public class HistoryService extends WifiService {

	public void HistoryIn(String my_Lat, String my_Lnt) {
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm:ss");

		Connection conn = getConnection();
		PreparedStatement pstat = null;

		try {
			String sql = "INSERT INTO history ( LAT, LNT, INQUIRE_TIME) "
					+ "VALUES (?, ?, ? );";

			pstat = conn.prepareStatement(sql);


			pstat.setString(1, my_Lat);
			pstat.setString(2, my_Lnt);
			pstat.setString(3, dateTime.format(formatter));


			int affected = pstat.executeUpdate();

			if(affected > 0){
				System.out.println(" 저장 성공 ");
			}else{
				System.out.println(" 저장 실패 ");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstat);
			close(conn);

		}
	}

	public List<History> list(){

		List<History> historyList = new ArrayList<>();
		Connection conn = getConnection();
		PreparedStatement pstat = null;
		ResultSet rs = null;

		try {
			String sql = "select  ID"
					+ " ,LAT AS LAT "
					+ " , LNT AS LNT "
					+ " ,INQUIRE_TIME "
					+ " From HISTORY "
					+"order by ID desc;"
					;

			pstat = conn.prepareStatement(sql);

			rs = pstat.executeQuery();

			while(rs.next()) {
				int id = rs.getInt("ID");
				double lat = rs.getDouble("LAT");
				double lnt = rs.getDouble("LNT");
				String inquireTime = rs.getString("INQUIRE_TIME");

				History history = new History();
				history.setId(id);
				history.setLat(lat);
				history.setLnt(lnt);
				history.setInquireTime(inquireTime);
				historyList.add(history);

				System.out.println(id + ", "+ lat + ", "+ lnt + ", "+ inquireTime);
			}

		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstat);
			close(conn);
		}


		return historyList;
	}

	public int delete (int id)  throws SQLException {

		Connection conn = getConnection();
		PreparedStatement pstat = null;
		int affected = 0;

		try {
			String sql = "delete from history "
					+ "where ID = ? ";


			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id);

			affected = pstat.executeUpdate();

			if(affected > 0){
				System.out.println("삭제 성공 ");
			}else{
				System.out.println("삭제 실패 ");
			}

		}catch (SQLException e) {
			e.printStackTrace();

		}finally {

			close(pstat);
			close(conn);
		}
		return affected;




	}
}
