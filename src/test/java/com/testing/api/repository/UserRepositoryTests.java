package com.testing.api.repository;

import com.testing.api.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTests {

        @Autowired
        private UserRepository userRepository;


        @Test
        public void UserRepository_SaveAll_ReturnSavedUser() {
                // Arrange
                User user = User.builder()
                        .name("arda")
                        .type("customer").build();
                // Act
                User savedUser = userRepository.save(user);

                //Assert
                Assertions.assertThat(savedUser).isNotNull();
                Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
        }

        @Test
        public void UserRepository_GetAll_ReturnAllUsers() {

                User user = User.builder()
                        .name("arda")
                        .type("customer").build();

                User user2 = User.builder()
                        .name("ahmet")
                        .type("customer").build();

                userRepository.save(user);
                userRepository.save(user2);

                List<User> userList = userRepository.findAll();

                Assertions.assertThat(userList).isNotNull();
                Assertions.assertThat(userList.size()).isEqualTo(2);

        }


        @Test
        public void UserRepository_FindById_ReturnUser() {

                User user = User.builder()
                        .name("arda")
                        .type("customer").build();

                userRepository.save(user);

                User userList = userRepository.findById(user.getId()).get();

                Assertions.assertThat(userList).isNotNull();
        }

}
