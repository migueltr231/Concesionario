package edu.unicauca.dsantiago135.concesionaria.Error;

public class excValidationException extends RuntimeException{
   public excValidationException(String prmMessage) {
      super(prmMessage);
   }
}
