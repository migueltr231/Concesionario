package edu.unicauca.dsantiago135.concesionaria.Service;

import org.springframework.stereotype.Service;

import edu.unicauca.dsantiago135.concesionaria.Repository.SaleRepository;

@Service
public class SaleService {
   private final SaleRepository attSaleRepository;

   public SaleService (SaleRepository prmSaleRepository){
      this.attSaleRepository = prmSaleRepository;
   }
}
