package ru.kata.spring.boot_security.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleService roleService;
    private final UserService userService;

    public DataLoader(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (roleService.getRoles().isEmpty()) {
            Set<Role> roles = new HashSet<>();
            roles.add(new Role("ROLE_ADMIN"));
            roles.add(new Role("ROLE_USER"));
            roleService.addRole(roles);
        }

        // Создаём тестового админа, если пользователей ещё нет
        if (userService.getAllUsers().isEmpty()) {
            UserDto adminDto = new UserDto();
            adminDto.setUsername("admin@mail.ru");
            adminDto.setFirstName("Admin");
            adminDto.setLastName("Adminov");
            adminDto.setAge(30);
            adminDto.setPassword("admin");
            adminDto.setRoleId(roleService.getRoleByName("ROLE_ADMIN").getId());
            userService.addUser(adminDto);
        }
    }
}
