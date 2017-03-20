package com.example.prashant.monolith.articleObject;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Root;

@Root(strict=false)
public class RelatedTopic {

    @SerializedName("Result")
    @Expose
    private String result;
    @SerializedName("Icon")
    @Expose
    private Icon icon;
    @SerializedName("FirstURL")
    @Expose
    private String firstURL;
    @SerializedName("Text")
    @Expose
    private String text;
    @SerializedName("Topics")
    @Expose
    private List<Topic> topics = null;
    @SerializedName("Name")
    @Expose
    private String name;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
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

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}