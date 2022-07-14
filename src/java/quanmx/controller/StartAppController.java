package quanmx.controller;

import java.io.IOException;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import quanmx.utils.AppConstants;

/**
 *
 * @author Dell
 */
public class StartAppController extends HttpServlet {

//
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITEMAPS");
        String url = AppConstants.StartAppFeatures.LOGIN_PAGE;

        try {

//            //1. check cookies has been existed (get cookies)
//            Cookie[] cookies = request.getCookies();
//            if (cookies != null) {
//                //2. get last cookies
//                Cookie lastCookie = cookies[cookies.length - 1];
//                //3. get username and password
//                String username = lastCookie.getName();
//                String password = lastCookie.getValue();
//                //4. call DAO
//                RegistrationDAO dao = new RegistrationDAO();
//                boolean result = dao.checkLogin(username, password);
//                if (result) {
//                    url = SEARCH_PAGE;
//                }
//            }//cookies has existed
            //check login using session
            HttpSession session = request.getSession(false);
            if (session != null) {
                if (session.getAttribute("USER") != null) {
                    url = AppConstants.StartAppFeatures.SEARCH_PAGE;

                }
            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        } catch (ClassNotFoundException ex) {
//            ex.printStackTrace();
//        } catch (NamingException ex) {
//            ex.printStackTrace();
//        } finally {

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
