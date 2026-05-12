package edu.unicauca.dsantiago135.concesionaria.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import edu.unicauca.dsantiago135.concesionaria.Model.clsVehicle;

@Repository
public class VehicleRepository {

//region ATTRIBUTES
private final JdbcTemplate attJdbcTemplate;

private final SimpleJdbcCall attSpRegisterX;
private final SimpleJdbcCall attSpUpdateX;
private final SimpleJdbcCall attSpGetX;
//endregion

//region CONSTRUCTOR
public VehicleRepository(JdbcTemplate prmJdbcTemplate) {
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
         * Convierte un objeto {@link clsVehicle} en un {@link MapSqlParameterSource}
         * listo para ser enviado como parámetros a un procedimiento almacenado de Oracle.
         *
         * @param prmVehicle objeto de tipo {@link clsVehicle} con los datos del vehículo
         * @return {@link MapSqlParameterSource} con los parámetros mapeados para Oracle
         */
        private MapSqlParameterSource opToParams(clsVehicle prmVehicle) {
                return new MapSqlParameterSource()
                        .addValue("P_VEH_ID",        prmVehicle.getAttVehicleId())
                        .addValue("P_VEH_BRAND",     prmVehicle.getAttBrand())
                        .addValue("P_VEH_MODEL",     prmVehicle.getAttModel())
                        .addValue("P_VEH_YEAR",      prmVehicle.getAttYear())
                        .addValue("P_VEH_BODY_TYPE", prmVehicle.getAttBodyType())
                        .addValue("P_VEH_FUEL_TYPE", prmVehicle.getAttFuelType())
                        .addValue("P_VEH_CATEGORY",  prmVehicle.getAttCategory())
                        .addValue("P_VEH_STATE",     prmVehicle.getAttState());
        }
        /**
         * Convierte el resultado de un procedimiento almacenado de Oracle en un objeto {@link clsVehicle}.
         * <p>
         * El {@link Map} de entrada corresponde a los parámetros de salida retornados
         * por {@link org.springframework.jdbc.core.simple.SimpleJdbcCall}.
         * </p>
         *
         * @param prmRow {@link Map} con las columnas y valores retornados por Oracle,
         *               donde la clave es el nombre del parámetro y el valor es de tipo {@link Object}
         * @return objeto de tipo {@link clsVehicle} con los datos mapeados
         */
        private clsVehicle opToObject(ResultSet prmRow) throws SQLException{
                clsVehicle varVehicle = new clsVehicle();

                varVehicle.setAttVehicleId(prmRow.getInt("VEH_ID"));
                varVehicle.setAttBrand(prmRow.getString("VEH_BRAND"));
                varVehicle.setAttModel(prmRow.getString("VEH_MODEL"));
                varVehicle.setAttYear(prmRow.getInt("VEH_YEAR"));
                varVehicle.setAttBodyType(prmRow.getString("VEH_BODY_TYPE"));
                varVehicle.setAttFuelType(prmRow.getString("VEH_FUEL_TYPE"));
                varVehicle.setAttCategory(prmRow.getString("VEH_CATEGORY"));
                varVehicle.setAttState(prmRow.getString("VEH_STATE"));

                return varVehicle;
        }
        /**
         * Sobrecarga para definir cómo convertir filas del cursor Oracle en objetos
         * {@link clsVehicle}.
         *
         * @return mapper reutilizable para consultas
         */
        private RowMapper<clsVehicle> opVehicleRowMapper() {
                return (rs, rowNum) -> opToObject(rs);
        }
//endregion

//region PROCEDURES

//endregion

}
