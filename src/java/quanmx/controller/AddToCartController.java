package quanmx.controller;

import java.io.IOException;
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
public class AddToCartController extends HttpServlet {
    
    private static final Logger LOGGER = Logger.getLogger(AddToCartController.class);


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITEMAPS");
        String url = AppConstants.AddToCartFeatures.ERROR_PAGE;
        
        try {
            HttpSession session = request.getSession(true);
            int sku = Integer.parseInt(request.getParameter("productID"));
            //1. get cart from session
            CartObject cart = (CartObject) session.getAttribute("CART");
            //2. check whether or not cart has existed in session
            if (cart == null) {
                cart = new CartObject();
            }
            //3. add product to cart
            cart.addToCart(sku);
            //4. store cart to session again
            session.setAttribute("CART", cart);
            url = AppConstants.AddToCartFeatures.SHOPPING_PAGE;
            
        } catch (NumberFormatException ex) {
            LOGGER.error("AddToCartController _NumberFormatException " + ex.getMessage());
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
