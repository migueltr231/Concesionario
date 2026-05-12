package edu.unicauca.dsantiago135.concesionaria.Model;

import java.util.Date;

import lombok.Data;

@Data
public class clsSale {

//region Attributes
    private int attSaleId;
    private clsCustomer attCustomer;
    private clsEmployee attEmployee;
    private clsUnit attUnit;
    private Date attDateStart;
    private double attPrice;
    private String attStatus;
    private Date attDateEnd;
//endregion

}