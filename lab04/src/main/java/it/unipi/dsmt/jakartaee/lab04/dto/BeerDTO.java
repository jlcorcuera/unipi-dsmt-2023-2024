package it.unipi.dsmt.jakartaee.lab04.dto;

public class BeerDTO {
    private String id;
    private String name;
    private String link;
    private String imageUrl;
    private Double rating;
    private Integer quantity;
    public BeerDTO(String id, String name, Integer quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }
    public BeerDTO(String name, String imageUrl, Double rating) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.rating = rating;
    }
    public BeerDTO(String id, String name, String link, String imageUrl, Double rating) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.imageUrl = imageUrl;
        this.rating = rating;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
