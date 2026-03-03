package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarReaderService;
import id.ac.ui.cs.advprog.eshop.service.CarWriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController { // SRP & LSP: Tidak lagi menyatu atau extends ProductController

    private final CarWriterService carWriterService;
    private final CarReaderService carReaderService;

    @Autowired
    public CarController(CarWriterService carWriterService, CarReaderService carReaderService) {
        this.carWriterService = carWriterService;
        this.carReaderService = carReaderService;
    }

    @GetMapping("/createCar")
    public String createCarPage(Model model){
        model.addAttribute("car", new Car());
        return "CreateCar";
    }

    @PostMapping("/createCar")
    public String createCarPost(@ModelAttribute Car car){
        carWriterService.create(car);
        return "redirect:listCar";
    }

    @GetMapping("/listCar")
    public String carListPage(Model model){
        List<Car> allCars = carReaderService.findAll();
        model.addAttribute("cars", allCars);
        return "CarList";
    }

    @GetMapping("/editCar/{carId}") // Perbaikan URL agar konsisten
    public String editCarPage(@PathVariable String carId, Model model){
        Car car = carReaderService.findById(carId);
        model.addAttribute("car", car);
        return "EditCar";
    }

    @PostMapping("/editCar")
    public String editCarPost(@ModelAttribute Car car){
        carWriterService.update(car.getCarId(), car);
        return "redirect:listCar";
    }

    @PostMapping("/deleteCar")
    public String deleteCar(@RequestParam("carId") String carId){
        carWriterService.deleteCarById(carId);
        return "redirect:listCar";
    }
}
