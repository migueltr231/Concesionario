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
import edu.unicauca.dsantiago135.concesionaria.Error.excDuplicateDataException;
import edu.unicauca.dsantiago135.concesionaria.Error.excNotFoundException;
import edu.unicauca.dsantiago135.concesionaria.Error.excOperationNotAllowedException;
import edu.unicauca.dsantiago135.concesionaria.Error.excValidationException;
import edu.unicauca.dsantiago135.concesionaria.Model.clsDealership;

@Repository
public class DealershipRepository {

	// region ATTRIBUTES
	private final JdbcTemplate attJdbcTemplate;

	private static final String attPkg = "PKG_DEALERSHIP";
	private final SimpleJdbcCall attSpRegisterDealership;
	private final SimpleJdbcCall attSpUpdateDealership;
	private final SimpleJdbcCall attSpDisableDealership;
	private final SimpleJdbcCall attFnDealershipExist;
	private final SimpleJdbcCall attFnGetDealershipById;
	private final SimpleJdbcCall attFnGetAllDealership;

	// endregion

	// region CONSTRUCTOR
	public DealershipRepository(JdbcTemplate prmJdbcTemplate) {
		// Conexion
		this.attJdbcTemplate = prmJdbcTemplate;
		// Calls to procedures
		this.attSpRegisterDealership = new SimpleJdbcCall(attJdbcTemplate). withCatalogName(attPkg)
				.withProcedureName("SP_REGISTER_DEALERSHIP");
		this.attSpUpdateDealership = new SimpleJdbcCall(attJdbcTemplate). withCatalogName(attPkg)
				.withProcedureName("SP_UPDATE_DEALERSHIP");
		this.attSpDisableDealership = new SimpleJdbcCall(attJdbcTemplate). withCatalogName(attPkg)
				.withProcedureName("SP_DISABLE_DEALERSHIP");
		this.attFnDealershipExist = new SimpleJdbcCall(attJdbcTemplate). withCatalogName(attPkg)
				.withFunctionName("FN_DEALERSHIP_EXIST");
		this.attFnGetDealershipById = new SimpleJdbcCall(attJdbcTemplate). withCatalogName(attPkg)
				.withFunctionName("FN_GET_DEALERSHIP_BY_ID");
		this.attFnGetAllDealership = new SimpleJdbcCall(attJdbcTemplate). withCatalogName(attPkg)
				.withFunctionName("FN_GET_ALL_DEALERSHIPS").returningResultSet("return", opDealershipRowMapper());
	}
	// endregion

	// region MAPPING
	/**
	 * Convierte un objeto {@link clsDealership} en un {@link MapSqlParameterSource}
	 * listo para ser enviado como parámetros a un procedimiento almacenado de
	 * Oracle.
	 *
	 * @param prmDealership objeto de tipo {@link clsDealership} con los datos de la
	 *                      concesionaria
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
	 * Convierte el resultado de un procedimiento almacenado de Oracle en un objeto
	 * {@link clsDealership}.
	 * <p>
	 * El {@link Map} de entrada corresponde a los parámetros de salida retornados
	 * por {@link org.springframework.jdbc.core.simple.SimpleJdbcCall}.
	 * </p>
	 *
	 * @param prmRow {@link Map} con las columnas y valores retornados por Oracle,
	 *               donde la clave es el nombre del parámetro y el valor es de tipo
	 *               {@link Object}
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
		return (rs, rowNum) -> opToObject(rs);
	}

	/**
	 * Convierte un entero en un parámetro para Oracle.
	 *
	 * @param prmId Id de la concesionaria
	 * @return parámetros compatibles con el procedure
	 */
	private MapSqlParameterSource opToId(int prmId){
		return new MapSqlParameterSource().addValue("P_DEA_ID", prmId);
	}
	// endregion

	// region PROCEDURES
	public void opRegisterDealership(clsDealership prmDealership)throws excDatabaseException, excDuplicateDataException, excValidationException{
		attSpRegisterDealership.execute(opToParams(prmDealership));
	}

	public void opUpdateDealership(clsDealership prmDealership)throws excDatabaseException, excNotFoundException, excValidationException{
		attSpUpdateDealership.execute(opToParams(prmDealership));
	}

	public void opDisableDealership(int prmId)throws excDatabaseException, excNotFoundException, excOperationNotAllowedException{
		attSpDisableDealership.execute(opToId(prmId));
	}

	public boolean opDealershipExist(int prmId)throws excDatabaseException{
		Boolean varResult = attFnDealershipExist.executeFunction(Boolean.class, opToId(prmId));
		return Boolean.TRUE.equals(varResult);
	}

	public clsDealership opGetDealershipById(int prmId)throws excDatabaseException, excNotFoundException{
		
		clsDealership varDealership = new  clsDealership();
		Map<String, Object> varResult = attFnGetDealershipById.execute(opToId(prmId));
		@SuppressWarnings("unchecked")
		Map<String, Object> varDealershipMap = (Map<String, Object>) varResult.get("return");
		if (varDealershipMap == null) {return null;}
		varDealership.setAttDealershipId(((Number) varDealershipMap.get("DEA_ID")).intValue());
		varDealership.setAttName((String) varDealershipMap.get("DEA_NAME"));
		varDealership.setAttPhone((String) varDealershipMap.get("DEA_PHONE"));
		varDealership.setAttAddress((String) varDealershipMap.get("DEA_ADDRESS"));
		varDealership.setAttState((String) varDealershipMap.get("DEA_STATE"));

		return varDealership;
	}

	public List<clsDealership> opGetAllDealership()throws excDatabaseException{
		Map<String, Object> varResult = attFnGetAllDealership.execute();
		@SuppressWarnings("unchecked")
		List<clsDealership> varDealerships = (List<clsDealership>) varResult.get("return");
		return varDealerships != null? varDealerships: List.of();
	}
	// endregion

}
