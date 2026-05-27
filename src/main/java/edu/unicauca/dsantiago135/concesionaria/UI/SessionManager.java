package edu.unicauca.dsantiago135.concesionaria.UI;

import edu.unicauca.dsantiago135.concesionaria.Model.clsEmployee;

public class SessionManager {

	private static clsEmployee currentUser;

    public static void setUser(clsEmployee user) {
        currentUser = user;
    }

    public static clsEmployee getUser() {
        return currentUser;
    }

    public static void clear() {
        currentUser = null;
    }
}
