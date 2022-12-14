package ru.clevertec.ecl.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.data.UserTestData;
import ru.clevertec.ecl.dto.UserDTO;
import ru.clevertec.ecl.entity.User;
import ru.clevertec.ecl.exception.EntityNotFoundException;
import ru.clevertec.ecl.mapper.UserMapper;
import ru.clevertec.ecl.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @InjectMocks
  private UserServiceImpl userService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserMapper userMapper;
  private User user;
  private UserDTO userDTO;
  private Pageable pageable;

  @BeforeEach
  void setUp() {
    pageable = Pageable.ofSize(1);
    user = UserTestData.buildUserOne();
    userDTO = UserTestData.buildUserDTO();
  }

  @Test
  void getUserByIdSuccessTest() {
    given(userRepository.findById(1L)).willReturn(Optional.of(user));
    given(userMapper.toUserDTO(user)).willReturn(userDTO);
    UserDTO userDTOByUsername = userService.getUserById(1L);
    assertAll(() -> assertThat(userDTO).isNotNull(),
        () -> assertEquals(userDTOByUsername.getUsername(), userDTO.getUsername()));
  }

  @Test
  void getUserByIdNotFoundException() {
    given(userRepository.findById(2L)).willReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> userService.getUserById(2L));
  }

  @Test
  void getAllUsersSuccessTest() {
    List<User> users = UserTestData.buildUsers();
    given(userRepository.findAll(pageable)).willReturn(new PageImpl<>(users));
    List<UserDTO> allUsers = userService.getAllUsers(pageable);
    assertAll(() -> assertThat(allUsers).isNotNull(),
        () -> assertEquals(allUsers.size(), 2));
  }
}
