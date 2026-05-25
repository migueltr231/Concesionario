package edu.unicauca.dsantiago135.concesionaria.Service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import edu.unicauca.dsantiago135.concesionaria.Model.clsSale;
import edu.unicauca.dsantiago135.concesionaria.Repository.SaleRepository;

@Service
public class SaleService {
   private final SaleRepository attSaleRepository;
   public HashMap<Integer, clsSale> varSales = new HashMap<>();

   public SaleService (SaleRepository prmSaleRepository){
      this.attSaleRepository = prmSaleRepository;
   }
   //public void opRegisterSale(clsSale prmSale)
   //public void opRegisterReservation(clsSale prmSale)
   //public void opCompleteReservation(int prmId)
   //public void opCancelReservation(int prmId)
   //public clsSale opGetSaleById(int prmId)
   //public List<clsSale> opGetAllSales()
   //public List<clsSale> opGetSalesByStatus(String prmStatus)
}
