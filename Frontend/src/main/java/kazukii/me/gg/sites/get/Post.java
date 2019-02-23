package kazukii.me.gg.sites.get;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.security.auth.login.LoginException;

import kazukii.me.gg.configs.Config;
import kazukii.me.gg.main.Site;
import kazukii.me.gg.sites.Permission;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Guild;
import spark.Request;
import spark.Response;
import spark.Route;

public class Post extends Route{

	public Post(String path) {
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
		
		Thread t = new Thread(sendEmbed(request.queryParams("id")));
		t.start();
		
		response.redirect("/?a=Embed is in sending queue");
		return "done";
	}
	
	public Runnable sendEmbed(final String id) {
		return new Runnable() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				JDABuilder builder = new JDABuilder(AccountType.BOT);
				JDA jda = null;
				
				builder.setToken(Config.getString("discordtoken"));
				builder.setStatus(OnlineStatus.ONLINE);
				
				try {
					jda = builder.buildBlocking();
				} catch (LoginException | InterruptedException e2) {
					e2.printStackTrace();
				}
				
				EmbedBuilder e = new EmbedBuilder();
				
				
				String sql = "SELECT * FROM `giveways` WHERE `id` = " + id;
				try {
					
					PreparedStatement stmt= Site.connection.prepareStatement(sql);  
					ResultSet rs=stmt.executeQuery();  
					while(rs.next()){
						e.setColor(Color.red);
						e.setImage(rs.getString("Image"));
						e.setTitle(rs.getString("Title"));
						e.setDescription(rs.getString("Description") + "\n\nhttps://gg.kazukii.me/view?id="+rs.getString("id") + " @everyone");
						
					}
					
					
				}catch (Exception e2) {
					e2.printStackTrace();
				}
				
				
				for(Guild g : jda.getGuilds()) {
					g.getTextChannelById("517243624782692362").sendMessage(e.build()).queue();
				}
			}
		};
	}

}
