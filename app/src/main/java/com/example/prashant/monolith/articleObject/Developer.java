package com.example.prashant.monolith.articleObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Root;

@Root(strict=false)
public class Developer {

@SerializedName("url")
@Expose
private String url;
@SerializedName("name")
@Expose
private String name;
@SerializedName("type")
@Expose
private String type;

public String getUrl() {
return url;
}

public void setUrl(String url) {
this.url = url;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

}