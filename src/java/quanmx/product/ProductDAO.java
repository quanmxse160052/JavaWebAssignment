package quanmx.product;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import quanmx.utils.DBHelper;

public class ProductDAO implements Serializable{

    private List<ProductDTO> fullProductList;

    public List<ProductDTO> getFullProductList() {
        return fullProductList;
    }

    public ProductDTO getProductBySku(int sku) throws SQLException, ClassNotFoundException, NamingException {
        ProductDTO product = null;
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. make connection
            connection = DBHelper.makeConnection();
            //2. query string
            String sql = "Select sku, name, description, price "
                    + "From product "
                    + "Where sku = ?";
            //3. prepare statement
            stm = connection.prepareStatement(sql);
            stm.setInt(1, sku);
            //4. execute
            rs = stm.executeQuery();
            //5. process rs
            while (rs.next()) {
                int id = rs.getInt("sku");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getFloat("price");
                product = new ProductDTO(id, name, description, price);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                rs.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return product;
    }

    public void loadAllProduct() throws SQLException, ClassNotFoundException, NamingException {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. make connection
            connection = DBHelper.makeConnection();
            //2. query String
            String sql = "Select sku, name, description, price "
                    + "From product ";
            //3. prepare statement
            stm = connection.prepareStatement(sql);
            //4. execute
            rs = stm.executeQuery();
            //5. process rs
            while (rs.next()) {
                int sku = rs.getInt("sku");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getFloat("price");
                ProductDTO product = new ProductDTO(sku, name, description, price);
                if (fullProductList == null) {
                    fullProductList = new ArrayList<>();
                }
                fullProductList.add(product);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
