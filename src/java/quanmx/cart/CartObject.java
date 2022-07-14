package quanmx.cart;

import java.util.HashMap;
import java.util.Map;

public class CartObject {

    // sku and quantity
    Map<Integer, Integer> items;
    

    public Map<Integer, Integer> getItems() {
        return items;
    }

    public void addToCart(int sku) {
        //1. check whether or not the items has been existed
        if (items == null) {
            items = new HashMap<>();
        }
        int quantity = 1;
        //2. check the sku is in the items or not
        if (items.get(sku) != null) {
            quantity = this.items.get(sku) + 1;
        }
        //3. put product to items
        items.put(sku, quantity);
    }

    public void removeFromCart(int sku) {
        //1. check whether or not the items has been existed
        if (this.items == null) {
            return;
        }
        //2. remove from items
        if (this.items.containsKey(sku)) {
            this.items.remove(sku);
            if (items.isEmpty()) {
                items = null;
            }
        }
    }

}
