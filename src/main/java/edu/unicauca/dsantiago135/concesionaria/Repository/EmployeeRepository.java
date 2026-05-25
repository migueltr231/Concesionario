package edu.unicauca.dsantiago135.concesionaria.Repository;

import java.sql.Date;
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
import edu.unicauca.dsantiago135.concesionaria.Model.clsEmployee;

@Repository
public class EmployeeRepository {

	// region ATTRIBUTES
	private final JdbcTemplate attJdbcTemplate;

	private static final String attPkg = "PKG_EMPLOYEE";
	private final SimpleJdbcCall attSpRegisterEmployee;
	private final SimpleJdbcCall attSpUpdateEmployee;
	private final SimpleJdbcCall attSpDisableEmployee;
	private final SimpleJdbcCall attFnEmployeeExist;
	private final SimpleJdbcCall attFnGetEmployeeById;
	private final SimpleJdbcCall attFnGetEmployeesByDealership;
	private final SimpleJdbcCall attFnGetAllEmployees;
	private final SimpleJdbcCall attFnGetEmployeesAboveAvg;
	// endregion

	// region CONSTRUCTOR
	public EmployeeRepository(JdbcTemplate prmJdbcTemplate) {
		// Conexion
		this.attJdbcTemplate = prmJdbcTemplate;
		// Calls to procedures
		this.attSpRegisterEmployee = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withProcedureName("SP_REGISTER_EMPLOYEE");
		this.attSpUpdateEmployee = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withProcedureName("SP_UPDATE_EMPLOYEE");
		this.attSpDisableEmployee = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withProcedureName("SP_DISABLE_EMPLOYEE");
		this.attFnEmployeeExist = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withFunctionName("FN_EMPLOYEE_EXIST");
		this.attFnGetEmployeeById = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withFunctionName("FN_GET_EMPLOYEE_BY_ID");
		this.attFnGetEmployeesByDealership = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withFunctionName("FN_GET_EMPLOYEES_BY_DEALERSHIP").returningResultSet("return", opEmployeeRowMapper());
		this.attFnGetEmployeesAboveAvg = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withFunctionName("FN_GET_EMPLOYEES_ABOVE_AVG").returningResultSet("return", opEmployeeRowMapper());
		this.attFnGetAllEmployees = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withFunctionName("FN_GET_ALL_EMPLOYEES").returningResultSet("return", opEmployeeRowMapper());
	}
	// endregion

	// region MAPPING
	/**
	 * Convierte un objeto {@link clsEmployee} en un {@link MapSqlParameterSource}
	 * listo para ser enviado como parámetros a un procedimiento almacenado de
	 * Oracle.
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
	 * Convierte el resultado de un procedimiento almacenado de Oracle en un objeto
	 * {@link clsEmployee}.
	 * <p>
	 * El {@link Map} de entrada corresponde a los parámetros de salida retornados
	 * por {@link org.springframework.jdbc.core.simple.SimpleJdbcCall}.
	 * </p>
	 *
	 * @param prmRow {@link Map} con las columnas y valores retornados por Oracle,
	 *               donde la clave es el nombre del parámetro y el valor es de tipo
	 *               {@link Object}
	 * @return objeto de tipo {@link clsEmployee} con los datos mapeados
	 */
	private clsEmployee opToObject(ResultSet prmRow) throws SQLException {
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
		return (rs, rowNum) -> opToObject(rs);
	}
	
	/**
	 * Convierte un entero en un parámetro para Oracle.
	 *
	 * @param prmId Id del empleado
	 * @return parámetros compatibles con el procedure
	 */
	private MapSqlParameterSource opToId(int prmId){
		return new MapSqlParameterSource().addValue("P_EMP_ID", prmId);
	}
	// endregion

	// region PROCEDURES
	public void opRegisterEmployee(clsEmployee prmEmployee){
		try {
			attSpRegisterEmployee.execute(opToParams(prmEmployee));
		} catch (excDatabaseException e) {
			throw new excDatabaseException(e.getMessage());
		}
	}

