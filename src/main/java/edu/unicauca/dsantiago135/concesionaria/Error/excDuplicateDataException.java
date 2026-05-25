package edu.unicauca.dsantiago135.concesionaria.Error;

public class excDuplicateDataException extends RuntimeException{
   public excDuplicateDataException(String prmMessage) {
      super(prmMessage);
   }

   public excDuplicateDataException(String prmMessage, Throwable cause) {
      super(prmMessage, cause);
   }
}
