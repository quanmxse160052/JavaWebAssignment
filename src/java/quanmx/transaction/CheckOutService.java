package quanmx.transaction;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import quanmx.cart.CartProduct;
import quanmx.order.OrderDAO;
import quanmx.orderdetail.OrderDetailDAO;
import quanmx.utils.DBHelper;

/**
 *
 * @author Dell
 */
public class CheckOutService implements Serializable {

    private final Logger LOGGER = Logger.getLogger(CheckOutService.class);
    private List<CartProduct> productList;

    public CheckOutService(List<CartProduct> productList) {
        this.productList = productList;
    }

    public boolean execute() throws SQLException, ClassNotFoundException, NamingException {
        boolean completed = false;
        Connection connection = null;
        try {
            connection = DBHelper.makeConnection();
            connection.setAutoCommit(false);
            //call DAO: insert order to Order
            OrderDAO dao = new OrderDAO();
            int lastId = dao.insertNewOrder(productList, connection);
            //check last identity of above action
            if (lastId > 0) {
                //call DAO: insert products to OrderDetail
                OrderDetailDAO orderDetailDao = new OrderDetailDAO();
                int insertedRow = orderDetailDao.insertIntoOrderDetail(productList, lastId, connection);
                if (insertedRow == productList.size()) {
                    completed = true;
                }
            }
            if (completed) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException | ClassNotFoundException | NamingException ex) {
            if (connection != null) {
                connection.rollback();
                LOGGER.error("CheckOutTransaction " + ex.getMessage());
            }
            throw ex;

        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return completed;
    }

}
