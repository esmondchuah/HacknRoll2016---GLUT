package com.example.shiping.glut;

public class Offers {
    private String source;
    private String deliverTo;
    private String offerTill;
    private String eta;
    private String budget;
    private String food;
    private String price;
    private String username;

    public Offers() {}


    public Offers(String username, String source, String deliverTo, String offerTill, String eta, String budget, String food, String price) {

        this.source = source;
        this.deliverTo = deliverTo;
        this.offerTill = offerTill;
        this.eta = eta;
        this.budget = budget;
        this.food = food;
        this.price = price;
        this.username = username;

    }

    public String getUsername() {
        return username;
    }

    public String getPrice() {
        return price;
    }

    public String getSource() {
        return source;
    }

    public String getDeliverTo() {
        return deliverTo;
    }

    public String getOfferTill() {
        return offerTill;
    }

    public String getEta() {
        return eta;
    }

    public String getBudget() {
        return budget;
    }

    public String getFood() {
        return food;
    }

}
