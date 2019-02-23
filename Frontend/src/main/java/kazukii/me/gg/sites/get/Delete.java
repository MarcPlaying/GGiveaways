package kazukii.me.gg.sites.get;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import kazukii.me.gg.main.Site;
import kazukii.me.gg.sites.Permission;
import spark.Request;
import spark.Response;
import spark.Route;

public class Delete extends Route{

	public Delete(String path) {
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
		
		 String query = "DELETE FROM `giveways` WHERE id = ?";
	     PreparedStatement preparedStmt;
		try {
			preparedStmt = Site.connection.prepareStatement(query);
			preparedStmt.setString   (1, request.queryParams("id"));
		    preparedStmt.executeUpdate();
		} catch (SQLException e) {
			response.redirect("/home?a=Can't delete this Giveaway");
			e.printStackTrace();
		}
	      
	      
		
		response.redirect("/?a=Deleted Giveaway");
		return "";
	}

}
