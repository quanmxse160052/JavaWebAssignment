package quanmx.registration;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import quanmx.utils.DBHelper;

/**
 *
 * @author Dell
 */
public class RegistrationDAO implements Serializable {

    public RegistrationDTO getUserInforByUsername(String username) throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        RegistrationDTO dto = null;
        try {
            //1. make connection
            con = DBHelper.makeConnection();
            //2. sql query
            String sql = "Select username, password, lastname, isAdmin "
                    + "From Registration "
                    + "Where username=?";
            //3. prepare statement
            stm = con.prepareStatement(sql);
            stm.setString(1, username);
            //4. execute
            rs = stm.executeQuery();
            //5. process result
            if (rs.next()) {
                String fullName = rs.getString("lastname");
                String password = rs.getString("password");
                boolean isAdmin = rs.getBoolean("isAdmin");

                dto = new RegistrationDTO(username, password, fullName, isAdmin);
            }
        } finally {
            if (con != null) {
                con.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return dto;
    }

    public boolean createNewAccount(RegistrationDTO dto) throws SQLException, ClassNotFoundException, NamingException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            //1. make connection
            connection = DBHelper.makeConnection();
            //2. sql string
            String sql = "Insert Into Registration"
                    + "(username, password, lastname, isAdmin) "
                    + "Values(?, ?, ?, ?)";
            //3. prepare statement
            stm = connection.prepareStatement(sql);
            stm.setString(1, dto.getUsername());
            stm.setString(2, dto.getPassword());
            stm.setString(3, dto.getFullName());
            stm.setBoolean(4, dto.isRole());
            //4. execute
            int affectedRows = stm.executeUpdate();
            //5. process result
            if (affectedRows > 0) {
                result = true;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    public RegistrationDTO checkLogin(String username, String password) throws SQLException, ClassNotFoundException, NamingException {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        RegistrationDTO result = null;
        try {
            //1. make Connection
            connection = DBHelper.makeConnection();
            if (connection != null) {
                //2. create SQL String
                String sql = "Select username, lastname, isAdmin "
                        + "From Registration "
                        + "Where username = ? "
                        + "And password = ?";
                //3. create Statement Object
                stm = connection.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                //4. execute query
                rs = stm.executeQuery();
                //5. process result
                if (rs.next()) {
                    String fullName = rs.getString("lastname");
                    boolean isAdmin = rs.getBoolean("isAdmin");
                    result = new RegistrationDTO(username, null, fullName, isAdmin);
                }
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
        return result;
    }
    private List<RegistrationDTO> accounts;

    public List<RegistrationDTO> getAccounts() {
        return accounts;
    }

    public boolean deleteByUsername(String username)
            throws SQLException, ClassNotFoundException, NamingException {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            //1. make Connection
            connection = DBHelper.makeConnection();
            if (connection != null) {
                //2. create SQL String
                String sql = "Delete From Registration "
                        + "Where username = ?";
                //3. create Statement Object
                stm = connection.prepareStatement(sql);
                stm.setString(1, username);
                //4. execute update
                int affectedRows = stm.executeUpdate();
                //5. process result
                if (affectedRows > 0) {
                    result = true;
                }
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
        return result;
    }

    public boolean updateUserInfor(String username, String newPassword, String newRole) throws ClassNotFoundException, SQLException, NamingException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement stm = null;
        try {

            //1. make connection
            connection = DBHelper.makeConnection();
            //2. query string
            String sql = "Update Registration "
                    + "Set "
                    + "password = ?, "
                    + "isAdmin = ? "
                    + "Where username = ? ";
            //3. prepare statement
            stm = connection.prepareStatement(sql);
            stm.setString(1, newPassword);
            if (newRole == null) {
                stm.setBoolean(2, false);
            } else {
                stm.setBoolean(2, true);
            }
            stm.setString(3, username);
            //4. execute
            int affectedRow = stm.executeUpdate();
            //5. process result
            if (affectedRow > 0) {
                result = true;
            }

        } finally {
            if (stm != null) {
                stm.close();
            }
            if (connection != null) {
                connection.close();
            }

        }
        return result;
    }

    public void searchLastName(String searchValue) throws SQLException, ClassNotFoundException, NamingException {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. make Connection
            connection = DBHelper.makeConnection();
            if (connection != null) {
                //2. create SQL String
                String sql = "Select username, password, lastname, isAdmin "
                        + "From Registration "
                        + "Where lastname Like ?";
                //3. create Statement Object
                stm = connection.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                //4. execute query
                rs = stm.executeQuery();
                //5. process result
                while (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String lastname = rs.getString("lastname");
                    boolean role = rs.getBoolean("isAdmin");
                    RegistrationDTO dto = new RegistrationDTO(username, password, lastname, role);
                    if (this.accounts == null) {
                        this.accounts = new ArrayList<>();
                    } //end account is initialized
                    //account has existed
                    this.accounts.add(dto);
                } //end traverse ResultSet
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
