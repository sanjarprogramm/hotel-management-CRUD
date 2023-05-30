package uz.qbg.appHotelServise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.qbg.appHotelServise.entity.Room;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Integer> {

boolean existsByRoomNumberAndFloor (String serialNumber,Integer floor);

boolean  existsByStatusAndRoomNumberAndFloorNot(String status,String serialNumber ,Integer floor);

    List<Room> findAllByHotelId(Integer hotelID);
}
