package com.example.prashant.monolith.articleObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Root;

@Root(strict=false)
public class Maintainer {

@SerializedName("github")
@Expose
private String github;

public String getGithub() {
return github;
}

public void setGithub(String github) {
this.github = github;
}

}