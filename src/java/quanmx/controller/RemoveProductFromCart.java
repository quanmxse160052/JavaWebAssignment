package quanmx.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import quanmx.cart.CartObject;
import quanmx.utils.AppConstants;

/**
 *
 * @author Dell
 */
public class RemoveProductFromCart extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(RemoveProductFromCart.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITEMAPS");
        String url = AppConstants.RemoveCartProductFeatures.VIEW_CART_PAGE;
        
        try {
            //1. get session
            HttpSession session = request.getSession(false);
            //2. check session has existed
            if (session != null) {
                //3. get cart from session
                CartObject cart = (CartObject) session.getAttribute("CART");
                //4. check cart has existed
                if (cart != null) {
                    //5. get items to see whether the cart containing any product
                    Map<Integer, Integer> items = cart.getItems();
                    //6. check items has existed
                    if (items != null) {
                        //7. get selected products
                        String removedProduct[] = request.getParameterValues("chkitems");
                        //8. check selected products to make sure that client has selected items to remove
                        if (removedProduct != null) {
                            for (String product : removedProduct) {
                                cart.removeFromCart(Integer.parseInt(product));
                            }
                            
                            session.setAttribute("CART", cart);
                        }
                    }
                }
            }
        } catch (NumberFormatException ex) {
            LOGGER.warn("RemoveProductFromCart _NumberFormatException " + ex.getMessage());
        } finally {
            //9. return to cart page after remove product
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
