package kazukii.me.gg.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.PropertyConfigurator;
import org.json.simple.parser.ParseException;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import kazukii.me.gg.configs.Config;
import kazukii.me.gg.configs.u;
import kazukii.me.gg.sites.get.Delete;
import kazukii.me.gg.sites.get.Giveaways;
import kazukii.me.gg.sites.get.Hide;
import kazukii.me.gg.sites.get.Home;
import kazukii.me.gg.sites.get.Logout;
import kazukii.me.gg.sites.get.Post;
import kazukii.me.gg.sites.get.PostLatestGiveaway;
import kazukii.me.gg.sites.get.Show;
import kazukii.me.gg.sites.get.SimpleTemplates;
import kazukii.me.gg.sites.get.View;
import kazukii.me.gg.sites.post.AddGiveaway;
import kazukii.me.gg.sites.post.Login;
import spark.Route;
import spark.Spark;

public class Site {
	
	//Statics
	public static Connection connection = null;
	public static Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
	
	public static ArrayList<Route> postroutes = new ArrayList<Route>();
	public static ArrayList<Route> getroutes = new ArrayList<Route>();
	
	public static ArrayList<File> ignorelist = new ArrayList<File>();
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		Config.createConfig();
		Config.loadConfig();
		makeJDBCConnection();
		
		Spark.setPort(Integer.parseInt(Config.getString("sparkport")));

		String log4jConfPath ="log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
		
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);
		cfg.setWrapUncheckedExceptions(true);
		
		File templates = new File("templates/");
		
		
		if(!templates.exists())templates.mkdirs();
		
		
		cfg.setDirectoryForTemplateLoading(templates);
		
		File[] alltemplates = templates.listFiles();
		
		ignorelist.add(new File("templates/base.html"));
		ignorelist.add(new File("templates/footer.html"));
		ignorelist.add(new File("templates/navbar.html"));
		ignorelist.add(new File("templates/view.html"));
		ignorelist.add(new File("templates/giveways.html"));
		ignorelist.add(new File("templates/home.html"));
		
		// Init routes
		postroutes.add(new AddGiveaway("/addgiveaway"));
		postroutes.add(new Login("/login"));
		getroutes.add(new Delete("/delete"));
		getroutes.add(new Post("/post"));
		getroutes.add(new Show("/show"));
		getroutes.add(new Hide("/hide"));
		getroutes.add(new Logout("/logout"));
		getroutes.add(new PostLatestGiveaway("/postlatestgiveaway"));
		getroutes.add(new View("/view"));
		getroutes.add(new Home("/"));
		getroutes.add(new Home("/home"));
		getroutes.add(new Giveaways("/giveways"));
		
		//HANDLE TEMPLATES
		for(final File file : alltemplates) {
			if(!file.isDirectory() && !ignorelist.contains(file)) {
					getroutes.add(new SimpleTemplates(file.getName().replaceAll(".html", ""), file));
			}
		}
		
		for(Route r : getroutes) {
			u.s.println("Registered Route " + r.toString());
			Spark.get(r);
		}
		
		for(Route r : postroutes) {
			u.s.println("Registered Route " + r.toString());
			Spark.post(r);
		}
		
	}
	
	private static void makeJDBCConnection() {
		 
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}
 
		try {
			connection = DriverManager.getConnection("jdbc:mysql://" + Config.getString("mysqlip")+":"+ Config.getString("mysqlport")+"/"+Config.getString("mysqldatabase"), Config.getString("mysqlusername"), Config.getString("mysqlpassword"));
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
 
	}
	
}
