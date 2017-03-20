package com.example.prashant.monolith.articleObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Root;

@Root(strict=false)
public class Topic {

@SerializedName("Result")
@Expose
private String result;
@SerializedName("Icon")
@Expose
private Icon_ icon;
@SerializedName("FirstURL")
@Expose
private String firstURL;
@SerializedName("Text")
@Expose
private String text;

public String getResult() {
return result;
}

public void setResult(String result) {
this.result = result;
}

public Icon_ getIcon() {
return icon;
}

public void setIcon(Icon_ icon) {
this.icon = icon;
}

public String getFirstURL() {
return firstURL;
}

public void setFirstURL(String firstURL) {
this.firstURL = firstURL;
}

public String getText() {
return text;
}

public void setText(String text) {
this.text = text;
}

}