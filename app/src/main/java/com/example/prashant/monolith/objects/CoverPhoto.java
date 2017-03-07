package com.example.prashant.monolith.objects;
 
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Root;

@Root(strict=false)
public class CoverPhoto {
 
@SerializedName("id")
@Expose
private String id;
@SerializedName("created_at")
@Expose
private String createdAt;
@SerializedName("updated_at")
@Expose
private String updatedAt;
@SerializedName("width")
@Expose
private Integer width;
@SerializedName("height")
@Expose
private Integer height;
@SerializedName("color")
@Expose
private String color;
@SerializedName("likes")
@Expose
private Integer likes;
@SerializedName("liked_by_user")
@Expose
private Boolean likedByUser;
@SerializedName("user")

public String getId() {
return id;
}
 
public void setId(String id) {
this.id = id;
}
 
public String getCreatedAt() {
return createdAt;
}
 
public void setCreatedAt(String createdAt) {
this.createdAt = createdAt;
}
 
public String getUpdatedAt() {
return updatedAt;
}
 
public void setUpdatedAt(String updatedAt) {
this.updatedAt = updatedAt;
}
 
public Integer getWidth() {
return width;
}
 
public void setWidth(Integer width) {
this.width = width;
}
 
public Integer getHeight() {
return height;
}
 
public void setHeight(Integer height) {
this.height = height;
}
 
public String getColor() {
return color;
}
 
public void setColor(String color) {
this.color = color;
}
 
public Integer getLikes() {
return likes;
}
 
public void setLikes(Integer likes) {
this.likes = likes;
}
 
public Boolean getLikedByUser() {
return likedByUser;
}
 
public void setLikedByUser(Boolean likedByUser) {
this.likedByUser = likedByUser;
}
    
}