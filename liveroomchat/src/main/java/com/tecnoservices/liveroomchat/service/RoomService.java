package com.tecnoservices.liveroomchat.service;

import org.springframework.stereotype.Service;

import com.tecnoservices.liveroomchat.model.User;
import com.tecnoservices.liveroomchat.model.Room;
import com.tecnoservices.liveroomchat.model.RoomType;
import com.tecnoservices.liveroomchat.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // Inyección de dependencias por constructor (Lombok)
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserService userService; // Reutilizamos lógica de otro servicio

    public Room createRoom(Long userId, String title, RoomType type) {
        User user = userService.findById(userId);
        if (!user.isStreamerEligible()) {
            throw new RuntimeException("El usuario no tiene permisos para crear una sala");
        }
        Room room = new Room();
        room.setTitle(title);
        room.setUser(user);
        room.setType(type != null ? type : RoomType.VIDEO);
        return roomRepository.save(room);

    }

    public void closeRoom(String channelName) {
        Room room = roomRepository.findAll().stream()
                .filter(r -> r.getTitle().equals(channelName))
                .findFirst()
                .orElse(null);

        if (room != null) {
            room.setStatus("FINISHED");
            roomRepository.save(room);
        }
    }

}