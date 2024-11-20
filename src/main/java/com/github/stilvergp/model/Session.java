package com.github.stilvergp.model;

import com.github.stilvergp.model.entity.Client;

public class Session {
    private static Session _instance;
    private Client loggedInClient;

    public Session() {
        loggedInClient = null;
    }

    public static Session getInstance() {
        if (_instance == null) {
            _instance = new Session();
        }
        return _instance;
    }

    /**
     * Gets the currently logged-in client.
     *
     * @return the currently logged-in client, or null if no client is logged in.
     */
    public Client getLoggedInClient() {
        return loggedInClient;
    }

    /**
     * Logs in the client.
     *
     * @param client the client to be logged in.
     */
    public void login(Client client) {
        loggedInClient = client;
    }

    /**
     * Logs out the currently logged-in client.
     */
    public void logout() {
        loggedInClient = null;
    }

    /**
     * Checks if a client is currently logged in.
     *
     * @return true if a client is logged in, false otherwise.
     */
    public boolean isLoggedIn() {
        return loggedInClient != null;
    }
}
