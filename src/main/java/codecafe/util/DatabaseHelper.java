package codecafe.util;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseHelper {

    private static final String URL = "jdbc:sqlite:codecafe.db";
    public static Connection connect() {
        Connection Hello = null;
        try {
            Hello = DriverManager.getConnection(URL);
            System.out.println("[Yay! Sqlite Connection has been established]");
        } catch (SQLException e) {
            System.out.println("[Connection Failed: " + e.getMessage() + " ]");
        }
        return Hello;
    }

    public static void createNewTable() {
        String sql = "Create Table if not Exists MenuItems(\n"
                + "name TEXT NOT NULL, \n"
                + "category TEXT NOT NULL, \n"
                + "description TEXT, \n"
                + "price REAL NOT NULL, \n"
                + "imagePath TEXT, \n"
                + "is_available BOOLEAN DEFAULT 1\n"
                + ");";

        try (Connection Hello = connect(); Statement smt = Hello.createStatement()){
            smt.execute(sql);
            System.out.println("Table 'MenuItems' is ready to go.");
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    public static void addItem(int id,String name,String category, String description, double price, String imagePath, boolean isAvailable ){
        String sql =
                "INSERT INTO MenuItems(name,category,description,price,imagePath)VALUES(?,?,?,?,?)";

        try (java.sql.Connection Hello = connect();
            java.sql.PreparedStatement jalla = Hello.prepareStatement(sql)){

            jalla.setString(1, name);
            jalla.setString(2, category);
            jalla.setString(3, description);
            jalla.setDouble(4, price);
            jalla.setString(5, imagePath);

            jalla.executeUpdate();
            System.out.println("[Item Successfully added to database]");
        } catch (java.sql.SQLException e){
            System.out.println(e.getMessage());
        }
    }

}
