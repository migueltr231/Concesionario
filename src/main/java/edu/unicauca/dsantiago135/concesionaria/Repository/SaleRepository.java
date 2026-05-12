package edu.unicauca.dsantiago135.concesionaria.Repository;

import java.util.Date;
import java.util.Map;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import edu.unicauca.dsantiago135.concesionaria.Model.clsCustomer;
import edu.unicauca.dsantiago135.concesionaria.Model.clsEmployee;
import edu.unicauca.dsantiago135.concesionaria.Model.clsSale;
import edu.unicauca.dsantiago135.concesionaria.Model.clsUnit;

@Repository
public class SaleRepository {

//region ATTRIBUTES
private final JdbcTemplate attJdbcTemplate;

private final SimpleJdbcCall attSpRegisterX;
private final SimpleJdbcCall attSpUpdateX;
private final SimpleJdbcCall attSpGetX;
//endregion

//region CONSTRUCTOR
public SaleRepository(JdbcTemplate prmJdbcTemplate) {
//Conexion 
        this.attJdbcTemplate = prmJdbcTemplate;
//Calls to procedures
        this.attSpRegisterX = new SimpleJdbcCall(attJdbcTemplate).withProcedureName("sp_register_x");
        this.attSpUpdateX = new SimpleJdbcCall(attJdbcTemplate).withProcedureName("sp_update_x");
        this.attSpGetX = new SimpleJdbcCall(attJdbcTemplate).withProcedureName("sp_get_x");
}
//endregion

//region MAPPING
/**
 * Convierte un objeto {@link clsSale} en un {@link MapSqlParameterSource}
 * listo para ser enviado como parámetros a un procedimiento almacenado de Oracle.
 *
 * @param prmSale objeto de tipo {@link clsSale} con los datos de la venta
 * @return {@link MapSqlParameterSource} con los parámetros mapeados para Oracle
 */
private MapSqlParameterSource opToParams(clsSale prmSale) {
        return new MapSqlParameterSource()
                .addValue("P_SALE_ID",         prmSale.getAttSaleId())
                .addValue("P_CUS_ID",         prmSale.getAttCustomer().getAttCustomerId())
                .addValue("P_EMP_ID",         prmSale.getAttEmployee().getAttEmployeeId())
                .addValue("P_UNI_ID",         prmSale.getAttUnit().getAttUnitId())
                .addValue("P_SALE_DATE_START", prmSale.getAttDateStart())
                .addValue("P_SALE_PRICE",      prmSale.getAttPrice())
                .addValue("P_SALE_STATUS",     prmSale.getAttStatus())
                .addValue("P_SALE_DATE_END",   prmSale.getAttDateEnd());
}   
/**
* Convierte el resultado de un procedimiento almacenado de Oracle en un objeto {@link clsSale}.
* <p>
* El {@link Map} de entrada corresponde a los parámetros de salida retornados
* por {@link org.springframework.jdbc.core.simple.SimpleJdbcCall}.
* </p>
*
* @param prmRow {@link Map} con las columnas y valores retornados por Oracle,
*               donde la clave es el nombre del parámetro y el valor es de tipo {@link Object}
* @return objeto de tipo {@link clsSale} con los datos mapeados
*/
private clsSale opToObject(ResultSet prmRow) throws SQLException{
        clsSale varSale = new clsSale();

        varSale.setAttSaleId(prmRow.getInt("SALE_ID"));

        clsCustomer varCustomer = new clsCustomer();
        varCustomer.setAttCustomerId(prmRow.getInt("CUS_ID"));
        varSale.setAttCustomer(varCustomer);

        clsEmployee varEmployee = new clsEmployee();
        varEmployee.setAttEmployeeId(prmRow.getInt("EMP_ID"));
        varSale.setAttEmployee(varEmployee);

        clsUnit varUnit = new clsUnit();
        varUnit.setAttUnitId((prmRow.getInt("UNI_ID")));
        varSale.setAttUnit(varUnit);

        varSale.setAttDateStart(prmRow.getDate("SALE_DATE_START"));
        varSale.setAttPrice(prmRow.getDouble("SALE_PRICE"));
        varSale.setAttStatus(prmRow.getString("SALE_STATUS"));
        varSale.setAttDateEnd(prmRow.getDate("SALE_DATE_END"));
        return varSale;
}
    /**
    * Sobrecarga para definir cómo convertir filas del cursor Oracle en objetos
    * {@link clsCustomer}.
    *
    * @return mapper reutilizable para consultas
    */
    private RowMapper<clsSale> opSaleRowMapper() {
        return (rs, rowNum) ->opToObject(rs);
        }
//endregion

//region PROCEDURES

//endregion

}
