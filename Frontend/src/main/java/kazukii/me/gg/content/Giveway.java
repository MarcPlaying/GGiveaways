package kazukii.me.gg.content;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import kazukii.me.gg.main.Site;
import kazukii.me.gg.sites.Permission;

public class Giveway {

    private String Image;
    private String Title;
    private String Description;
    private String id;
    
    private String Giveway;
    
    private String Timestamp;
    
    public String getTimestamp() {
    	return Timestamp;
    }
    
    public String getID() {
    	return id;
    }
    
    public void setID(String id) {
    	this.id = id;
    }
    
    
    public void setTimestamp(String time) {
    	Timestamp = time;
    }
    
    public String getImage() {
        return Image;
    }

    public void setImage(String imgurl) {
    	Image = imgurl;
    }
    
    public String getGiveway() {
    	return Giveway;
    }
    
    public void setGiveway(String author) {
    	Giveway = author;
    }
    

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
    	Title = title;
    }
    
    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
    	Description = description;
    }
    
    public static void addView(int id) {
    	
    	String sql = "SELECT * FROM `giveways` WHERE `id` = ?";
    	int views = 0;
		try {
			PreparedStatement stmt= Site.connection.prepareStatement(sql);  
			stmt.setInt(1, id);
			ResultSet rs=stmt.executeQuery();  
			while(rs.next()){
				views = rs.getInt("views");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
    	
		views++;
		
    	String query = "UPDATE giveways SET views = " + views + " WHERE id = ?";
	     PreparedStatement preparedStmt;
		try {
			preparedStmt = Site.connection.prepareStatement(query);
			preparedStmt.setInt(1, id);
		    preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

}