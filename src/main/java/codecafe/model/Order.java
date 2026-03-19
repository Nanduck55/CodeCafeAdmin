package codecafe.model;
import java.util.List;

public class Order {
    private int orderId;
    private String orderType;
    private String orderTime;
    private String status;
    private List<String> items;

    // Constructor matching the database columns exactly
    public Order(int orderId, String orderType, String orderTime, String status, List<String> orderDetails) {
        this.orderId = orderId;
        this.orderType = orderType;
        this.orderTime = orderTime;
        this.status = status;
        this.items = items;
    }

    // Getters (Required for your Admin TableView)
    public int getOrderId() { return orderId; }
    public String getOrderType() { return orderType; }
    public String getOrderTime() { return orderTime; }
    public String getStatus() { return status; }
    public List<String> getItems() { return items; }
}





