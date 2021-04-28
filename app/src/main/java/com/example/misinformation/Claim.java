package com.example.misinformation;

public class Claim {
    private String title;
    private String claimant;
    private String claimDate;
    private String source;
    private String reviewDate;
    private String claimRating;
    private String publisherName;
    private String publisherSite;
    private String url;

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

    public String getTitle() {
        return title;
    }

    public String getClaimant() {
        return claimant;
    }

    public String getClaimDate() {
        return claimDate;
    }

    public String getSource() {
        return source;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public String getClaimRating() {
        return claimRating;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public String getPublisherSite() {
        return publisherSite;
    }

    public String getUrl() { return url; }
}
