package com.example.prashant.monolith.articleObject;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Root;

@Root(strict=false)
public class Meta {

@SerializedName("maintainer")
@Expose
private Maintainer maintainer;
@SerializedName("perl_module")
@Expose
private String perlModule;
@SerializedName("status")
@Expose
private String status;
@SerializedName("production_state")
@Expose
private String productionState;
@SerializedName("dev_date")
@Expose
private Object devDate;
@SerializedName("js_callback_name")
@Expose
private String jsCallbackName;
@SerializedName("signal_from")
@Expose
private String signalFrom;
@SerializedName("live_date")
@Expose
private Object liveDate;
@SerializedName("src_id")
@Expose
private Integer srcId;
@SerializedName("src_options")
@Expose
private SrcOptions srcOptions;
@SerializedName("repo")
@Expose
private String repo;
@SerializedName("developer")
@Expose
private List<Developer> developer = null;
@SerializedName("tab")
@Expose
private String tab;
@SerializedName("producer")
@Expose
private Object producer;
@SerializedName("unsafe")
@Expose
private Integer unsafe;
@SerializedName("id")
@Expose
private String id;
@SerializedName("dev_milestone")
@Expose
private String devMilestone;
@SerializedName("topic")
@Expose
private List<String> topic = null;
@SerializedName("name")
@Expose
private String name;
@SerializedName("attribution")
@Expose
private Object attribution;
@SerializedName("created_date")
@Expose
private Object createdDate;
@SerializedName("example_query")
@Expose
private String exampleQuery;
@SerializedName("description")
@Expose
private String description;
@SerializedName("is_stackexchange")
@Expose
private Object isStackexchange;
@SerializedName("designer")
@Expose
private Object designer;
@SerializedName("src_domain")
@Expose
private String srcDomain;
@SerializedName("src_name")
@Expose
private String srcName;
@SerializedName("blockgroup")
@Expose
private Object blockgroup;
@SerializedName("src_url")
@Expose
private Object srcUrl;

public Maintainer getMaintainer() {
return maintainer;
}

public void setMaintainer(Maintainer maintainer) {
this.maintainer = maintainer;
}

public String getPerlModule() {
return perlModule;
}

public void setPerlModule(String perlModule) {
this.perlModule = perlModule;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getProductionState() {
return productionState;
}

public void setProductionState(String productionState) {
this.productionState = productionState;
}

public Object getDevDate() {
return devDate;
}

public void setDevDate(Object devDate) {
this.devDate = devDate;
}

public String getJsCallbackName() {
return jsCallbackName;
}

public void setJsCallbackName(String jsCallbackName) {
this.jsCallbackName = jsCallbackName;
}

public String getSignalFrom() {
return signalFrom;
}

public void setSignalFrom(String signalFrom) {
this.signalFrom = signalFrom;
}

public Object getLiveDate() {
return liveDate;
}

public void setLiveDate(Object liveDate) {
this.liveDate = liveDate;
}

public Integer getSrcId() {
return srcId;
}

public void setSrcId(Integer srcId) {
this.srcId = srcId;
}

public SrcOptions getSrcOptions() {
return srcOptions;
}

public void setSrcOptions(SrcOptions srcOptions) {
this.srcOptions = srcOptions;
}

public String getRepo() {
return repo;
}

public void setRepo(String repo) {
this.repo = repo;
}

public List<Developer> getDeveloper() {
return developer;
}

public void setDeveloper(List<Developer> developer) {
this.developer = developer;
}

public String getTab() {
return tab;
}

public void setTab(String tab) {
this.tab = tab;
}

public Object getProducer() {
return producer;
}

public void setProducer(Object producer) {
this.producer = producer;
}

public Integer getUnsafe() {
return unsafe;
}

public void setUnsafe(Integer unsafe) {
this.unsafe = unsafe;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getDevMilestone() {
return devMilestone;
}

public void setDevMilestone(String devMilestone) {
this.devMilestone = devMilestone;
}

public List<String> getTopic() {
return topic;
}

public void setTopic(List<String> topic) {
this.topic = topic;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public Object getAttribution() {
return attribution;
}

public void setAttribution(Object attribution) {
this.attribution = attribution;
}

public Object getCreatedDate() {
return createdDate;
}

public void setCreatedDate(Object createdDate) {
this.createdDate = createdDate;
}

public String getExampleQuery() {
return exampleQuery;
}

public void setExampleQuery(String exampleQuery) {
this.exampleQuery = exampleQuery;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public Object getIsStackexchange() {
return isStackexchange;
}

public void setIsStackexchange(Object isStackexchange) {
this.isStackexchange = isStackexchange;
}

public Object getDesigner() {
return designer;
}

public void setDesigner(Object designer) {
this.designer = designer;
}

public String getSrcDomain() {
return srcDomain;
}

public void setSrcDomain(String srcDomain) {
this.srcDomain = srcDomain;
}

public String getSrcName() {
return srcName;
}

public void setSrcName(String srcName) {
this.srcName = srcName;
}

public Object getBlockgroup() {
return blockgroup;
}

public void setBlockgroup(Object blockgroup) {
this.blockgroup = blockgroup;
}

public Object getSrcUrl() {
return srcUrl;
}

public void setSrcUrl(Object srcUrl) {
this.srcUrl = srcUrl;
}

}