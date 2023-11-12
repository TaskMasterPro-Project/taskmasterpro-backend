package com.taskmaster.server.utils;

import com.taskmaster.server.auth.RoleRepository;
import com.taskmaster.server.auth.UserRepository;
import com.taskmaster.server.auth.entity.RoleEntity;
import com.taskmaster.server.auth.entity.UserEntity;
import com.taskmaster.server.auth.RoleEnum;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Set;

@Component
@ConditionalOnProperty("app.data-loader")
public class DataLoader implements ApplicationRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public DataLoader(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void run(ApplicationArguments args) {
        if (roleRepository.count() == 0) {
            //set user roles
            RoleEntity adminRole = roleRepository.save(RoleEntity.builder().roleName(RoleEnum.ADMIN).build());
            RoleEntity userRole = roleRepository.save(RoleEntity.builder().roleName(RoleEnum.USER).build());

            UserEntity deletedUser = userRepository.save(
                    UserEntity
                            .builder()
                            .username("deleted")
                            .password("$2a$12$w.GNfFrtuRMFSxWq0TZsgO2M/O3jTwZ8cvdL3X/EW0XQKNitCqD6K")
                            .enabled(false)
                            .roles(Set.of(userRole))
                            .firstName(null)
                            .lastName(null)
                            .email("deleted@deleted.com")
                            .build()
            );

            UserEntity user = userRepository.save(
                    UserEntity
                            .builder()
                            .username("test1")
                            .password("$2a$12$w.GNfFrtuRMFSxWq0TZsgO2M/O3jTwZ8cvdL3X/EW0XQKNitCqD6K")
                            .enabled(true)
                            .roles(Set.of(userRole))
                            .firstName("Test")
                            .lastName("Test")
                            .email("test@test.com")
                            .build()
            );

            userRepository.save(
                    UserEntity
                            .builder()
                            .username("admin")
                            .password("$2a$12$w.GNfFrtuRMFSxWq0TZsgO2M/O3jTwZ8cvdL3X/EW0XQKNitCqD6K")
                            .enabled(true)
                            .roles(Set.of(adminRole))
                            .firstName("Admin")
                            .lastName("Adminov")
                            .email("admin@admin.com")
                            .build()
            );
        }
    }
}
