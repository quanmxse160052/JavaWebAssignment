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
public class UpdateController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UpdateController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("txtUsername");
        String newPassword = request.getParameter("txtPassword");
        String newRole = request.getParameter("chkAdmin");
        String searchValue = request.getParameter("lastSearchValue");
        ServletContext context = request.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITEMAPS");
        String url = AppConstants.UpdateAccountFeatures.ERROR_PAGE;

        try {
            //check constraint about role 

            HttpSession session = request.getSession(false);
            RegistrationDTO user = (RegistrationDTO) session.getAttribute("USER");

            if (user.isRole() == false) {
                if (!user.getUsername().equals(username)) {
                    LOGGER.warn("USER: " + user.getUsername() + " attempted to modify registration infor without any allowing");
                }//end if user edit infor of another user/admin
                else if (newRole != null) {
                    LOGGER.warn("USER: " + user.getUsername() + " attempted to modify registration infor without any allowing");
                } //end if user edit his/her role
                else {
                    //1. call DAO
                    RegistrationDAO dao = new RegistrationDAO();
                    boolean result = dao.updateUserInfor(username, newPassword, newRole);
                    if (result) {
                        url = AppConstants.DispatchFeatures.SEARCH_LASTNAME_CONTROLLER
                                + "?txtSearchValue="
                                + searchValue;

                    }
                }
            } //end if user role is user

            if (user.isRole() == true) {

                RegistrationDAO dao = new RegistrationDAO();
                if (!user.getUsername().equals(username)) {
                    RegistrationDTO updatedUser = dao.getUserInforByUsername(username);
                    newPassword = updatedUser.getPassword();
                }

                if (user.getUsername().equals(username) && newRole == null) {
                    LOGGER.warn("ADMIN: " + user.getUsername() + " attempted to modify his/her role");
                }//end if admin modifies his/her role
                else if (!user.getUsername().equals(username) && newRole == null) {
                    LOGGER.warn("ADMIN: " + user.getUsername() + " modified " + username + " infor");
                    boolean result = dao.updateUserInfor(username, newPassword, newRole);
                    if (result) {
                        url = AppConstants.DispatchFeatures.SEARCH_LASTNAME_CONTROLLER
                                + "?txtSearchValue="
                                + searchValue;
                    }

                } //end if admin modifies role of another admin or user infor
                else {

                    boolean result = dao.updateUserInfor(username, newPassword, newRole);
                    if (result) {
                        url = AppConstants.DispatchFeatures.SEARCH_LASTNAME_CONTROLLER + "?txtSearchValue="
                                + searchValue;

                    }
                }
            }//end if user is admin

        } catch (ClassNotFoundException ex) {

            LOGGER.error("UpdateController _ClassNotFoundException " + ex.getMessage());
        } catch (SQLException ex) {

            LOGGER.error("UpdateController _SQLException " + ex.getMessage());

        } catch (NamingException ex) {

            LOGGER.error("UpdateController _NamingException " + ex.getMessage());

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
