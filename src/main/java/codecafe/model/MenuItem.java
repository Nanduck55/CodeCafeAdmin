package codecafe.model;

public class MenuItem {
    private int id;
    private String name;
    private String category;
    private String description;
    private double price;
    private String imagePath;
    private boolean isAvailable;

    public MenuItem(int id,String name,String category, String description, double price, String imagePath, boolean isAvailable ){
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
        this.isAvailable = isAvailable;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
