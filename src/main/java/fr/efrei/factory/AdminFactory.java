package fr.efrei.factory;

import fr.efrei.domain.Customer;
import fr.efrei.domain.Role;

/**
 * Small factory for creating admin customers. Delegates to CustomerFactory.
 */
public final class AdminFactory {
    private AdminFactory() {}

    public static Customer create(String id, String name, String contactNumber, String password) {
        // reuse CustomerFactory and set Role.ADMIN
        return CustomerFactory.create(id, name, contactNumber, password, Role.ADMIN);
    }
}
