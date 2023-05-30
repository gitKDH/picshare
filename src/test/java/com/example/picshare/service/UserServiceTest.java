 package com.example.picshare.service;

 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;
 import org.mockito.InjectMocks;
 import org.mockito.Mock;
 import org.mockito.MockitoAnnotations;

 import static org.mockito.Mockito.*;

 public class UserServiceTest {

     @Mock
     private UserRepository userRepository;

     @InjectMocks
     private UserService userService;

     @BeforeEach
     public void setup() {
         MockitoAnnotations.openMocks(this);
     }

     @Test
     public void testRegisterUser() {
         // 테스트용 데이터
         User user = new User();
         user.setUsername("testuser");
         user.setPassword("testpassword");

         // Mock 객체 설정
         when(userRepository.save(user)).thenReturn(user);

         // 테스트 실행
         userService.registerUser(user);

         // Mock 메서드 호출 검증
         verify(userRepository, times(1)).save(user);
     }

     // 나머지 테스트 메서드들...
 }
