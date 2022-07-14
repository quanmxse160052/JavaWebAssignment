package quanmx.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import quanmx.registration.RegistrationDAO;
import quanmx.registration.RegistrationDTO;
import quanmx.utils.AppConstants;

/**
 *
 * @author Dell
 */
public class LoginController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

//it was commented after completing StartAppController. Website needs to have "welcome, [userName]"
//so I change to dynamic page
//private final String SEARCH_PAGE = "search.html";
//    private final String INVALID_PAGE = "invalidPage";
//    private final String SEARCH_PAGE = "searchPage";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext context = request.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITEMAPS");
//        String url = siteMaps.getProperty(INVALID_PAGE);
        String url = AppConstants.LoginFeatures.INVALID_PAGE;
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");

        try {
            // 1. call Model/DAO
            // a. new DAO obj, then call method on DAO object
            RegistrationDAO dao = new RegistrationDAO();
            RegistrationDTO user = dao.checkLogin(username, password);
            if (user != null) {
//                url = siteMaps.getProperty(SEARCH_PAGE);
                url = AppConstants.LoginFeatures.SEARCH_PAGE;

//                Cookie cookie = new Cookie(username, password);
//                cookie.setMaxAge(60 * 2);
//                response.addCookie(cookie);
                //Create session
                HttpSession session = request.getSession(true);
                session.setMaxInactiveInterval(60);
                session.setAttribute("USER", user);

            } //end if user is authenticated
            //end if user clicks login

        } catch (SQLException ex) {
//            ex.printStackTrace();
//            log("LoginController " + ex.getMessage());
            LOGGER.error("LoginController " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
//            ex.printStackTrace();
//            log("LoginController " + ex.getMessage());
            LOGGER.error("LoginController " + ex.getMessage());

        } catch (NamingException ex) {
//            ex.printStackTrace();
//            log("LoginController " + ex.getMessage());
            LOGGER.error("LoginController " + ex.getMessage());

        } finally {
            //use response.sendRedirect to store cookies in client side before come to the website again
//            RequestDispatcher rd = request.getRequestDispatcher(url);
//            rd.forward(request, response);
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
