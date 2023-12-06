package ru.maconconsulting.library.util;

import ru.maconconsulting.library.models.MaconUser;
import ru.maconconsulting.library.models.Role;

public class MaconUsersTestData {

    public static final MaconUser USER =
            new MaconUser("User", "user", "user@mail.com", "user", Role.ROLE_USER);

    public static final MaconUser MANAGER =
            new MaconUser("Manager", "manager", "manager@mail.com", "manager", Role.ROLE_MANAGER);

    public static final MaconUser ADMIN =
            new MaconUser("Admin", "admin", "admin@mail.com", "admin", Role.ROLE_ADMIN);

}