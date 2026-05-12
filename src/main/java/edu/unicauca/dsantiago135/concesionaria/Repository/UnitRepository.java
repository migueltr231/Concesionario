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

import edu.unicauca.dsantiago135.concesionaria.Model.clsDealership;
import edu.unicauca.dsantiago135.concesionaria.Model.clsUnit;
import edu.unicauca.dsantiago135.concesionaria.Model.clsVehicle;

@Repository
public class UnitRepository {

//region ATTRIBUTES
private final JdbcTemplate attJdbcTemplate;

private final SimpleJdbcCall attSpRegisterX;
private final SimpleJdbcCall attSpUpdateX;
private final SimpleJdbcCall attSpGetX;
//endregion

//region CONSTRUCTOR
public UnitRepository(JdbcTemplate prmJdbcTemplate) {
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
 * Convierte un objeto {@link clsUnit} en un {@link MapSqlParameterSource}
 * listo para ser enviado como parámetros a un procedimiento almacenado de Oracle.
 *
 * @param prmUnit objeto de tipo {@link clsUnit} con los datos de la unidad
 * @return {@link MapSqlParameterSource} con los parámetros mapeados para Oracle
 */
private MapSqlParameterSource opToParams(clsUnit prmUnit) {
        return new MapSqlParameterSource()
                .addValue("P_UNI_ID",            prmUnit.getAttUnitId())
                .addValue("P_DEA_ID",            prmUnit.getAttDealership().getAttDealershipId())
                .addValue("P_VEH_ID",            prmUnit.getAttVehicle().getAttVehicleId())
                .addValue("P_UNI_LICENSE_PLATE", prmUnit.getAttLicensePlate())
                .addValue("P_UNI_COLOR",         prmUnit.getAttColor())
                .addValue("P_UNI_MILEAGE",       prmUnit.getAttMileage())
                .addValue("P_UNI_DATE_ENTRY",    prmUnit.getAttDateEntry())
                .addValue("P_UNI_CONDITION",     prmUnit.getAttCondition())
                .addValue("P_UNI_STATUS",        prmUnit.getAttStatus());
    }
/**
     * Convierte el resultado de un procedimiento almacenado de Oracle en un objeto {@link clsUnit}.
     * <p>
     * El {@link Map} de entrada corresponde a los parámetros de salida retornados
     * por {@link org.springframework.jdbc.core.simple.SimpleJdbcCall}.
     * </p>
     *
     * @param prmRow {@link Map} con las columnas y valores retornados por Oracle,
     *               donde la clave es el nombre del parámetro y el valor es de tipo {@link Object}
     * @return objeto de tipo {@link clsUnit} con los datos mapeados
     */
private clsUnit opToObject(ResultSet prmRow) throws SQLException{
        clsUnit varUnit = new clsUnit();

        varUnit.setAttUnitId(prmRow.getInt("UNIT_ID"));

        clsDealership varDealership = new clsDealership();
        varDealership.setAttDealershipId(prmRow.getInt("DEA_ID"));
        varUnit.setAttDealership(varDealership);

        clsVehicle varVehicle = new clsVehicle();
        varVehicle.setAttVehicleId(prmRow.getInt("VEH_ID"));
        varUnit.setAttVehicle(varVehicle);

        varUnit.setAttLicensePlate(prmRow.getString("UNIT_LICENSE_PLATE"));
        varUnit.setAttColor(prmRow.getString("UNIT_COLOR"));
        varUnit.setAttMileage(prmRow.getInt("UNIT_MILEAGE"));
        varUnit.setAttDateEntry(prmRow.getDate("UNIT_DATE_ENTRY"));
        varUnit.setAttCondition(prmRow.getString("UNIT_CONDITION"));
        varUnit.setAttStatus(prmRow.getString("UNIT_STATUS"));

        return varUnit;
}
    /**
    * Sobrecarga para definir cómo convertir filas del cursor Oracle en objetos
    * {@link clsUnit}.
    *
    * @return mapper reutilizable para consultas
    */
    private RowMapper<clsUnit> opUnitRowMapper() {
        return (rs, rowNum) ->opToObject(rs);
        }
//endregion

//region PROCEDURES

//endregion

}
