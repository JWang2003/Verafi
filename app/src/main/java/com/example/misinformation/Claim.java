package com.example.misinformation;

public class Claim {

    public String title;
    public String claimant;
    public String claimDate;
    public String source;
    public String reviewDate;
    public String claimRating;
    public String publisherName;
    public String publisherSite;
    public String url;

    public Claim(String title, String claimant, String claimDate, String source, String reviewDate, String claimRating, String publisherName,  String publisherSite, String url) {
        this.title = title;
        this.claimant = claimant;
        this.claimDate = claimDate;
        this.source = source;
        this.reviewDate = reviewDate;
        this.claimRating = claimRating;
        this.publisherName = publisherName;
        this.publisherSite = publisherSite;
        this.url = url;
    }

    @Override
    public String toString() {
        return "Claim{" +
                "title='" + title + '\'' +
                ", claimant='" + claimant + '\'' +
                ", claimDate='" + claimDate + '\'' +
                ", source='" + source + '\'' +
                ", reviewDate='" + reviewDate + '\'' +
                ", claimRating='" + claimRating + '\'' +
                ", publisherName='" + publisherName + '\'' +
                ", publisherSite='" + publisherSite + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
