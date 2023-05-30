package uz.qbg.appHotelServise.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.qbg.appHotelServise.payload.RoomDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String status;

    @Column(name = "room_number", unique = true)
    private String roomNumber;

    private Integer floor;

    @Column(name = "hotel_id")
    private Integer hotelId;

    private String price;

    @Column(name = "is_empty")
    private boolean empty = true;

    public RoomDto toDto() {
        RoomDto roomDto = new RoomDto();
        roomDto.setStatus(getStatus());
        roomDto.setRoomNumber(getRoomNumber());
        roomDto.setPrice(getPrice());
        roomDto.setFloor(getFloor());
        roomDto.setHotelId(getHotelId());
        return roomDto;
    }
}
