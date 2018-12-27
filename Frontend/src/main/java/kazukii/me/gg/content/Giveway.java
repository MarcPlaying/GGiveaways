package kazukii.me.gg.content;

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

}