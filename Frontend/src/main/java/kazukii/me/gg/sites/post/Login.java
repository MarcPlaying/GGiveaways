package kazukii.me.gg.sites.post;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kazukii.me.gg.main.Site;
import spark.Request;
import spark.Response;
import spark.Route;

public class Login extends Route{

	public Login(String path) {
		super(path);
	}

	@Override
	public Object handle(Request request, Response response) {
		String username = request.queryParams("username");
		String password = request.queryParams("password");
		
		try {
			String sql = "SELECT * FROM `users` WHERE `username` = ? AND `password` = ?";
			PreparedStatement stmt= Site.connection.prepareStatement(sql);  
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs=stmt.executeQuery();  
			while(rs.next()){
				response.cookie("session", rs.getString("cookie"));
			}
		
		response.redirect("/");
		return "";
		
		}catch(Exception e) {
			return "SQL Exception: Check your SQL Syntax at PreparedStatement File Login.java Line 26";
		}
		
	
	}

}
