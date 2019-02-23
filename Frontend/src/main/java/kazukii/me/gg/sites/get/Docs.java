package kazukii.me.gg.sites.get;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import kazukii.me.gg.main.Site;
import kazukii.me.gg.sites.Permission;
import spark.Request;
import spark.Response;
import spark.Route;

public class Docs extends Route{
	
	public Map<String, Object> m = new HashMap<String, Object>();
	
	public File file;
	public Docs(String path, File file) {
		super(path);
		this.file = file;
	}

	@Override
	public Object handle(Request request, Response response) {
		String title = file.getName().replaceAll(".md", "");
		String bar = title.substring(0, 1).toUpperCase() + title.substring(1);
		
		m.put("titlebar", bar);
		
		Permission.hasPermissions(request.cookie("session"), m);
		String content = null;
		try {
			content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Parser parser = Parser.builder().build();
		Node document = parser.parse(content);
		HtmlRenderer renderer = HtmlRenderer.builder().build();
		String s = renderer.render(document); 
		
		m.put("doc", s);
		
		try {
	        Template template = Site.cfg.getTemplate("doc.html");
	        Writer out = new StringWriter();
	        template.process(m, out);
	        return out.toString();

	    } catch (IOException | TemplateException e) {
	        throw new RuntimeException(e);
	    }
	}

}
