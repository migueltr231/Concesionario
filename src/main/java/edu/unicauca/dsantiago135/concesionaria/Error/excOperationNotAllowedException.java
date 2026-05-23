package edu.unicauca.dsantiago135.concesionaria.Error;

public class excOperationNotAllowedException extends RuntimeException{
   public excOperationNotAllowedException(String prmMessage) {
      super(prmMessage);
   }
}
