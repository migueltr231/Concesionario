package edu.unicauca.dsantiago135.concesionaria.Service;

import org.springframework.stereotype.Service;

import edu.unicauca.dsantiago135.concesionaria.Repository.VehicleRepository;

@Service
public class VehicleService {
   private final VehicleRepository attVehicleRepository;

   public VehicleService (VehicleRepository prmVehicleRepository){
      this.attVehicleRepository = prmVehicleRepository;
   }
}
