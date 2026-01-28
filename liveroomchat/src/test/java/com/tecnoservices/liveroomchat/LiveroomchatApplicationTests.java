package com.tecnoservices.liveroomchat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tecnoservices.liveroomchat.model.Room;
import com.tecnoservices.liveroomchat.model.User;
import com.tecnoservices.liveroomchat.repository.RoomRepository;
import com.tecnoservices.liveroomchat.service.RoomService;
import com.tecnoservices.liveroomchat.service.UserService;

@ExtendWith(MockitoExtension.class)
class LiveroomchatApplicationTests {

	@Mock
	private RoomRepository roomRepository; // Simulamos la DB

	@Mock
	private UserService userService;

	@InjectMocks
	private RoomService roomService; // El servicio a probar

	@Test
	void createRoom_ShouldReturnRoom_WhenUserIsEligible() {
		// 1. Arrange (Preparar datos)
		User mockUser = new User();
		mockUser.setId(1L);
		mockUser.setStreamerEligible(true);

		// Definimos comportamiento simulado
		when(userService.findById(1L)).thenReturn(mockUser);
		when(roomRepository.save(any(Room.class))).thenAnswer(i -> i.getArguments()[0]);

		// 2. Act (Ejecutar)
		Room createdRoom = roomService.createRoom(1L, "Mi primer live!",
				com.tecnoservices.liveroomchat.model.RoomType.VIDEO);

		// 3. Assert (Verificar)
		assertNotNull(createdRoom);
		assertEquals("Mi primer live!", createdRoom.getTitle());
		verify(roomRepository, times(1)).save(any(Room.class)); // Verificamos que se guardó
	}
}
