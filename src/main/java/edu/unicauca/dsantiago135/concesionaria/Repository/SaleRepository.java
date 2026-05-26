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
import edu.unicauca.dsantiago135.concesionaria.Model.clsCustomer;
import edu.unicauca.dsantiago135.concesionaria.Model.clsEmployee;
import edu.unicauca.dsantiago135.concesionaria.Model.clsSale;
import edu.unicauca.dsantiago135.concesionaria.Model.clsUnit;

@Repository
public class SaleRepository {

	// region ATTRIBUTES
	private final JdbcTemplate attJdbcTemplate;

	private static final String attPkg = "PKG_SALE";
	private final SimpleJdbcCall attSpRegisterSale;
	private final SimpleJdbcCall attSpRegisterReservation;
	private final SimpleJdbcCall attSpCompleteReservation;
	private final SimpleJdbcCall attSpCancelReservation;
	private final SimpleJdbcCall attFnSaleExist;
	private final SimpleJdbcCall attFnGetSaleById;
	private final SimpleJdbcCall attFnGetAllSales;
	private final SimpleJdbcCall attFnGetSalesByStatus;

	// endregion

	// region CONSTRUCTOR
	public SaleRepository(JdbcTemplate prmJdbcTemplate) {
		// Conexion
		this.attJdbcTemplate = prmJdbcTemplate;
		// Calls to procedures
		this.attSpRegisterSale = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withProcedureName("SP_REGISTER_SALE");
		this.attSpRegisterReservation = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withProcedureName("SP_REGISTER_RESERVATION");
		this.attSpCompleteReservation = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withProcedureName("SP_COMPLETE_RESERVATION");
		this.attSpCancelReservation = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withProcedureName("SP_CANCEL_RESERVATION");
		this.attFnSaleExist = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withFunctionName("FN_SALE_EXIST");
		this.attFnGetSaleById = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withFunctionName("FN_GET_SALE_BY_ID").returningResultSet("return",opSaleRowMapper());
		this.attFnGetSalesByStatus = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withFunctionName("FN_GET_SALES_BY_STATUS").returningResultSet("return",opSaleRowMapper());
		this.attFnGetAllSales = new SimpleJdbcCall(attJdbcTemplate).withCatalogName(attPkg)
				.withFunctionName("FN_GET_ALL_SALES").returningResultSet("return",opSaleRowMapper());
	}
	// endregion

	// region MAPPING
	/**
	 * Convierte un objeto {@link clsSale} en un {@link MapSqlParameterSource}
	 * listo para ser enviado como parámetros a un procedimiento almacenado de
	 * Oracle.
	 *
	 * @param prmSale objeto de tipo {@link clsSale} con los datos de la venta
	 * @return {@link MapSqlParameterSource} con los parámetros mapeados para Oracle
	 */
	private MapSqlParameterSource opToParams(clsSale prmSale) {
		return new MapSqlParameterSource()
				.addValue("P_SALE_ID", prmSale.getAttSaleId())
				.addValue("P_CUS_ID", prmSale.getAttCustomer().getAttCustomerId())
				.addValue("P_EMP_ID", prmSale.getAttEmployee().getAttEmployeeId())
				.addValue("P_UNIT_ID", prmSale.getAttUnit().getAttUnitId())
				.addValue("P_SALE_DATE_START", prmSale.getAttDateStart())
				.addValue("P_SALE_PRICE", prmSale.getAttPrice())
				.addValue("P_SALE_STATUS", prmSale.getAttStatus())
				.addValue("P_SALE_DATE_END", prmSale.getAttDateEnd());
	}

