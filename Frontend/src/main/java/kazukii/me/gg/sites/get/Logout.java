package kazukii.me.gg.sites.get;

import spark.Request;
import spark.Response;
import spark.Route;

public class Logout extends Route{

	public Logout(String path) {
		super(path);
	}

	@Override
	public Object handle(Request request, Response response) {
		
		response.removeCookie("session");
		
		response.redirect("/");
		return "";
	}
	
}
