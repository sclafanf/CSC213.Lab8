package com.example.reviews;

import com.opencsv.bean.CsvBindByName;

public class Review {

    @CsvBindByName
    private int reviewId;

    @CsvBindByName
    private String title;

    @CsvBindByName
    private String category;

    @CsvBindByName
    private String productId;

    @CsvBindByName
    private double price;

    public int getReviewId() { return reviewId; }
    public void setReviewId(int reviewId) { this.reviewId = reviewId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return "Review{id=" + reviewId + ", product='" + productId + "', price=" + price + ", title='" + title + "'}";
    }
}
