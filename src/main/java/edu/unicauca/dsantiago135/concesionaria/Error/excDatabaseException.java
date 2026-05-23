package edu.unicauca.dsantiago135.concesionaria.Error;

public class excDatabaseException extends RuntimeException{
   public excDatabaseException(String prmMessage) {
      super(prmMessage);
   }
}
