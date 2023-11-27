package it.unipi.dsmt.jakartaee.lab04.entity;

public class Beer {

    private String id;
    private String name;
    private String asin;
    private String link;
    private String image;
    private Double rating;
    private Double ratingsTotal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getRatingsTotal() {
        return ratingsTotal;
    }

    public void setRatingsTotal(Double ratingsTotal) {
        this.ratingsTotal = ratingsTotal;
    }
}
