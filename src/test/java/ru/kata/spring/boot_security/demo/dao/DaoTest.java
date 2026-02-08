package ru.kata.spring.boot_security.demo.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class DaoTest {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Doljen soxranit novovo polzovatelya")
    void addUser() {
        UserDto userDto = new UserDto();
        userDto.setUsername("test@mail.ru");
        userDto.setFirstName("Test");
        userDto.setLastName("Testov");
        userDto.setAge(30);
        userDto.setPassword("password");
        userDto.setRoleId(roleService.getRoleByName("ROLE_USER").getId());

        userService.addUser(userDto);

        User userFromDb = userService.getUserByUsername("test@mail.ru");
        assertThat(userFromDb).isNotNull();
        assertThat(userFromDb.getFirstName()).isEqualTo("Test");
        assertThat(userFromDb.getId()).isNotNull();
    }

    @Test
    @DisplayName("doljen vernut vsex polzovateley")
    void getAllUsers() {
        UserDto userDto1 = new UserDto();
        userDto1.setUsername("test1@mail.ru");
        userDto1.setFirstName("Test");
        userDto1.setLastName("Testov");
        userDto1.setAge(30);
        userDto1.setPassword("password");
        userDto1.setRoleId(roleService.getRoleByName("ROLE_USER").getId());

        UserDto userDto2 = new UserDto();
        userDto2.setUsername("test2@mail.ru");
        userDto2.setFirstName("Test2");
        userDto2.setLastName("Testov2");
        userDto2.setAge(20);
        userDto2.setPassword("pass");
        userDto2.setRoleId(roleService.getRoleByName("ROLE_USER").getId());

        userService.addUser(userDto1);
        userService.addUser(userDto2);
        entityManager.flush();

        List<User> userList = userService.getAllUsers();
        assertThat(userList).extracting(User::getFirstName)
                .contains("Test", "Test2");
    }
}
