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
import edu.unicauca.dsantiago135.concesionaria.Model.clsEmployee;

@Repository
public class EmployeeRepository {

//region ATTRIBUTES
private final JdbcTemplate attJdbcTemplate;

private final SimpleJdbcCall attSpRegisterX;
private final SimpleJdbcCall attSpUpdateX;
private final SimpleJdbcCall attSpGetX;
//endregion

//region CONSTRUCTOR
public EmployeeRepository(JdbcTemplate prmJdbcTemplate) {
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
 * Convierte un objeto {@link clsEmployee} en un {@link MapSqlParameterSource}
 * listo para ser enviado como parámetros a un procedimiento almacenado de Oracle.
 *
 * @param prmEmployee objeto de tipo {@link clsEmployee} con los datos de 
 * @return {@link MapSqlParameterSource} con los parámetros mapeados para Oracle
 */
private MapSqlParameterSource opToParams(clsEmployee prmEmployee) {

return new MapSqlParameterSource()
        .addValue("P_EMP_ID", prmEmployee.getAttEmployeeId())
        .addValue("P_DEA_ID", prmEmployee.getAttDealership().getAttDealershipId())
        .addValue("P_EMP_NAME", prmEmployee.getAttName())
        .addValue("P_EMP_PHONE", prmEmployee.getAttPhone())
        .addValue("P_EMP_SALARY", prmEmployee.getAttSalary())
        .addValue("P_EMP_HIRE_DATE", prmEmployee.getAttHireDate())
        .addValue("P_EMP_ROLE", prmEmployee.getAttRole())
        .addValue("P_EMP_STATE", prmEmployee.getAttState());
    }
/**
 * Convierte el resultado de un procedimiento almacenado de Oracle en un objeto {@link clsEmployee}.
 * <p>
 * El {@link Map} de entrada corresponde a los parámetros de salida retornados
 * por {@link org.springframework.jdbc.core.simple.SimpleJdbcCall}.
 * </p>
 *
 * @param prmRow {@link Map} con las columnas y valores retornados por Oracle,
 *               donde la clave es el nombre del parámetro y el valor es de tipo {@link Object}
 * @return objeto de tipo {@link clsEmployee} con los datos mapeados
 */
private clsEmployee opToObject(ResultSet prmRow) throws SQLException{
        clsEmployee varEmployee = new clsEmployee();

        varEmployee.setAttEmployeeId(prmRow.getInt("EMP_ID"));

        clsDealership varDealership = new clsDealership();
        varDealership.setAttDealershipId(prmRow.getInt("DEA_ID"));
        varEmployee.setAttDealership(varDealership);

        varEmployee.setAttName(prmRow.getString("EMP_NAME"));
        varEmployee.setAttPhone(prmRow.getString("EMP_PHONE"));
        varEmployee.setAttRole(prmRow.getString("EMP_ROLE"));
        varEmployee.setAttSalary(prmRow.getDouble("EMP_SALARY"));
        varEmployee.setAttState(prmRow.getString("EMP_STATE"));
        varEmployee.setAttHireDate(prmRow.getDate("EMP_HIRE_DATE"));
        return varEmployee;
}
    /**
    * Sobrecarga para definir cómo convertir filas del cursor Oracle en objetos
    * {@link clsEmployee}.
    *
    * @return mapper reutilizable para consultas
    */
    private RowMapper<clsEmployee> opEmployeeRowMapper() {
        return (rs, rowNum) ->opToObject(rs);
        }
//endregion

//region PROCEDURES

//endregion

}
