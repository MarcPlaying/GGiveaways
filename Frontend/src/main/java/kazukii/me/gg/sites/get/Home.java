package kazukii.me.gg.sites.get;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import kazukii.me.gg.content.Giveway;
import kazukii.me.gg.main.Site;
import kazukii.me.gg.sites.Permission;
import spark.Request;
import spark.Response;
import spark.Route;

public class Home extends Route{

	public Map<String, Object> m = new HashMap<String, Object>();
	
	public Home(String path) {
		super(path);
	}

	@Override
	public Object handle(Request request, Response response) {
		m.put("titlebar", "Home");
		
		Permission.hasPermissions(request.cookie("session"), m);
		
		if(request.queryParams("a") == null) {
			m.put("alert", "");
		}else {
			m.put("alert", request.queryParams("a"));
		}
		
		//Latest Giveaways
		ArrayList<Giveway> neww = new ArrayList<Giveway>(); 
		
		String sqqqll = "SELECT * FROM `giveways` WHERE `enabled` = 1";
		Statement stmt2 = null;
		try {
			stmt2 = Site.connection.createStatement();
			ResultSet rs = stmt2.executeQuery(sqqqll);
			while(rs.next()) {
				Giveway n = new Giveway();
				n.setGiveway(rs.getString("Giveway"));
				n.setID(new StringBuilder().append(rs.getInt("id")).toString());
				n.setImage(rs.getString("Image"));
				n.setDescription(rs.getString("Description"));
				n.setTimestamp(rs.getString("Timestamp"));
				n.setTitle(rs.getString("Title"));
				
				neww.add(n);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		m.put("giveway", neww);
		
		try {
	        Template template = Site.cfg.getTemplate("home.html");
	        Writer out = new StringWriter();
	        template.process(m, out);
	        return out.toString();

	    } catch (IOException | TemplateException e) {
	        throw new RuntimeException(e);
	    }
	}

}
