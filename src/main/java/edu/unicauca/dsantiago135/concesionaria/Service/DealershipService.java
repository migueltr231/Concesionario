package edu.unicauca.dsantiago135.concesionaria.Service;

import org.springframework.stereotype.Service;

import edu.unicauca.dsantiago135.concesionaria.Repository.DealershipRepository;

@Service
public class DealershipService {
   private final DealershipRepository attDealershipRepository;

   public DealershipService(DealershipRepository prmDealershipRepository){
      this.attDealershipRepository = prmDealershipRepository;
   }
}
