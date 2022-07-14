package quanmx.cart;

import quanmx.product.ProductDTO;

public class CartProduct {

    private ProductDTO product;
    private int quantity;

    public CartProduct() {
    }

    public CartProduct(ProductDTO product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public ProductDTO getProduct() {
        return product;
    }

}
