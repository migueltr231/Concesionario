package edu.unicauca.dsantiago135.concesionaria.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import edu.unicauca.dsantiago135.concesionaria.Error.excDatabaseException;
import edu.unicauca.dsantiago135.concesionaria.Model.clsCustomer;

@Repository
public class CustomerRepository {

	// region ATTRIBUTES
	private final JdbcTemplate attJdbcTemplate;

	private static final String attPkg = "PKG_CUSTOMER";
	private final SimpleJdbcCall attSpRegisterCustomer;
	private final SimpleJdbcCall attSpUpdateCustomer;
	private final SimpleJdbcCall attSpDisableCustomer;
	private final SimpleJdbcCall attFnCustomerExist;
	private final SimpleJdbcCall attFnGetCustomerById;
	private final SimpleJdbcCall attFnGetAllCustomers;
	// endregion

	// region CONSTRUCTOR
	public CustomerRepository(JdbcTemplate prmJdbcTemplate) {

		this.attJdbcTemplate = prmJdbcTemplate;

		this.attSpRegisterCustomer = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withProcedureName("SP_REGISTER_CUSTOMER");
		this.attSpUpdateCustomer = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withProcedureName("SP_UPDATE_CUSTOMER");
		this.attSpDisableCustomer = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withProcedureName("SP_DISABLE_CUSTOMER");
		this.attFnCustomerExist = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withFunctionName("FN_CUSTOMER_EXIST");
		this.attFnGetCustomerById = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withFunctionName("FN_GET_CUSTOMER_BY_ID");
		this.attFnGetAllCustomers = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withFunctionName("FN_GET_ALL_CUSTOMERS").returningResultSet("return", opCustomerRowMapper());
	}
	// endregion

	// region MAPPING
	/**
	 * Convierte un {@link clsCustomer} en parámetros para Oracle.
	 *
	 * @param prmCustomer cliente a convertir
	 * @return parámetros compatibles con el procedure
	 */
	private MapSqlParameterSource opToParams(clsCustomer prmCustomer) {

		return new MapSqlParameterSource()
				.addValue("P_CUS_ID", prmCustomer.getAttCustomerId())
				.addValue("P_CUS_NAME", prmCustomer.getAttName())
				.addValue("P_CUS_PHONE", prmCustomer.getAttPhone())
				.addValue("P_CUS_EMAIL", prmCustomer.getAttEmail())
				.addValue("P_CUS_STATE", prmCustomer.getAttState());
	}

	/**
	 * Convierte el registro o fila de un cursor Oracle
	 * en un objeto {@link clsCustomer}.
	 *
	 * @param prmRs fila actual obtenida desde Oracle
	 * @return objeto cliente construido desde la fila
	 * @throws SQLException error al leer columnas
	 */
	private clsCustomer opRowToCustomer(ResultSet prmRs) throws SQLException {
		clsCustomer varCustomer = new clsCustomer();
		varCustomer.setAttCustomerId(prmRs.getInt("CUS_ID"));
		varCustomer.setAttName(prmRs.getString("CUS_NAME"));
		varCustomer.setAttPhone(prmRs.getString("CUS_PHONE"));
		varCustomer.setAttEmail(prmRs.getString("CUS_EMAIL"));
		varCustomer.setAttState(prmRs.getString("CUS_STATE"));

		return varCustomer;
	}

	/**
	 * Sobrecarga para definir cómo convertir filas del cursor Oracle en objetos
	 * {@link clsCustomer}.
	 *
	 * @return mapper reutilizable para consultas de clientes
	 */
	private RowMapper<clsCustomer> opCustomerRowMapper() {

		return (rs, rowNum) -> opRowToCustomer(rs);
	}
	
	/**
	 * Convierte un entero en un parámetro para Oracle.
	 *
	 * @param prmId Id del cliente
	 * @return parámetros compatibles con el procedure
	 */
	private MapSqlParameterSource opToId(int prmId){
		return new MapSqlParameterSource().addValue("P_CUS_ID", prmId);
	}
	// endregion

	// region PROCEDURES
	public void opRegisterCustomer(clsCustomer prmCustomer) throws excDatabaseException{
		try {
			attSpRegisterCustomer.execute(opToParams(prmCustomer));
		} catch (Exception e) {
			throw new excDatabaseException(e.getMessage());
		}
	}

	public void opUpdateCustomer(clsCustomer prmCustomer)throws excDatabaseException{
		try {
			attSpUpdateCustomer.execute(opToParams(prmCustomer));
		} catch (Exception e) {
			throw new excDatabaseException(e.getMessage());
		}
	}

	public void opDisableCustomer(int prmId)throws excDatabaseException{
		try {
			attSpDisableCustomer.execute(opToId(prmId));
		} catch (Exception e) {
			throw new excDatabaseException(e.getMessage());
		}
	}

	public boolean opCustomerExist(int prmId)throws excDatabaseException{
		try {
			Boolean varResult = attFnCustomerExist.executeFunction(Boolean.class,opToId(prmId));
			return Boolean.TRUE.equals(varResult);
		} catch (Exception e) {
			throw new excDatabaseException(e.getMessage());
		}
	}

	public clsCustomer opGetCustomerById(int prmId)throws excDatabaseException{
		try {
		Map<String, Object> varResult =attFnGetCustomerById.execute(opToId(prmId));
		@SuppressWarnings("unchecked")
		Map<String, Object> varCustomerMap = (Map<String, Object>) varResult.get("return");
		if(varCustomerMap == null )return null;
		clsCustomer varCustomer = new clsCustomer();
		varCustomer.setAttCustomerId(((Number) varCustomerMap.get("CUS_ID")).intValue());
		varCustomer.setAttName((String) varCustomerMap.get("CUS_NAME"));
		varCustomer.setAttPhone((String) varCustomerMap.get("CUS_PHONE"));
		varCustomer.setAttEmail((String) varCustomerMap.get("CUS_EMAIL"));
		varCustomer.setAttState((String) varCustomerMap.get("CUS_STATE"));
		return varCustomer;
		} catch (Exception e) {
			throw new excDatabaseException(e.getMessage());
		}
	}

	public List<clsCustomer> opGetAllCustomers() throws excDatabaseException{
		try {
			Map<String, Object> varResult = attFnGetAllCustomers.execute();
			@SuppressWarnings("unchecked")
			List<clsCustomer> varCustomers = (List<clsCustomer>) varResult.get("return");
			return varCustomers != null? varCustomers: List.of();
		} catch (Exception e) {
			throw new excDatabaseException(e.getMessage());
		}
	}
	// endregion

}