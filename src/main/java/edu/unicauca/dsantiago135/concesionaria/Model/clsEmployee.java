package edu.unicauca.dsantiago135.concesionaria.Model;

import java.util.Date;
import lombok.Data;

@Data
public class clsEmployee {

//region Attributes
    private int attEmployeeId;
    private clsDealership attDealership;
    private String attName;
    private String attPhone;
    private double attSalary;
    private Date attHireDate;
    private String attRole;
    private String attState;
//endregion

}