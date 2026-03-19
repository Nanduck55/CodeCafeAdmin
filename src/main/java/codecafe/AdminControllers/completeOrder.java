package codecafe.AdminControllers; // Make sure your package name matches

import codecafe.util.DatabaseHelper;

public class completeOrder {
    public static void processCompletion(int orderId) {
        System.out.println("Processing Order #" + orderId + " through CompleteOrderController!");
        DatabaseHelper.completeOrder(orderId);

        // "Print Receipt"
    }
}