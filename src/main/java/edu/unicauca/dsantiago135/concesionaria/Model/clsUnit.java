package edu.unicauca.dsantiago135.concesionaria.Model;

import java.util.Date;

import lombok.Data;

@Data
public class clsUnit {

//region Attributes
    private int attUnitId;
    private clsDealership attDealership;
    private clsVehicle attVehicle;
    
    private String attLicensePlate;
    private String attColor;
    private int attMileage;
    private Date attDateEntry;
    private String attCondition;
    private String attStatus;
//endregion

}