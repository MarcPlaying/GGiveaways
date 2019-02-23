package kazukii.me.gg.sites;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import kazukii.me.gg.main.Site;

public class Permission {
	
	public static boolean hasPermissions(String cookie, Map<String, Object> map) {
		try {
			String sql = "SELECT * FROM `users` WHERE `cookie` = ?";
			PreparedStatement stmt= Site.connection.prepareStatement(sql);  
			stmt.setString(1, cookie);
			ResultSet rs=stmt.executeQuery();  
			while(rs.next()) {
				map.put("username", rs.getString("username"));
				map.put("loggedin", "true");
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		map.put("loggedin", "false");
		return false;
		
	}
	
	public static boolean hasPermissions(String cookie) {
		try {
			String sql = "SELECT * FROM `users` WHERE `cookie` = ?";
			PreparedStatement stmt= Site.connection.prepareStatement(sql);  
			stmt.setString(1, cookie);
			ResultSet rs=stmt.executeQuery();  
			while(rs.next()) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	
	

}
