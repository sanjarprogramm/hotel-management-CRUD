package uz.qbg.appHotelServise.controllers;

import lombok.AllArgsConstructor;
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
import uz.qbg.appHotelServise.entity.Hotel;
import uz.qbg.appHotelServise.payload.ApiRespons;
import uz.qbg.appHotelServise.payload.HotelDto;
import uz.qbg.appHotelServise.sevice.HotelService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/hotel")
@AllArgsConstructor
public class HotelController {



    private final HotelService hotelService;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<?> create(@Valid @RequestBody HotelDto hotelDto) {
        ApiRespons apiRespons = hotelService.addHotel(hotelDto);
        return ResponseEntity.status(apiRespons.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiRespons);
    }

    @GetMapping
    public HttpEntity<?> getAll(@RequestParam int page, @RequestParam int size) {
        Page<Hotel> hotels = hotelService.hotelPage(page, size);
        return ResponseEntity.ok(hotels);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<?> getById(@PathVariable Integer id){
        Hotel hotel = hotelService.getHotelById(id);
        return ResponseEntity.ok(hotel);
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<?> update (@RequestBody HotelDto hotelDto){
        ApiRespons apiRespons = hotelService.editHotel( hotelDto  );
        return ResponseEntity.status(apiRespons.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiRespons);
    }

    @DeleteMapping(value = "/delete/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        ApiRespons apiRespons = hotelService.deleteById(id);
        return ResponseEntity.status(apiRespons.isSuccess()?HttpStatus.NO_CONTENT:HttpStatus.CONFLICT).body(apiRespons);
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
