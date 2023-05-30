package uz.qbg.appHotelServise.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.qbg.appHotelServise.payload.HotelDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "floor")
    private Integer floor;


    public HotelDto toDto() {
        return HotelDto.builder()
                .name(getName())
                .address(getAddress())
                .floor(getFloor())
                .phoneNumber(getPhoneNumber())
                .build();
    }
}
