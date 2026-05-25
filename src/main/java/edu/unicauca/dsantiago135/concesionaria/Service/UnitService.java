package edu.unicauca.dsantiago135.concesionaria.Service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import edu.unicauca.dsantiago135.concesionaria.Model.clsUnit;
import edu.unicauca.dsantiago135.concesionaria.Repository.UnitRepository;

@Service
public class UnitService {
   private final UnitRepository attUnitRepository;
   public HashMap<Integer, clsUnit> varUnits = new HashMap<>();

   public UnitService (UnitRepository prmUnitRepository){
      this.attUnitRepository = prmUnitRepository;
      //public void opRegisterUnit(clsUnit prmUnit)
      //public void opUpdateUnit(clsUnit prmUnit)
      //public void opUpdateUnitStatus(int prmId, String prmStatus)
      //public clsUnit opGetUnitById(int prmId)
      //public List<clsUnit> opGetAllUnits()
      //public List<clsUnit> opGetUnitsByStatus(String prmStatus)
   }
}
