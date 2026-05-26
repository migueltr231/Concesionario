package edu.unicauca.dsantiago135.concesionaria.Repository;

import java.util.List;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import edu.unicauca.dsantiago135.concesionaria.Error.excDatabaseException;
import edu.unicauca.dsantiago135.concesionaria.Model.clsDealership;
import edu.unicauca.dsantiago135.concesionaria.Model.clsUnit;
import edu.unicauca.dsantiago135.concesionaria.Model.clsVehicle;

@Repository
public class UnitRepository {

	// region ATTRIBUTES
	private final JdbcTemplate attJdbcTemplate;

	private static final String attPkg = "PKG_UNIT"; 
	private final SimpleJdbcCall attSpRegisterUnit;
	private final SimpleJdbcCall attSpUpdateUnit;
	private final SimpleJdbcCall attSpUpdateUnitStatus;
	private final SimpleJdbcCall attFnUnitExist;
	private final SimpleJdbcCall attFnGetUnitById;
	private final SimpleJdbcCall attFnGetAllUnits;
	private final SimpleJdbcCall attFnGetUnitsByStatus;
	// endregion

	// region CONSTRUCTOR
	public UnitRepository(JdbcTemplate prmJdbcTemplate) {
		// Conexion
		this.attJdbcTemplate = prmJdbcTemplate;
		// Calls to procedures
		this.attSpRegisterUnit = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withProcedureName("SP_REGISTER_UNIT");
		this.attSpUpdateUnit = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withProcedureName("SP_UPDATE_UNIT");
		this.attSpUpdateUnitStatus = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withProcedureName("SP_UPDATE_UNIT_STATUS");
		this.attFnUnitExist = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withFunctionName("FN_UNIT_EXIST");
		this.attFnGetUnitById = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withFunctionName("FN_GET_UNIT_BY_ID").returningResultSet("return", opUnitRowMapper());
		this.attFnGetUnitsByStatus = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withFunctionName("FN_UNITS_BY_STATUS").returningResultSet("return", opUnitRowMapper());
		this.attFnGetAllUnits = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withFunctionName("FN_GET_ALL_UNITS").returningResultSet("return",opUnitRowMapper());
	}
	// endregion

	// region MAPPING
	/**
	 * Convierte un objeto {@link clsUnit} en un {@link MapSqlParameterSource}
	 * listo para ser enviado como parámetros a un procedimiento almacenado de
	 * Oracle.
	 *
	 * @param prmUnit objeto de tipo {@link clsUnit} con los datos de la unidad
	 * @return {@link MapSqlParameterSource} con los parámetros mapeados para Oracle
	 */
	private MapSqlParameterSource opToParams(clsUnit prmUnit) {
		return new MapSqlParameterSource()
				.addValue("P_UNIT_ID", prmUnit.getAttUnitId())
				.addValue("P_DEA_ID", prmUnit.getAttDealership().getAttDealershipId())
				.addValue("P_VEH_ID", prmUnit.getAttVehicle().getAttVehicleId())
				.addValue("P_UNIT_LICENSE_PLATE", prmUnit.getAttLicensePlate())
				.addValue("P_UNIT_COLOR", prmUnit.getAttColor())
				.addValue("P_UNIT_MILEAGE", prmUnit.getAttMileage())
				.addValue("P_UNIT_DATE_ENTRY", prmUnit.getAttDateEntry())
				.addValue("P_UNIT_CONDITION", prmUnit.getAttCondition())
				.addValue("P_UNIT_STATUS", prmUnit.getAttStatus());
	}

	/**
	 * Convierte el resultado de un procedimiento almacenado de Oracle en un objeto
	 * {@link clsUnit}.
	 * <p>
	 * El {@link Map} de entrada corresponde a los parámetros de salida retornados
	 * por {@link org.springframework.jdbc.core.simple.SimpleJdbcCall}.
	 * </p>
	 *
	 * @param prmRow {@link Map} con las columnas y valores retornados por Oracle,
	 *               donde la clave es el nombre del parámetro y el valor es de tipo
	 *               {@link Object}
	 * @return objeto de tipo {@link clsUnit} con los datos mapeados
	 */
	private clsUnit opToObject(ResultSet prmRow) throws SQLException {
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
		return (rs, rowNum) -> opToObject(rs);
	}

	/**
	 * Convierte un entero en un parámetro para Oracle.
	 *
	 * @param prmId Id de la unidad
	 * @return parámetros compatibles con el procedure
	 */
	private MapSqlParameterSource opToId(int prmId){
		return new MapSqlParameterSource().addValue("P_UNIT_ID", prmId);
	}
	// endregion

	// region PROCEDURES
	public void opRegisterUnit(clsUnit prmUnit){
		try {
			attSpRegisterUnit.execute(opToParams(prmUnit));
		} catch (Exception e) {
			throw new excDatabaseException(e.getMessage());
		}
	}
	
	public void opUpdateUnit(clsUnit prmUnit){
		try {
			attSpUpdateUnit.execute(opToParams(prmUnit));
		} catch (Exception e) {
			throw new excDatabaseException(e.getMessage());
		}
	}
	
	public void opUpdateUnitStatus(int prmId, String prmStatus){
		MapSqlParameterSource varParams = new MapSqlParameterSource();
		varParams.addValue("P_UNIT_ID", prmId);
		varParams.addValue("P_UNIT_STATUS", prmStatus);
		try {
			attSpUpdateUnitStatus.execute(varParams);
		} catch (Exception e) {
			throw new excDatabaseException(e.getMessage());
		}
	}
	
	public boolean opUnitExist(int prmId){
		try {
			Boolean varResult = attFnUnitExist.executeFunction(Boolean.class,opToId(prmId));
			return Boolean.TRUE.equals(varResult);
		} catch (Exception e) {
			throw new excDatabaseException(e.getMessage());
		}
	}
	
	public clsUnit opGetUnitById(int prmId){
		try {
			Map<String, Object> varResult = attFnGetUnitById.execute(opToId(prmId));
			@SuppressWarnings("unchecked")
			List<clsUnit> varList = (List<clsUnit>) varResult.get("return");
			if (varList == null || varList.isEmpty()) return null;
			return varList.get(0);
		} catch (Exception e) {
			throw new excDatabaseException(e.getMessage());
		}
	}
	
	public List<clsUnit> opGetAllUnits(){
		try {
			Map<String, Object> varResult = attFnGetAllUnits.execute();
			@SuppressWarnings("unchecked")
			List<clsUnit> varUnite = (List<clsUnit>) varResult.get("return");
			return varUnite != null? varUnite: List.of();
		} catch (Exception e) {
			throw new excDatabaseException(e.getMessage());
		}
	}
	
	public List<clsUnit> opGetUnitsByStatus(String prmStatus){
		try {
			MapSqlParameterSource varParams = new MapSqlParameterSource();
			varParams.addValue("P_UNIT_STATUS", prmStatus);
			Map<String, Object> varResult = attFnGetUnitsByStatus.execute(varParams);
			@SuppressWarnings("unchecked")
			List<clsUnit> varUnite = (List<clsUnit>) varResult.get("return");
			return varUnite != null? varUnite: List.of();
		} catch (Exception e) {
			throw new excDatabaseException(e.getMessage());
		}
	}
	// endregion

}