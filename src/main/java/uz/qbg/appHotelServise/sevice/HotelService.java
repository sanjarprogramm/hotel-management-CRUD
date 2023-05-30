package uz.qbg.appHotelServise.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.qbg.appHotelServise.entity.Hotel;
import uz.qbg.appHotelServise.entity.Room;
import uz.qbg.appHotelServise.payload.ApiRespons;
import uz.qbg.appHotelServise.payload.HotelDto;
import uz.qbg.appHotelServise.payload.RoomDto;
import uz.qbg.appHotelServise.repository.HotelRepository;
import uz.qbg.appHotelServise.repository.RoomRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class HotelService {

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    RoomRepository roomRepository;

    public ApiRespons addHotel(HotelDto hotelDto) {
        boolean existsByName = hotelRepository.existsByName(hotelDto.getName());
        if (existsByName)
            return new ApiRespons("Jadvalda bunday mehmonhona Avvaldan bor", false);
        Hotel hotel = new Hotel();
        hotel.setAddress(hotelDto.getAddress());
        hotel.setName(hotelDto.getName());
        hotel.setPhoneNumber(hotelDto.getPhoneNumber());
        hotel.setFloor(hotelDto.getFloor());
        hotelRepository.save(hotel);
        return new ApiRespons("Jadvalga Yangi mehmonhona saqlandi", true);

    }

    public Page<Hotel> hotelPage(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "id");
        return hotelRepository.findAll(pageable);
    }


    public Hotel getHotelById(Integer id) {
        return hotelRepository.findById(id).orElseThrow(() ->new RuntimeException("siz qidirgan mehmonhona jadvalda yo'q"));
    }

//    public ApiRespons search (String name){
//        hotelRepository.existsByNameAndAddress(String name);
//
//    }

    public ApiRespons editHotel(HotelDto hotelDto) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelDto.getId());
        if (!optionalHotel.isPresent())
            return new ApiRespons("Bunday Id ga ega mehmonhona jadvalda yo'q", false);
        Hotel hotel = new Hotel();
        hotel.setId(hotelDto.getId());
        hotel.setAddress(hotelDto.getAddress());
        hotel.setName(hotelDto.getName());
        hotel.setPhoneNumber(hotelDto.getPhoneNumber());
        hotel.setFloor(hotelDto.getFloor());
        hotelRepository.save(hotel);
        return new ApiRespons("mehmonhonaga o'zgartirish kiritildi", true);

    }

    public ApiRespons deleteById(Integer id) {

        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (!optionalHotel.isPresent())
            return new ApiRespons("Bunday Id ga ega mehmonhona jadvalda yo'q", false);
        hotelRepository.deleteById(id);
        return new ApiRespons("Jadvaldan mehmonhona o'chirildi", true);
    }

    private Room toEntity(RoomDto dto) {
        Room room = new Room();
        room.setPrice(dto.getPrice());
        room.setRoomNumber(dto.getRoomNumber());
        room.setFloor(dto.getFloor());
        room.setEmpty(dto.isEmpty());
        room.setStatus(dto.getStatus());
        room.setHotelId(dto.getHotelId());
        return roomRepository.save(room);
    }

    private Set<Room> roomList(Set<RoomDto> roomDtoList) {
        Set<Room> roomSet = new HashSet<>();
        roomDtoList.forEach(r -> {
            roomSet.add(toEntity(r));
        });
        return roomSet;
    }


}
