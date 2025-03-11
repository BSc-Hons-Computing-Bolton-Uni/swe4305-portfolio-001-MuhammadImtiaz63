package Logbook.Week5;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        StockDemo demo = new StockDemo();
        demo.run();
    }
}

class Product {
    private int id;
    private String name;
    private int quantity;

    public Product(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void print() {
        System.out.println("Product ID: " + id + ", Name: " + name + ", Quantity: " + quantity);
    }
}

class StockList {
    private ArrayList<Product> products;

    public StockList() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(int id) {
        products.removeIf(product -> product.getId() == id);
    }

    public Product findProduct(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public void printProducts() {
        for (Product product : products) {
            product.print();
        }
    }
}

class StockDemo {
    private StockList stockList;

    public StockDemo() {
        stockList = new StockList();
    }

    public void testAddProducts() {
        Product p1 = new Product(1, "Laptop", 10);
        Product p2 = new Product(2, "Phone", 20);
        Product p3 = new Product(3, "Tablet", 15);

        stockList.addProduct(p1);
        stockList.addProduct(p2);
        stockList.addProduct(p3);

        System.out.println("Products added to stock:");
        stockList.printProducts();
    }

    public void testRemoveProduct() {
        System.out.println("\nRemoving Product with ID 2...");
        stockList.removeProduct(2);
        stockList.printProducts();
    }

    public void testFindProduct() {
        System.out.println("\nSearching for Product with ID 3...");
        Product foundProduct = stockList.findProduct(3);
        if (foundProduct != null) {
            foundProduct.print();
        } else {
            System.out.println("Product not found.");
        }
    }

    public void run() {
        testAddProducts();
        testRemoveProduct();
        testFindProduct();
    }
}