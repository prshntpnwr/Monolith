package com.example.prashant.monolith.GalleryObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(strict=false)
public class Results {

    @SerializedName("results")
    @Expose
    private ArrayList<SingleResult> results = null;

    public ArrayList<SingleResult> getResults() {
        return results;
    }

    public void setResults(ArrayList<SingleResult> results) {
        this.results = results;
    }

    @Root(strict=false)
    public static class SingleResult {

        @SerializedName("cover_photo")
        @Expose
        private CoverPhoto coverPhoto;

        public CoverPhoto getCoverPhoto() {
            return coverPhoto;
        }

        public void setCoverPhoto(CoverPhoto coverPhoto) {
            this.coverPhoto = coverPhoto;
        }
    }

    @Root(strict=false)
    public static class CoverPhoto {

        @SerializedName("urls")
        @Expose
        private Urls urls = null;
        @SerializedName("categories")

        public Urls getUrls() {
            return urls;
        }

        public void setUrls(Urls urls) {
            this.urls = urls;
        }

    }

    @Root(strict=false)
    public static class Urls {

        @SerializedName("raw")
        @Expose
        private String raw = null;
        @SerializedName("full")
        @Expose
        private String full = null;
        @SerializedName("regular")
        @Expose
        private String regular = null;
        @SerializedName("small")
        @Expose
        private String small = null;
        @SerializedName("thumb")
        @Expose
        private String thumb;

        public String getRaw() {
            return raw;
        }

        public void setRaw(String raw) {
            this.raw = raw;
        }

        public String getFull() {
            return full;
        }

        public void setFull(String full) {
            this.full = full;
        }

        public String getRegular() {
            return regular;
        }

        public void setRegular(String regular) {
            this.regular = regular;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

    }
}