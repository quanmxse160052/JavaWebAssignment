package quanmx.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import quanmx.cart.CartObject;
import quanmx.cart.CartProduct;
import quanmx.product.ProductDAO;
import quanmx.transaction.CheckOutService;
import quanmx.utils.AppConstants;

/**
 *
 * @author Dell
 */
public class CheckOutController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CheckOutController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITEMAPS");
        String url = AppConstants.CheckOutFeatures.VIEW_CART_PAGE;
        try {
            //1. get session
            HttpSession session = request.getSession(false);
            //2. check session has existed
            if (session != null) {
                // 3. get cart in session
                CartObject cart = (CartObject) session.getAttribute("CART");
                //4. check cart has existed
                if (cart != null) {
                    //5. check items in cart has existed
                    Map<Integer, Integer> items = cart.getItems();
                    //6. check items has existed
                    if (items != null) {
                        //7. get product list
                        List<CartProduct> productList = new ArrayList<>();
                        ProductDAO proDao = new ProductDAO();
                        for (int sku : items.keySet()) {
                            CartProduct product = new CartProduct(proDao.getProductBySku(sku), items.get(sku));
                            productList.add(product);
                        }
                        //Create a transaction
                        CheckOutService transaction = new CheckOutService(productList);
                        boolean result = transaction.execute();
                        if (result) {
                            session.removeAttribute("CART");
//                            url = siteMaps.getProperty(SHOPPING_PAGE);
                            url = AppConstants.CheckOutFeatures.SHOPPING_PAGE;

                        }
                    }
                }
            }
        } catch (SQLException ex) {
//            ex.printStackTrace();
//            log("CheckOutController " + ex.getMessage());
            LOGGER.error("CheckOutController _SQLException " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
//            ex.printStackTrace();
//            log("CheckOutController " + ex.getMessage());
            LOGGER.error("CheckOutController _ClassNotFoundException " + ex.getMessage());

        } catch (NamingException ex) {
//            ex.printStackTrace();
//            log("CheckOutController " + ex.getMessage());
            LOGGER.error("CheckOutController _NamingException " + ex.getMessage());

        } finally {
            response.sendRedirect(url);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
