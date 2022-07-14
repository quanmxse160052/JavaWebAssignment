package quanmx.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import quanmx.registration.RegistrationDAO;
import quanmx.registration.RegistrationDTO;
import quanmx.utils.AppConstants;

public class SearchController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SearchController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        ServletContext context = request.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITEMAPS");
        String url = siteMaps.getProperty(AppConstants.SearchAccountFeatures.SEARCH_RESULT_PAGE);

        String searchValue = request.getParameter("txtSearchValue");
        try {
            if (!searchValue.trim().isEmpty()) {
                // 1. call DAO
                RegistrationDAO dao = new RegistrationDAO();
                dao.searchLastName(searchValue);
                // 2. process result
                List<RegistrationDTO> result = dao.getAccounts();
                request.setAttribute("SEARCH_RESULT", result);

            } //search value has existed
        } catch (SQLException ex) {
            LOGGER.error("SearchController " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            LOGGER.error("SearchController " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("SearchController " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
