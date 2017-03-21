package com.example.prashant.monolith.articleObject;

import org.simpleframework.xml.Root;

@Root(strict=false)
public class Guid {
    private String content;

    private String isPermaLink;

    public String getContent () {
        return content;
    }

    public void setContent (String content) {
        this.content = content;
    }

    public String getIsPermaLink () {
        return isPermaLink;
    }

    public void setIsPermaLink (String isPermaLink) {
        this.isPermaLink = isPermaLink;
    }

    @Override
    public String toString() {
        return "ClassPojo [content = "+content+", isPermaLink = "+isPermaLink+"]";
    }
}