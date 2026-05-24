package edu.unicauca.dsantiago135.concesionaria.Repository;

import java.util.List;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import edu.unicauca.dsantiago135.concesionaria.Error.excDatabaseException;
import edu.unicauca.dsantiago135.concesionaria.Error.excDuplicateDataException;
import edu.unicauca.dsantiago135.concesionaria.Error.excNotFoundException;
import edu.unicauca.dsantiago135.concesionaria.Error.excOperationNotAllowedException;
import edu.unicauca.dsantiago135.concesionaria.Error.excValidationException;
import edu.unicauca.dsantiago135.concesionaria.Model.clsVehicle;

@Repository
public class VehicleRepository {

	// region ATTRIBUTES
	private final JdbcTemplate attJdbcTemplate;

	private static final String attPkg = "PKG_VEHICLE";
	private final SimpleJdbcCall attSpRegisterVehicle;
	private final SimpleJdbcCall attSpUpdateVehicle;
	private final SimpleJdbcCall attSpDisableVehicle;
	private final SimpleJdbcCall attFnVehicleExist;
	private final SimpleJdbcCall attFnGetVehicleById;
	private final SimpleJdbcCall attFnGetAllVehicles;

	// endregion

	// region CONSTRUCTOR
	public VehicleRepository(JdbcTemplate prmJdbcTemplate) {
		// Conexion
		this.attJdbcTemplate = prmJdbcTemplate;
		// Calls to procedures
		this.attSpRegisterVehicle = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withProcedureName("SP_REGISTER_VEHICLE");
		this.attSpUpdateVehicle = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withProcedureName("SP_UPDATE_VEHICLE");
		this.attSpDisableVehicle = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withProcedureName("SP_DISABLE_VEHICLE");
		this.attFnVehicleExist = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withFunctionName("FN_VEHICLE_EXIST");
		this.attFnGetVehicleById = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withFunctionName("FN_GET_VEHICLE_BY_ID");
		this.attFnGetAllVehicles = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withFunctionName("FN_GET_ALL_VEHICLES").returningResultSet("return", opVehicleRowMapper());
	}
	// endregion

	// region MAPPING
	/**
	 * Convierte un objeto {@link clsVehicle} en un {@link MapSqlParameterSource}
	 * listo para ser enviado como parámetros a un procedimiento almacenado de
	 * Oracle.
	 *
	 * @param prmVehicle objeto de tipo {@link clsVehicle} con los datos del
	 *                   vehículo
	 * @return {@link MapSqlParameterSource} con los parámetros mapeados para Oracle
	 */
	private MapSqlParameterSource opToParams(clsVehicle prmVehicle) {
		return new MapSqlParameterSource()
				.addValue("P_VEH_ID", prmVehicle.getAttVehicleId())
				.addValue("P_VEH_BRAND", prmVehicle.getAttBrand())
				.addValue("P_VEH_MODEL", prmVehicle.getAttModel())
				.addValue("P_VEH_YEAR", prmVehicle.getAttYear())
				.addValue("P_VEH_BODY_TYPE", prmVehicle.getAttBodyType())
				.addValue("P_VEH_FUEL_TYPE", prmVehicle.getAttFuelType())
				.addValue("P_VEH_CATEGORY", prmVehicle.getAttCategory())
				.addValue("P_VEH_STATE", prmVehicle.getAttState());
	}

	/**
	 * Convierte el resultado de un procedimiento almacenado de Oracle en un objeto
	 * {@link clsVehicle}.
	 * <p>
	 * El {@link Map} de entrada corresponde a los parámetros de salida retornados
	 * por {@link org.springframework.jdbc.core.simple.SimpleJdbcCall}.
	 * </p>
	 *
	 * @param prmRow {@link Map} con las columnas y valores retornados por Oracle,
	 *               donde la clave es el nombre del parámetro y el valor es de tipo
	 *               {@link Object}
	 * @return objeto de tipo {@link clsVehicle} con los datos mapeados
	 */
	private clsVehicle opToObject(ResultSet prmRow) throws SQLException {
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

	/**
	 * Convierte un entero en un parámetro para Oracle.
	 *
	 * @param prmId Id del vehículo
	 * @return parámetros compatibles con el procedure
	 */
	private MapSqlParameterSource opToId(int prmId){
		return new MapSqlParameterSource().addValue("P_VEH_ID", prmId);
	}
	// endregion

	// region PROCEDURES
	public void  opRegisterVehicle (clsVehicle prmVehicle)throws  excDatabaseException, excDuplicateDataException, excValidationException{
		attSpRegisterVehicle.execute(opToParams(prmVehicle));
	}

	public void  opUpdateVehicle(clsVehicle prmVehicle)throws excDatabaseException, excNotFoundException, excValidationException{
		attSpUpdateVehicle.execute(opToParams(prmVehicle));
	}

	public void  opDisableVehicle(int prmId)throws excDatabaseException, excNotFoundException, excOperationNotAllowedException{
		attSpDisableVehicle.execute(opToId(prmId));
	}

	public boolean  opVehicleExist(int prmId)throws excDatabaseException{
		Boolean varResult = attFnVehicleExist.executeFunction(Boolean.class,opToId(prmId));
		return Boolean.TRUE.equals(varResult);
	}

	public clsVehicle  opGetVehicleById(int prmId)throws excDatabaseException, excNotFoundException{

		clsVehicle varVehicle = new clsVehicle();
		Map<String,Object> varResult = attFnGetVehicleById.execute(opToId(prmId));
		@SuppressWarnings("unchecked")
		Map<String, Object> varVehicleMap = (Map<String, Object>) varResult.get("return");
		if(varVehicleMap == null )return null;
		varVehicle.setAttVehicleId(((Number)varVehicleMap.get("VEH_ID")).intValue());
		varVehicle.setAttYear(((Number)varVehicleMap.get("VEH_YEAR")).intValue());
		varVehicle.setAttBodyType((String)varVehicleMap.get("VEH_BODY_TYPE"));
		varVehicle.setAttBrand((String)varVehicleMap.get("VEH_BRAND"));
		varVehicle.setAttCategory((String)varVehicleMap.get("VEH_CATEGORY"));
		varVehicle.setAttFuelType((String)varVehicleMap.get("VEH_FUEL_TYPE"));
		varVehicle.setAttModel((String)varVehicleMap.get("VEH_MODEL"));
		varVehicle.setAttState((String)varVehicleMap.get("VEH_STATE"));

		return varVehicle;
	}

	public List<clsVehicle>  opGetAllVehicles()throws excDatabaseException{
		Map<String, Object> varResult = attFnGetAllVehicles.execute();
		@SuppressWarnings("unchecked")
		List<clsVehicle> varVehicles = (List<clsVehicle>) varResult.get("return");
		return varVehicles!=null? varVehicles: List.of();
	}
	// endregion

}