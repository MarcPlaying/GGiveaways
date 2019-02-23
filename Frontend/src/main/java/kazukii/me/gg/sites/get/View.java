package kazukii.me.gg.sites.get;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

public class View extends Route{

	public Map<String, Object> m = new HashMap<String, Object>();
	
	public View(String path) {
		super(path);
	}

	@Override
	public Object handle(Request request, Response response) {
		String id = request.queryParams("id");

		if(!(id == null)) {
			String sql = "SELECT * FROM `giveways` WHERE `id` = ?";
			try {
				PreparedStatement stmt= Site.connection.prepareStatement(sql);  
				stmt.setString(1, id);
				ResultSet rs=stmt.executeQuery();  
				while(rs.next()){
						m.put("titlebar", rs.getString("Title"));
						m.put("image", rs.getString("Image"));
						m.put("description", rs.getString("Description"));
						m.put("giveway", rs.getString("Giveway"));
						m.put("timestamp", rs.getString("Timestamp"));
						m.put("id", rs.getString("id"));
						m.put("enabled", rs.getString("enabled"));
						m.put("views", rs.getInt("views"));
						
						Giveway.addView(rs.getInt("id"));
						
						Permission.hasPermissions(request.cookie("session"),m);
						
						try {
					        Template template = Site.cfg.getTemplate("view.html");
					        Writer out = new StringWriter();
					        template.process(m, out);
					        return out.toString();

					    } catch (IOException | TemplateException e) {
					        throw new RuntimeException(e);
					    }
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			return id;
		}else {
			return null;
		}
	}

}
