package uz.qbg.appHotelServise.repository;

import liquibase.pro.packaged.S;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.qbg.appHotelServise.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel,Integer > {

//    @Query(value = "select h from Hotel h where (name is not null and lower(h.name) " +
//            "like lower(concat('%', name, '%')) || (name is not null and lower(h.address) " +
//            "like lower(concat('%', name, '%'))))", nativeQuery = true)
//    boolean existsByNameAndAddress(String name);

    boolean existsByName(String name);

}
