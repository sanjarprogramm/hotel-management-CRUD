package uz.qbg.appHotelServise.sevice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.qbg.appHotelServise.entity.Room;
import uz.qbg.appHotelServise.payload.ApiRespons;
import uz.qbg.appHotelServise.payload.RoomDto;
import uz.qbg.appHotelServise.repository.RoomRepository;

import java.awt.image.RasterFormatException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class RoomServise {
    @Autowired
    RoomRepository roomRepository;

    /**
     * jadvalga hona qushishdan avval bu qavatda shu tartib raqamli hona bor yoki yuqligi tekshiriladi
     *
     * @param roomDto
     * @return ApiRespons
     */

    public ApiRespons addRoom(RoomDto roomDto) {
        boolean existsBySerialNumber = roomRepository.existsByRoomNumberAndFloor(roomDto.getRoomNumber(), roomDto.getFloor());
        if (existsBySerialNumber)
            return new ApiRespons("ushbu qavatda bunday raqamli hona mavjud", false);
        Room room = new Room();
        room.setPrice(roomDto.getPrice());
        room.setStatus(roomDto.getStatus());
        room.setRoomNumber(roomDto.getRoomNumber());
        room.setFloor(room.getFloor());
        roomRepository.save(room);
        return new ApiRespons("Ruyhatga hona qushildi", true);

    }

    /**
     * @param page
     * @param size
     * @return honalar ro'yhati sahifalangan holda
     */
    public Page<Room> rooms(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "id");
        return roomRepository.findAll(pageable);
    }

    /**
     * id orqali honani ko'rish
     *
     * @param id
     * @return Room
     */

    public Room getRoomById(Integer id) {
        return roomRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Siz qidirgan hona jadvaldan topilmadi"));
    }

    public List<RoomDto> getAllByHotelId(Integer hotelID) {
        List<RoomDto> list = new ArrayList<>();
        List<Room> allByHotelId = roomRepository.findAllByHotelId(hotelID);
        allByHotelId.forEach(r -> {
            list.add(entityToDTO(r));
        });
        return list;
    }

    /**
     * Roomni taxrirlash
     *
     * @param roomDto
     * @return ApiRespons
     */

    public ApiRespons editRoom(RoomDto roomDto) {
        boolean statusAndSerialNumber = roomRepository
                .existsByStatusAndRoomNumberAndFloorNot(roomDto.getStatus(), roomDto.getRoomNumber(), roomDto.getFloor());
        if (statusAndSerialNumber)
            return new ApiRespons("jadvalda bu statusli hona shu tartib raqamda Avvaldan bor", false);
        Optional<Room> optionalRoom = roomRepository.findById(roomDto.getId());
        if (!optionalRoom.isPresent())
            return new ApiRespons("Siz qidirgan hona Jadavaldan topilmadi", false);
        Room room = optionalRoom.get();
        room.setId(roomDto.getId());
        room.setHotelId(roomDto.getHotelId());
        room.setPrice(roomDto.getPrice());
        room.setStatus(roomDto.getStatus());
        room.setFloor(roomDto.getFloor());
        room.setRoomNumber(roomDto.getRoomNumber());
        roomRepository.save(room);
        return new ApiRespons("hona taxrirlandi", true);

    }

    /**
     * Id orqali jadvaldan honani o'chirish
     *
     * @param id
     * @return ApiRespons
     */
    public ApiRespons deleteById(Integer id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (!optionalRoom.isPresent())
            return new ApiRespons("jadvalda bunday id ga ega hona topilmadi", false);
        roomRepository.deleteById(id);
        return new ApiRespons("hona jadvaldan o'chirildi", true);
    }

    private RoomDto entityToDTO(Room room) {
        RoomDto dto = new RoomDto();
        dto.setId(room.getId());
        dto.setRoomNumber(room.getRoomNumber());
        dto.setHotelId(room.getHotelId());
        dto.setFloor(room.getFloor());
        dto.setPrice(room.getPrice());
        dto.setStatus(room.getStatus());
        dto.setEmpty(room.isEmpty());
        return dto;
    }

}
