package codecafe.model;

public class MenuItem {
    private int itemId;
    private String itemName;
    private int categoryId;
    private String description;
    private double price;
    private String imagePath;
    private boolean isAvailable;


    public MenuItem(int itemId, String itemName, int categoryId, String description, double price, String imagePath, boolean isAvailable) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.categoryId = categoryId;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
        this.isAvailable = isAvailable;
    }

    public int getItemId() { return itemId; }
    public String getItemName() { return itemName; }
    public int getCategoryId() { return categoryId; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getImagePath() { return imagePath; }
    public boolean isAvailable() { return isAvailable; }
}
