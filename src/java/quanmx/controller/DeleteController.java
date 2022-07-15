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
public class DeleteController extends HttpServlet {
    
    private static final Logger LOGGER = Logger.getLogger(DeleteController.class);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("pk");
        String searchValue = request.getParameter("lastSearchValue");
        ServletContext context = request.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITEMAPS");
        
        String urlRewriting = AppConstants.DeleteAccountFeatures.ERROR_PAGE;
        HttpSession session = request.getSession(false);
        RegistrationDTO user = (RegistrationDTO) session.getAttribute("USER");
        
        try {
            //check role of user
            if (user.isRole() == false) {
                LOGGER.warn("USER " + user.getUsername() + " attempted to delete another user");
            }
            RegistrationDAO dao = new RegistrationDAO();
            RegistrationDTO deletedUser = dao.getUserInforByUsername(username);
            if (user.isRole() == true && !user.getUsername().equals(username)
                    && deletedUser.isRole() == false) {
                //1. call DAO
                boolean result = dao.deleteByUsername(username);
                LOGGER.warn("ADMIN " + user.getUsername() + " deleted user " + username);
                //2. process result
                if (result) {
                    //call Search function again by using url rewriting
                    urlRewriting = AppConstants.DispatchFeatures.SEARCH_LASTNAME_CONTROLLER
                            + "?txtSearchValue=" + searchValue;
                }//delete is sucessful

            }
        } catch (SQLException ex) {
            LOGGER.error("DeleteController _SQLException " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            LOGGER.error("DeleteController _ClassNotFoundException " + ex.getMessage());
            
        } catch (NamingException ex) {
            LOGGER.error("DeleteController _NamingException " + ex.getMessage());
            
        } finally {
            response.sendRedirect(urlRewriting);
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
