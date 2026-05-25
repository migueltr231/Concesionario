package edu.unicauca.dsantiago135.concesionaria.Service;

import org.springframework.stereotype.Service;

import edu.unicauca.dsantiago135.concesionaria.Repository.UnitRepository;

@Service
public class UnitService {
   private final UnitRepository attUnitRepository;

   public UnitService (UnitRepository prmUnitRepository){
      this.attUnitRepository = prmUnitRepository;
   }
}
