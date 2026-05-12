package edu.unicauca.dsantiago135.concesionaria.Model;

import lombok.Data;

@Data
public class clsVehicle {

//region Attributes
    private int attVehicleId;

    private String attBrand;
    private String attModel;
    private int attYear;
    private String attBodyType;
    private String attFuelType;
    private String attCategory;
    private String attState;
//endregion

}