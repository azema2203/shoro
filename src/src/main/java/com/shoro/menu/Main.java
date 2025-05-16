package com.shoro.menu;

import com.shoro.menu.auth.Authentication;
import com.shoro.menu.auth.User;
import com.shoro.menu.db.DBConnection;
import com.shoro.menu.menu.DeliveryMenu;
import com.shoro.menu.menu.ProviderMenu;
import com.shoro.menu.menu.SellerMenu;

public class Main {
    public static void main(String[] args) {
        // Attempt authentication
        User user = Authentication.authenticate();

        if (user == null) {
            System.err.println("Authentication failed. Exiting program.");
            System.exit(1);
        }

        // Proceed with menu based on user type
        switch (user.getUserType()) {
            case SELLER:
                SellerMenu sellerMenu = new SellerMenu(user);
                sellerMenu.showMenu();
                break;
            case DELIVERY:
                DeliveryMenu deliveryMenu = new DeliveryMenu(user);
                deliveryMenu.showMenu();
                break;
            case PROVIDER:
                ProviderMenu providerMenu = new ProviderMenu(user);
                providerMenu.showMenu();
                break;
            default:
                System.err.println("Unknown user type");
                break;
        }

        // Close database connection
        DBConnection.closeConnection();
        System.out.println("Программа завершена, мы будем рады вашему возвращению!");
    }
}