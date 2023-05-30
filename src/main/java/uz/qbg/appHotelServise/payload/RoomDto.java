package uz.qbg.appHotelServise.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    private Integer id;
    private String status;
    private String roomNumber;
    private Integer floor ;
    private Integer hotelId ;
    private String price;
    private boolean empty;
}
