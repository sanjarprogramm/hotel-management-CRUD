package uz.qbg.appHotelServise.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uz.qbg.appHotelServise.entity.Room;
import uz.qbg.appHotelServise.payload.ApiRespons;
import uz.qbg.appHotelServise.payload.RoomDto;
import uz.qbg.appHotelServise.sevice.RoomServise;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/room")
public class RoomController {

    @Autowired
    RoomServise roomServise;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<?> add(@Valid @RequestBody RoomDto roomDto) {
        ApiRespons apiRespons = roomServise.addRoom(roomDto);
        return ResponseEntity.status(apiRespons.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiRespons);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<?> get(@RequestParam int page, @RequestParam int size) {
        Page<Room> roomPage = roomServise.rooms(page, size);
        return ResponseEntity.ok(roomPage);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<?> getById(@PathVariable Integer id) {
        Room room = roomServise.getRoomById(id);
        return ResponseEntity.ok(room);
    }

    @PutMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<?> put(@RequestBody RoomDto roomDto) {
        ApiRespons apiRespons = roomServise.editRoom(roomDto);
        return ResponseEntity.status(apiRespons.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiRespons);
    }

    @DeleteMapping(value = "/delete/{id}")
    public HttpEntity<?> delete (@PathVariable Integer id){
        ApiRespons apiRespons = roomServise.deleteById(id);
        return ResponseEntity.status(apiRespons.isSuccess()?HttpStatus.NO_CONTENT:HttpStatus.CONFLICT).body(apiRespons);
    }

    @GetMapping(value = "/hotel_room", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<?> getByCompanyId(@RequestParam Integer hotelID) {
        List<RoomDto> allByHotelId = roomServise.getAllByHotelId(hotelID);
        return ResponseEntity.ok(allByHotelId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
