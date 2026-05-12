package edu.unicauca.dsantiago135.concesionaria.Model;

import java.util.Date;

import lombok.Data;

@Data
public class clsSalesGoal {

//region Attributes

    private int attSalesGoalId;
    private clsEmployee attEmployee;
    private clsDealership attDealership;

    private String attGoalType;
    private double attTargetValue;
    private Date attStartDate;
    private Date attEndDate;
    private String attState;
//endregion

}