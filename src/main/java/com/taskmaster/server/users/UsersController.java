package com.taskmaster.server.users;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;
    private final UsersRepository usersRepository;

    @GetMapping("/users")
    public List<UsersModel> getUsers() {
        return usersService.getAllUsers();
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody UsersDto dto) {

        return new ResponseEntity<>(usersService.createUser(dto), HttpStatus.CREATED);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        Optional<UsersModel> user = usersRepository.findById(userId);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{userId}/edit")
    public ResponseEntity<?> editUser(
            @PathVariable Long userId,
            @RequestBody UsersDto updatedDto) {

        usersService.editUser(userId, updatedDto);
        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long projectId) {
        usersService.deleteUserById(projectId);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

}
