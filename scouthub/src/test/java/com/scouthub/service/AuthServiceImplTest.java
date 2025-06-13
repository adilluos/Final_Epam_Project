package com.scouthub.service;

import com.scouthub.dto.PlayerRegistrationRequest;
import com.scouthub.model.Player;
import com.scouthub.repository.PlayerRepository;
import com.scouthub.repository.ScoutRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceImplTest {
    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private ScoutRepository scoutRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerPlayer_shouldSavePlayer_whenPasswordsMatch() {
        PlayerRegistrationRequest request = new PlayerRegistrationRequest();
        request.setUsername("john");
        request.setPassword("password");
        request.setRepeatPassword("password");
        request.setEmail("john@example.com");
        request.setName("John");
        request.setSurname("Doe");

        when(playerRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Player savedPlayer = authService.registerPlayer(request);

        assertThat(savedPlayer.getUsername()).isEqualTo("john");
        assertThat(savedPlayer.getPassword()).isNotEqualTo("password");
        verify(playerRepository, times(1)).save(any());
    }

    @Test
    void registerPlayer_shouldThrow_whenPasswordsDoNotMatch() {
        PlayerRegistrationRequest request = new PlayerRegistrationRequest();
        request.setPassword("abc");
        request.setRepeatPassword("xyz");

        assertThatThrownBy(() -> authService.registerPlayer(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Passwords do not match.");
    }
}