	public void opUpdateEmployee(clsEmployee prmEmployee){
		try {
			attSpUpdateEmployee.execute(opToParams(prmEmployee));
		} catch (excDatabaseException e) {
			throw new excDatabaseException(e.getMessage());
		}
	}
	
	public void opDisableEmployee(int prmId){
		try {
			attSpDisableEmployee.execute(opToId(prmId));
		} catch (excDatabaseException e) {
			throw new excDatabaseException(e.getMessage());
		}
	}

	public boolean opEmployeeExist(int prmId){
		try {
			Number varResult = attFnEmployeeExist.executeFunction(Number.class, opToId(prmId));
			return varResult != null && varResult.intValue() == 1;
		} catch (excDatabaseException e) {
			throw new excDatabaseException(e.getMessage());
		}
	}

	public clsEmployee opGetEmployeeById(int prmId){
		try {
			clsEmployee varEmployee = new clsEmployee();
			Map<String, Object> varResult = attFnGetEmployeeById.execute(opToId(prmId));
			@SuppressWarnings("unchecked")
			Map<String, Object> varEmployeeData = (Map<String, Object>) varResult.get("return");
			if(varEmployeeData == null )return null;
			varEmployee.setAttEmployeeId(((Number)varEmployeeData.get("EMP_ID")).intValue());
			clsDealership varDealership = new clsDealership();
			varDealership.setAttDealershipId(((Number)varEmployeeData.get("DEA_ID")).intValue());
			varEmployee.setAttDealership(varDealership);
			varEmployee.setAttName((String)varEmployeeData.get("EMP_NAME"));
			varEmployee.setAttPhone((String)varEmployeeData.get("EMP_PHONE"));
			varEmployee.setAttRole((String)varEmployeeData.get("EMP_ROLE"));
			varEmployee.setAttSalary(((Number)varEmployeeData.get("EMP_SALARY")).doubleValue());
			varEmployee.setAttState((String)varEmployeeData.get("EMP_STATE"));
			varEmployee.setAttHireDate((Date)varEmployeeData.get("EMP_HIRE_DATE"));
			return varEmployee;
		} catch (excDatabaseException e) {
			throw new excDatabaseException(e.getMessage());
		}
	}

	public List<clsEmployee> opGetEmployeesByDealership(int prmDealershipId){
		try {
			MapSqlParameterSource varParams = new MapSqlParameterSource();
			varParams.addValue("P_DEA_ID", prmDealershipId);
			Map<String, Object> varResult = attFnGetEmployeesByDealership.execute(varParams);
			@SuppressWarnings("unchecked")
			List<clsEmployee> varEmployee = (List<clsEmployee>) varResult.get("return");
			return varEmployee != null? varEmployee: List.of();
		} catch (excDatabaseException e) {
			throw new excDatabaseException(e.getMessage());
		}
	}

	public List<clsEmployee> opGetAllEmployees(){
		try {
			Map<String, Object> varResult = attFnGetAllEmployees.execute();
			@SuppressWarnings("unchecked")
			List<clsEmployee> varEmployee = (List<clsEmployee>) varResult.get("return");
			return varEmployee != null? varEmployee: List.of();
		} catch (excDatabaseException e) {
			throw new excDatabaseException(e.getMessage());
		}
	}

	public List<clsEmployee> opGetEmployeesAboveAvg(){
		try {
			Map<String, Object> varResult = attFnGetEmployeesAboveAvg.execute();
			@SuppressWarnings("unchecked")
			List<clsEmployee> varEmployee = (List<clsEmployee>) varResult.get("return");
			return varEmployee != null? varEmployee: List.of();
		} catch (excDatabaseException e) {
			throw new excDatabaseException(e.getMessage());
		}
	}
	// endregion
}