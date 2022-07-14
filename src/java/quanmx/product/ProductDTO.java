package quanmx.product;

import java.io.Serializable;

public class ProductDTO implements Serializable{

    private int sku;
    private String name;
    private String description;
    private double price;

    public ProductDTO() {
    }

    public ProductDTO(int sku, String name, String description, double price) {
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getSku() {
        return sku;
    }

    public void setSku(int sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
