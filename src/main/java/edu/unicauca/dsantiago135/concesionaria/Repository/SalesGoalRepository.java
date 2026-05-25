package edu.unicauca.dsantiago135.concesionaria.Repository;

import java.util.List;
import java.util.Map;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import edu.unicauca.dsantiago135.concesionaria.Error.excDatabaseException;
import edu.unicauca.dsantiago135.concesionaria.Model.clsDealership;
import edu.unicauca.dsantiago135.concesionaria.Model.clsEmployee;
import edu.unicauca.dsantiago135.concesionaria.Model.clsSalesGoal;

@Repository
public class SalesGoalRepository {

	// region ATTRIBUTES
	private final JdbcTemplate attJdbcTemplate;

	private static final String attPkg = "PKG_SALESGOAL";
	private final SimpleJdbcCall attSpRegisterSalesGoal;
	private final SimpleJdbcCall attSpUpdateSalesGoal;
	private final SimpleJdbcCall attSpDisableSalesGoal;
	private final SimpleJdbcCall attSpCompleteSalesGoal;
	private final SimpleJdbcCall attFnSalesGoalExist;
	private final SimpleJdbcCall attFnGetSalesGoalById;
	private final SimpleJdbcCall attFnGetAllSalesGoals;
	private final SimpleJdbcCall attFnGetSalesGoalsByState;
	// endregion

	// region CONSTRUCTOR
	public SalesGoalRepository(JdbcTemplate prmJdbcTemplate) {
		// Conexion
		this.attJdbcTemplate = prmJdbcTemplate;
		// Calls to procedures
		this.attSpRegisterSalesGoal = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withProcedureName("SP_REGISTER_SALESGOAL");
		this.attSpUpdateSalesGoal = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withProcedureName("SP_UPDATE_SALESGOAL");
		this.attSpDisableSalesGoal = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withProcedureName("SP_DISABLE_SALESGOAL");
		this.attSpCompleteSalesGoal = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withProcedureName("SP_COMPLETE_SALESGOAL");
		this.attFnSalesGoalExist = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withFunctionName("FN_SALESGOAL_EXIST");
		this.attFnGetSalesGoalById = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withFunctionName("FN_GET_SALESGOAL_BY_ID");
		this.attFnGetSalesGoalsByState = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withFunctionName("FN_GET_SALESGOALS_BY_STATE").returningResultSet("return",opSalesGoalRowMapper());
		this.attFnGetAllSalesGoals = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withFunctionName("FN_GET_ALL_SALESGOALS").returningResultSet("return",opSalesGoalRowMapper());
	}
	// endregion

	// region MAPPING
	/**
	 * Convierte un objeto {@link clsSalesGoal} en un {@link MapSqlParameterSource}
	 * listo para ser enviado como parámetros a un procedimiento almacenado de
	 * Oracle.
	 *
	 * @param prmSalesGoal objeto de tipo {@link clsSalesGoal} con los datos de la
	 *                     meta de ventas
	 * @return {@link MapSqlParameterSource} con los parámetros mapeados para Oracle
	 */
	private MapSqlParameterSource opToParams(clsSalesGoal prmSalesGoal) {
		return new MapSqlParameterSource()
				.addValue("P_SGL_ID", prmSalesGoal.getAttSalesGoalId())
				.addValue("P_EMP_ID", prmSalesGoal.getAttEmployee().getAttEmployeeId())
				.addValue("P_DEA_ID", prmSalesGoal.getAttDealership().getAttDealershipId())
				.addValue("P_SGL_GOAL_TYPE", prmSalesGoal.getAttGoalType())
				.addValue("P_SGL_TARGET_VALUE", prmSalesGoal.getAttTargetValue())
				.addValue("P_SGL_START_DATE", prmSalesGoal.getAttStartDate())
				.addValue("P_SGL_END_DATE", prmSalesGoal.getAttEndDate())
				.addValue("P_SGL_STATE", prmSalesGoal.getAttState());
	}

	/**
	 * Convierte el resultado de un procedimiento almacenado de Oracle en un objeto
	 * {@link clsSalesGoal}.
	 * <p>
	 * El {@link Map} de entrada corresponde a los parámetros de salida retornados
	 * por {@link org.springframework.jdbc.core.simple.SimpleJdbcCall}.
	 * </p>
	 *
	 * @param prmRow {@link Map} con las columnas y valores retornados por Oracle,
	 *               donde la clave es el nombre del parámetro y el valor es de tipo
	 *               {@link Object}
	 * @return objeto de tipo {@link clsSalesGoal} con los datos mapeados
	 */
	private clsSalesGoal opToObject(ResultSet prmRow) throws SQLException {
		clsSalesGoal varSalesGoal = new clsSalesGoal();
		varSalesGoal.setAttSalesGoalId(prmRow.getInt("SGL_ID"));

		clsEmployee varEmployee = new clsEmployee();
		varEmployee.setAttEmployeeId(prmRow.getInt("EMP_ID"));
		varSalesGoal.setAttEmployee(varEmployee);
		clsDealership varDealership = new clsDealership();
		varDealership.setAttDealershipId(prmRow.getInt("DEA_ID"));
		varSalesGoal.setAttDealership(varDealership);

		varSalesGoal.setAttGoalType(prmRow.getString("SGL_GOAL_TYPE"));
		varSalesGoal.setAttTargetValue(prmRow.getDouble("SGL_TARGET_VALUE"));
		varSalesGoal.setAttStartDate(prmRow.getDate("SGL_START_DATE"));
		varSalesGoal.setAttEndDate(prmRow.getDate("SGL_END_DATE"));
		varSalesGoal.setAttState(prmRow.getString("SGL_STATE"));
		return varSalesGoal;
	}

