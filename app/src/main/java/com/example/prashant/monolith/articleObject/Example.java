package com.example.prashant.monolith.articleObject;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Root;

@Root(strict=false)
public class Example {

@SerializedName("DefinitionSource")
@Expose
private String definitionSource;
@SerializedName("Heading")
@Expose
private String heading;
@SerializedName("ImageWidth")
@Expose
private Integer imageWidth;
@SerializedName("RelatedTopics")
@Expose
private List<RelatedTopic> relatedTopics = null;
@SerializedName("Entity")
@Expose
private String entity;
@SerializedName("meta")
@Expose
private Meta meta;
@SerializedName("Type")
@Expose
private String type;
@SerializedName("Redirect")
@Expose
private String redirect;
@SerializedName("DefinitionURL")
@Expose
private String definitionURL;
@SerializedName("AbstractURL")
@Expose
private String abstractURL;
@SerializedName("Definition")
@Expose
private String definition;
@SerializedName("AbstractSource")
@Expose
private String abstractSource;
@SerializedName("Infobox")
@Expose
private String infobox;
@SerializedName("Image")
@Expose
private String image;
@SerializedName("ImageIsLogo")
@Expose
private Integer imageIsLogo;
@SerializedName("Abstract")
@Expose
private String _abstract;
@SerializedName("AbstractText")
@Expose
private String abstractText;
@SerializedName("AnswerType")
@Expose
private String answerType;
@SerializedName("ImageHeight")
@Expose
private Integer imageHeight;
@SerializedName("Answer")
@Expose
private String answer;
@SerializedName("Results")
@Expose
private List<Object> results = null;

public String getDefinitionSource() {
return definitionSource;
}

public void setDefinitionSource(String definitionSource) {
this.definitionSource = definitionSource;
}

public String getHeading() {
return heading;
}

public void setHeading(String heading) {
this.heading = heading;
}

public Integer getImageWidth() {
return imageWidth;
}

public void setImageWidth(Integer imageWidth) {
this.imageWidth = imageWidth;
}

public List<RelatedTopic> getRelatedTopics() {
return relatedTopics;
}

public void setRelatedTopics(List<RelatedTopic> relatedTopics) {
this.relatedTopics = relatedTopics;
}

public String getEntity() {
return entity;
}

public void setEntity(String entity) {
this.entity = entity;
}

public Meta getMeta() {
return meta;
}

public void setMeta(Meta meta) {
this.meta = meta;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getRedirect() {
return redirect;
}

public void setRedirect(String redirect) {
this.redirect = redirect;
}

public String getDefinitionURL() {
return definitionURL;
}

public void setDefinitionURL(String definitionURL) {
this.definitionURL = definitionURL;
}

public String getAbstractURL() {
return abstractURL;
}

public void setAbstractURL(String abstractURL) {
this.abstractURL = abstractURL;
}

public String getDefinition() {
return definition;
}

public void setDefinition(String definition) {
this.definition = definition;
}

public String getAbstractSource() {
return abstractSource;
}

public void setAbstractSource(String abstractSource) {
this.abstractSource = abstractSource;
}

public String getInfobox() {
return infobox;
}

public void setInfobox(String infobox) {
this.infobox = infobox;
}

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

public Integer getImageIsLogo() {
return imageIsLogo;
}

public void setImageIsLogo(Integer imageIsLogo) {
this.imageIsLogo = imageIsLogo;
}

public String getAbstract() {
return _abstract;
}

public void setAbstract(String _abstract) {
this._abstract = _abstract;
}

public String getAbstractText() {
return abstractText;
}

public void setAbstractText(String abstractText) {
this.abstractText = abstractText;
}

public String getAnswerType() {
return answerType;
}

public void setAnswerType(String answerType) {
this.answerType = answerType;
}

public Integer getImageHeight() {
return imageHeight;
}

public void setImageHeight(Integer imageHeight) {
this.imageHeight = imageHeight;
}

public String getAnswer() {
return answer;
}

public void setAnswer(String answer) {
this.answer = answer;
}

public List<Object> getResults() {
return results;
}

public void setResults(List<Object> results) {
this.results = results;
}

}