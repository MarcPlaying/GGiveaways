package kazukii.me.gg.sites.get;

import java.io.File;
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

public class SimpleTemplates extends Route{
	
	public Map<String, Object> m = new HashMap<String, Object>();
	
	public File file;
	public SimpleTemplates(String path, File file) {
		super(path);
		this.file = file;
	}

	@Override
	public Object handle(Request request, Response response) {
		String title = file.getName().replaceAll(".html", "");
		String bar = title.substring(0, 1).toUpperCase() + title.substring(1);
		
		m.put("titlebar", bar);
		
		Permission.hasPermissions(request.cookie("session"), m);

		try {
	        Template template = Site.cfg.getTemplate(file.getName());
	        Writer out = new StringWriter();
	        template.process(m, out);
	        return out.toString();

	    } catch (IOException | TemplateException e) {
	        throw new RuntimeException(e);
	    }
	}

}
