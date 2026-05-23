package edu.unicauca.dsantiago135.concesionaria.Error;

public class excInactiveStateException extends RuntimeException{
   public excInactiveStateException(String prmMessage) {
      super(prmMessage);
   }
}
