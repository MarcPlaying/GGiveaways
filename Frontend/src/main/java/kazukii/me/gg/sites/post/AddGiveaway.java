package kazukii.me.gg.sites.post;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import kazukii.me.gg.main.Site;
import kazukii.me.gg.sites.Permission;
import spark.Request;
import spark.Response;
import spark.Route;

public class AddGiveaway extends Route{


	public AddGiveaway(String path) {
		super(path);
	}

	@Override
	public Object handle(Request request, Response response) {
		Boolean hasPermissions = Permission.hasPermissions(request.cookie("session"));
		
		if(!hasPermissions) {
			return null;
		}
		
		String enabled = request.queryParams("enabled");
		String image = request.queryParams("image");
		String title = request.queryParams("title");
		String description = request.queryParams("description");
		String gleam = request.queryParams("giveaway");
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();  
	    String timestamp = formatter.format(date);
	    
	    String sql = "INSERT INTO `giveways`(`enabled`, `Image`, `Title`, "
	    		+ "`Description`, `Giveway`, `Timestamp`) VALUES (?,?,?,?,?,?)";
	    
	    	try (PreparedStatement pstmt = Site.connection.prepareStatement(sql)) {
	    	    pstmt.setString(1, enabled);
	    	    pstmt.setString(2, image);
	    	    pstmt.setString(3, title);
	    	    pstmt.setString(4, description);
	    	    pstmt.setString(5, gleam);
	    	    pstmt.setString(6, timestamp);
	    	    pstmt.executeUpdate();

		    } catch (SQLException e) {
		    	e.printStackTrace();
		    	return "SQL Exception check your Syntax at File: AddGiveaway.java at Line 64";
		    	
	    	}

	    response.redirect("/?a=Giveaway "+title+" was created");
		return "";
	
	
}

}