	/**
	 * Sobrecarga para definir cómo convertir filas del cursor Oracle en objetos
	 * {@link clsSalesGoal}.
	 *
	 * @return mapper reutilizable para consultas
	 */
	private RowMapper<clsSalesGoal> opSalesGoalRowMapper() {
		return (rs, rowNum) -> opToObject(rs);
	}

	/**
	 * Convierte un entero en un parámetro para Oracle.
	 *
	 * @param prmId Id de la meta
	 * @return parámetros compatibles con el procedure
	 */
	private MapSqlParameterSource opToId(int prmId){
		return new MapSqlParameterSource().addValue("P_SGL_ID", prmId);
	}
	// endregion

	// region PROCEDURES
	public int opRegisterSalesGoal(clsSalesGoal prmSalesGoal){
		Map<String, Object> varResult = null;
		try {
			varResult = attSpRegisterSalesGoal.execute(opToParams(prmSalesGoal));
		} catch (excDatabaseException e) {
			throw new excDatabaseException(e.getMessage());
		}
		Number varId = (Number) varResult.get("P_SGL_ID");
		return varId.intValue();
	}
	
	public void opUpdateSalesGoal(clsSalesGoal prmSalesGoal){
		try {
			attSpUpdateSalesGoal.execute(opToParams(prmSalesGoal));
		} catch (excDatabaseException e) {
			throw new excDatabaseException(e.getMessage());
		}
	}
	
	public void opDisableSalesGoal(int prmId){
		try {
			attSpDisableSalesGoal.execute(opToId(prmId));
		} catch (excDatabaseException e) {
			throw new excDatabaseException(e.getMessage());
		}
	}
	
	public void opCompleteSalesGoal(int prmId){
		try {
			attSpCompleteSalesGoal.execute(opToId(prmId));
		} catch (excDatabaseException e) {
			throw new excDatabaseException(e.getMessage());
		}
	}
	
	public boolean opSalesGoalExist(int prmId){
		try {
			Number varResult = attFnSalesGoalExist.executeFunction(Number.class, opToId(prmId));
			return varResult != null && varResult.intValue() == 1;
		} catch (excDatabaseException e) {
			throw new excDatabaseException(e.getMessage());
		}
	}
	
	public clsSalesGoal opGetSalesGoalById(int prmId){
		clsSalesGoal varSalesGoal = new clsSalesGoal();
		try {
			Map<String, Object> varResult = attFnGetSalesGoalById.execute(opToId(prmId));
			@SuppressWarnings("unchecked")
			Map<String, Object> varMapSalesGoal = (Map<String, Object>) varResult.get("return");
			if(varMapSalesGoal == null )return null;
			varSalesGoal.setAttSalesGoalId(((Number)(varMapSalesGoal.get("SGL_ID"))).intValue());
			clsDealership varDealership = new clsDealership();
			varDealership.setAttDealershipId(((Number)(varMapSalesGoal.get("DEA_ID"))).intValue());
			clsEmployee varEmployee = new clsEmployee();
			varEmployee.setAttEmployeeId(((Number)(varMapSalesGoal.get("EMP_ID"))).intValue());
			varSalesGoal.setAttDealership(varDealership);
			varSalesGoal.setAttEmployee(varEmployee);
			
			varSalesGoal.setAttStartDate((Date)(varMapSalesGoal.get("SGL_START_DATE")));
			varSalesGoal.setAttEndDate((Date)(varMapSalesGoal.get("SGL_END_DATE")));
			varSalesGoal.setAttGoalType((String)(varMapSalesGoal.get("SGL_GOAL_TYPE")));
			varSalesGoal.setAttState((String)(varMapSalesGoal.get("SGL_STATE")));
			varSalesGoal.setAttTargetValue(((Number)(varMapSalesGoal.get("SGL_TARGET_VALUE"))).doubleValue());
		} catch (excDatabaseException e) {
			throw new excDatabaseException(e.getMessage());
		}
		return varSalesGoal;
	}
	
	public List<clsSalesGoal> opGetAllSalesGoals(){
		try {
			Map<String, Object> varResult = attFnGetAllSalesGoals.execute();
			@SuppressWarnings("unchecked")
			List<clsSalesGoal> varSalesGoal = (List<clsSalesGoal>) varResult.get("return");
			return varSalesGoal != null? varSalesGoal: List.of();
		} catch (excDatabaseException e) {
			throw new excDatabaseException(e.getMessage());
		}
	}

	public List<clsSalesGoal> opGetSalesGoalsByState(String prmState){
		try {
			MapSqlParameterSource varParams = new MapSqlParameterSource();
			varParams.addValue("P_SGL_STATE", prmState);
			Map<String, Object> varResult = attFnGetSalesGoalsByState.execute(varParams);
			@SuppressWarnings("unchecked")
			List<clsSalesGoal> varSalesGoal = (List<clsSalesGoal>) varResult.get("return");
			return varSalesGoal != null? varSalesGoal: List.of();
		} catch (excDatabaseException e) {
			throw new excDatabaseException(e.getMessage());
		}
	}
	// endregion

}