	/**
	 * Convierte el resultado de un procedimiento almacenado de Oracle en un objeto
	 * {@link clsSale}.
	 * <p>
	 * El {@link Map} de entrada corresponde a los parámetros de salida retornados
	 * por {@link org.springframework.jdbc.core.simple.SimpleJdbcCall}.
	 * </p>
	 *
	 * @param prmRow {@link Map} con las columnas y valores retornados por Oracle,
	 *               donde la clave es el nombre del parámetro y el valor es de tipo
	 *               {@link Object}
	 * @return objeto de tipo {@link clsSale} con los datos mapeados
	 */
	private clsSale opToObject(ResultSet prmRow) throws SQLException {
		clsSale varSale = new clsSale();

		varSale.setAttSaleId(prmRow.getInt("SALE_ID"));

		clsCustomer varCustomer = new clsCustomer();
		varCustomer.setAttCustomerId(prmRow.getInt("CUS_ID"));
		varSale.setAttCustomer(varCustomer);

		clsEmployee varEmployee = new clsEmployee();
		varEmployee.setAttEmployeeId(prmRow.getInt("EMP_ID"));
		varSale.setAttEmployee(varEmployee);

		clsUnit varUnit = new clsUnit();
		varUnit.setAttUnitId((prmRow.getInt("UNIT_ID")));
		varSale.setAttUnit(varUnit);

		varSale.setAttDateStart(prmRow.getDate("SALE_DATE_START"));
		varSale.setAttPrice(prmRow.getDouble("SALE_PRICE"));
		varSale.setAttStatus(prmRow.getString("SALE_STATUS"));
		varSale.setAttDateEnd(prmRow.getDate("SALE_DATE_END"));
		return varSale;
	}

	/**
	 * Sobrecarga para definir cómo convertir filas del cursor Oracle en objetos
	 * {@link clsSale}.
	 *
	 * @return mapper reutilizable para consultas
	 */
	private RowMapper<clsSale> opSaleRowMapper() {
		return (rs, rowNum) -> opToObject(rs);
	}

	/**
	 * Convierte un entero en un parámetro para Oracle.
	 *
	 * @param prmId Id de la venta
	 * @return parámetros compatibles con el procedure
	 */
	private MapSqlParameterSource opToId(int prmId){
		return new MapSqlParameterSource().addValue("P_SALE_ID", prmId);
	}
	// endregion

	// region PROCEDURES
	public int opRegisterSale(clsSale prmSale){
		Map<String, Object> varResult = null;
		try {
			varResult = attSpRegisterSale.execute(opToParams(prmSale));
		} catch (Exception e) {
			throw new excDatabaseException(e.getMessage());
		}
		Number varId = (Number) varResult.get("P_SALE_ID");
		return varId.intValue();
	}
	
	public int opRegisterReservation(clsSale prmSale){
		Map<String, Object> varResult = null;
		try {
			varResult = attSpRegisterReservation.execute(opToParams(prmSale));
		} catch (Exception e) {
			throw new excDatabaseException(e.getMessage());
		}
		Number varId = (Number) varResult.get("P_SALE_ID");
		return varId.intValue();
	}
	
	public void opCompleteReservation(int prmId){
		try {
			attSpCompleteReservation.execute(opToId(prmId));
		} catch (Exception e) {
			throw new excDatabaseException(e.getMessage());
		}
	}
	
	public void opCancelReservation(int prmId){
		try {
			attSpCancelReservation.execute(opToId(prmId));
		} catch (Exception e) {
			throw new excDatabaseException(e.getMessage());
		}
	}
	
	public boolean opSaleExist(int prmId){
		try {
			Boolean varResult = attFnSaleExist.executeFunction(Boolean.class,opToId(prmId));
			return Boolean.TRUE.equals(varResult);
		} catch (Exception e) {
			throw new excDatabaseException(e.getMessage());
		}
	}
	
	public clsSale opGetSaleById(int prmId){
		try {
			Map<String, Object> varResult = attFnGetSaleById.execute(opToId(prmId));
			@SuppressWarnings("unchecked")
			List<clsSale> varList = (List<clsSale>) varResult.get("return");
			if (varList == null || varList.isEmpty()) return null;
			return varList.get(0);
		} catch (Exception e) {
			throw new excDatabaseException(e.getMessage());
		}
}
	
	public List<clsSale> opGetAllSales(){
		try {
			Map<String, Object> varResult = attFnGetAllSales.execute();
			@SuppressWarnings("unchecked")
			List<clsSale> varSales = (List<clsSale>) varResult.get("return");
			return varSales != null? varSales: List.of();
		} catch (Exception e) {
			throw new excDatabaseException(e.getMessage());
		}
	}
	
	public List<clsSale> opGetSalesByStatus(String prmStatus){
		try {
			MapSqlParameterSource varParams = new MapSqlParameterSource();
			varParams.addValue("P_SALE_STATUS", prmStatus);
			Map<String, Object> varResult = attFnGetSalesByStatus.execute(varParams);
			@SuppressWarnings("unchecked")
			List<clsSale> varSales = (List<clsSale>) varResult.get("return");
			return varSales != null? varSales: List.of();
		} catch (Exception e) {
			throw new excDatabaseException(e.getMessage());
		}
	}
	// endregion

}