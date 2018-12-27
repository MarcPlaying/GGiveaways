package kazukii.me.gg.sites.get;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Template;
import freemarker.template.TemplateException;
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
		
		Boolean hasPermissions = Permission.hasPermissions(request.cookie("session"));
		if(hasPermissions) {
			m.put("loggedin", "true");
		}else {
			m.put("loggedin", "false");
		}
		
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
