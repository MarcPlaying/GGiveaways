package kazukii.me.gg.sites;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kazukii.me.gg.main.Site;

public class Permission {
	
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
