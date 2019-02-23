package kazukii.me.gg.sites.get;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import kazukii.me.gg.main.Site;
import kazukii.me.gg.sites.Permission;
import spark.Request;
import spark.Response;
import spark.Route;

public class Show extends Route{

	public Show(String path) {
		super(path);
	}

	@Override
	public Object handle(Request request, Response response) {
		Boolean hasPermissions = Permission.hasPermissions(request.cookie("session"));
		
		if(hasPermissions == false) {
			return null;
		}
		
		if(request.queryParams("id") == null) {
			response.redirect("/?a=ERROR: You need to target a ID for posting it to Discord");
		}
		
		 String query = "UPDATE giveways SET enabled = 1 WHERE id = ?";
	     PreparedStatement preparedStmt;
		try {
			preparedStmt = Site.connection.prepareStatement(query);
			preparedStmt.setString   (1, request.queryParams("id"));
		    preparedStmt.executeUpdate();
		} catch (SQLException e) {
			response.redirect("/home?a=Can't Show this Giveaway");
			e.printStackTrace();
		}
	      
	      
		
		response.redirect("/view?id="+request.queryParams("id"));
		return "";
	}

}
