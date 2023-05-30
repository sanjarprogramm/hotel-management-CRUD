package uz.qbg.appHotelServise.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelDto {


    private Integer id;

    private String name;

    private String address;

    private String phoneNumber;

    private Integer floor;

}
