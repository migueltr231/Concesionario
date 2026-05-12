package edu.unicauca.dsantiago135.concesionaria.Repository;

import java.util.Map;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import edu.unicauca.dsantiago135.concesionaria.Model.clsDealership;

@Repository
public class DealershipRepository {

//region ATTRIBUTES
private final JdbcTemplate attJdbcTemplate;

private final SimpleJdbcCall attSpRegisterX;
private final SimpleJdbcCall attSpUpdateX;
private final SimpleJdbcCall attSpGetX;
//endregion

//region CONSTRUCTOR
public DealershipRepository(JdbcTemplate prmJdbcTemplate) {
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
 * Convierte un objeto {@link clsDealership} en un {@link MapSqlParameterSource}
 * listo para ser enviado como parámetros a un procedimiento almacenado de Oracle.
 *
 * @param prmDealership objeto de tipo {@link clsDealership} con los datos de la concesionaria
 * @return {@link MapSqlParameterSource} con los parámetros mapeados para Oracle
 */
private MapSqlParameterSource opToParams(clsDealership prmDealership) {

        return new MapSqlParameterSource()
                .addValue("P_DEA_ID", prmDealership.getAttDealershipId())
                .addValue("P_DEA_NAME", prmDealership.getAttName())
                .addValue("P_DEA_STATE", prmDealership.getAttState())
                .addValue("P_DEA_ADDRESS", prmDealership.getAttAddress())
                .addValue("P_DEA_PHONE", prmDealership.getAttPhone());
    }
/**
 * Convierte el resultado de un procedimiento almacenado de Oracle en un objeto {@link clsDealership}.
 * <p>
 * El {@link Map} de entrada corresponde a los parámetros de salida retornados
 * por {@link org.springframework.jdbc.core.simple.SimpleJdbcCall}.
 * </p>
 *
 * @param prmRow {@link Map} con las columnas y valores retornados por Oracle,
 *               donde la clave es el nombre del parámetro y el valor es de tipo {@link Object}
 * @return objeto de tipo {@link clsDealership} con los datos mapeados
 */
private clsDealership opToObject(ResultSet prmRow) throws SQLException {
        clsDealership varDealership = new clsDealership();

        varDealership.setAttDealershipId(prmRow.getInt("DEA_ID"));
        varDealership.setAttName(prmRow.getString("DEA_NAME"));
        varDealership.setAttState(prmRow.getString("DEA_STATE"));
        varDealership.setAttAddress(prmRow.getString("DEA_ADDRESS"));
        varDealership.setAttPhone(prmRow.getString("DEA_PHONE"));

        return varDealership;
    }
/**
    * Sobrecarga para definir cómo convertir filas del cursor Oracle en objetos
    * {@link clsDealership}.
    *
    * @return mapper reutilizable para consultas
    */
        private RowMapper<clsDealership> opDealershipRowMapper() {
                return (rs, rowNum) ->opToObject(rs);
                }
//endregion

//region PROCEDURES

//endregion

}
