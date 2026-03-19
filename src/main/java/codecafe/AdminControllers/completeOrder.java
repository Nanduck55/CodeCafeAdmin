package codecafe.AdminControllers; // Make sure your package name matches

import codecafe.util.DatabaseComs;

public class completeOrder {
    public static void processCompletion(int orderId) {
        System.out.println("Processing Order #" + orderId + " through CompleteOrderController!");
        DatabaseComs.completeOrder(orderId);

        // "Print Receipt"
    }
}