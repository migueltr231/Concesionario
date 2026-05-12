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

import edu.unicauca.dsantiago135.concesionaria.Model.clsCustomer;

@Repository
public class CustomerRepository {

    // region ATTRIBUTES
    private final JdbcTemplate attJdbcTemplate;

    private final SimpleJdbcCall attSpRegisterCustomer;
    private final SimpleJdbcCall attSpUpdateCustomer;
    private final SimpleJdbcCall attSpDisableCustomer;
    private final SimpleJdbcCall attSpGetCustomerById;
    private final SimpleJdbcCall attSpGetAllCustomers;
    // endregion

    // region CONSTRUCTOR
    public CustomerRepository(JdbcTemplate prmJdbcTemplate) {

        this.attJdbcTemplate = prmJdbcTemplate;

        this.attSpRegisterCustomer = new SimpleJdbcCall(attJdbcTemplate).withCatalogName("PKG_CUSTOMER").withProcedureName("SP_REGISTER_CUSTOMER");
        this.attSpUpdateCustomer = new SimpleJdbcCall(attJdbcTemplate).withCatalogName("PKG_CUSTOMER").withProcedureName("SP_UPDATE_CUSTOMER");
        this.attSpDisableCustomer = new SimpleJdbcCall(attJdbcTemplate).withCatalogName("PKG_CUSTOMER").withProcedureName("SP_DISABLE_CUSTOMER");
        this.attSpGetCustomerById = new SimpleJdbcCall(attJdbcTemplate).withCatalogName("PKG_CUSTOMER").withProcedureName("SP_GET_CUSTOMER_BY_ID").returningResultSet("P_CURSOR", opCustomerRowMapper());
        this.attSpGetAllCustomers = new SimpleJdbcCall(attJdbcTemplate).withCatalogName("PKG_CUSTOMER").withProcedureName("SP_GET_ALL_CUSTOMERS").returningResultSet("P_CURSOR", opCustomerRowMapper());
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
            .addValue("P_CUS_ID",prmCustomer.getAttCustomerId())
            .addValue("P_CUS_NAME",prmCustomer.getAttName())
            .addValue("P_CUS_PHONE",prmCustomer.getAttPhone())
            .addValue("P_CUS_EMAIL",prmCustomer.getAttEmail())
            .addValue("P_CUS_STATE",prmCustomer.getAttState());
}
    /**
     * Convierte el registro o fila de un cursor Oracle
     * en un objeto {@link clsCustomer}.
     *
     * @param prmRs fila actual obtenida desde Oracle
     * @return objeto cliente construido desde la fila
     * @throws SQLException error al leer columnas
     */
    private clsCustomer opRowToCustomer(ResultSet prmRs)throws SQLException {
        clsCustomer varCustomer =new clsCustomer();
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

    return (rs, rowNum) ->opRowToCustomer(rs);
    }
// endregion

    // region PROCEDURES
    public clsCustomer opGetCustomerBy(int prmId) {
        MapSqlParameterSource varParams = new MapSqlParameterSource().addValue("P_CUS_ID", prmId);
        
        Map<String, Object> varResult = attSpGetCustomerById.execute(varParams);
        
        @SuppressWarnings("unchecked")
        List<clsCustomer> varCustomers = (List<clsCustomer>) varResult.get("P_CURSOR");
        
        if (varCustomers == null || varCustomers.isEmpty()) return null;
        
        return varCustomers.get(0);
    }
    public List<clsCustomer> opGetAllCustomers() {

        Map<String, Object> varResult = attSpGetAllCustomers.execute(new MapSqlParameterSource());

        @SuppressWarnings("unchecked")
        List<clsCustomer> varCustomers = (List<clsCustomer>) varResult.get("P_CURSOR");

        return varCustomers != null ? varCustomers : List.of();
    }
    // endregion

}