package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository; // Import Interfacenya
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarWriterServiceImpl implements CarWriterService {

    @Autowired
    private CarRepository carRepository; // DIP: Bergantung pada Interface

    @Override
    public Car create(Car car) {
        // SRP: Hanya meneruskan perintah ke repository
        return carRepository.create(car);
    }

    @Override
    public void update(String carId, Car car) {
        carRepository.update(carId, car);
    }

    @Override
    public void deleteCarById(String carId) {
        carRepository.delete(carId);
    }
}