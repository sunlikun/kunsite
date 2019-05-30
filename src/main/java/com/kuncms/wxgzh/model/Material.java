package com.kuncms.wxgzh.model;
/*获取素材列表参数实体类**/
public class Material {
    
    private String title;//图文消息的标题
    
    private String thumb_media_id;//图文消息的封面图片素材id（必须是永久mediaID）
    
    private String author;//作者
    
    private String digest;//图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空
    
    private String content;//图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS
    
    private String url;//图文页的URL，或者，当获取的列表是图片素材列表时，该字段是图片的URL
    
    private int show_cover_pic;//是否显示封面，0为false，即不显示，1为true，即显示
    
    
    private String total_count;
    
    private String item_count;
    
    private String media_id;
    
    private String name;
    
    private String update_time;
    
    public String getTotal_count() {
		return total_count;
	}
	public void setTotal_count(String total_count) {
		this.total_count = total_count;
	}
	public String getItem_count() {
		return item_count;
	}
	public void setItem_count(String item_count) {
		this.item_count = item_count;
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
    
 
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getThumb_media_id() {
        return thumb_media_id;
    }
    public void setThumb_media_id(String thumb_media_id) {
        this.thumb_media_id = thumb_media_id;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getDigest() {
        return digest;
    }
    public void setDigest(String digest) {
        this.digest = digest;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getShow_cover_pic() {
        return show_cover_pic;
    }
    public void setShow_cover_pic(int show_cover_pic) {
        this.show_cover_pic = show_cover_pic;
    }
    
}