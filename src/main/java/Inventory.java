import java.util.Map;
import java.util.HashMap;

class InsufficientInventory extends Exception {
    public InsufficientInventory(int currentInventory, int requestedInventory) {
        super("There is not enough inventory for this product. " +
                "Current Inventory: " + currentInventory + " Needed: " + requestedInventory);
    }
}

public class Inventory {
    private Map<String, Product> products = new HashMap<>();



    void addProduct(String productId, double price, int quantity) {
        products.put(productId, new Product(productId, price, quantity));
    }

    void removeProduct(String productId, int quantity) throws InsufficientInventory {

        if (products.get(productId).getQuantity() < quantity) {
            throw new InsufficientInventory(products.get(productId).getQuantity(), quantity);
        } else if (products.get(productId).getQuantity() == quantity) {
            products.remove(productId);
        } else {
            products.get(productId).removeStock(quantity);
        }
    }

    boolean inStock(String productId) {
        return products.containsKey(productId) && products.get(productId).getQuantity() > 0;
    }

    double totalInventoryValue() {
        double value = 0.0;
        for (String productId : products.keySet()) {
            value += products.get(productId).getPrice() * products.get(productId).getQuantity();
        }

        return value;
    }

    String getAllProductNames() {
        StringBuilder productNames = new StringBuilder();
        for (String name : products.keySet()) {
            productNames.append(name + "\n");
        }
        return productNames.toString();
    }

    public static void main(String[] args) throws InsufficientInventory {
        Inventory inventory = new Inventory();
        inventory.addProduct("milk", 3.5, 1);
        inventory.addProduct("banana", .6, 1);

        System.out.println(inventory.inStock("milk"));
        inventory.addProduct("chicken", 10.0, 5);
        System.out.println(inventory.totalInventoryValue());

        System.out.println(inventory.getAllProductNames());
    }
}
