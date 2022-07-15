package quanmx.filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import quanmx.utils.AppConstants;

/**
 *
 * @author Dell
 */
public class AccessControllFilter implements Filter {

    private static final boolean debug = true;
    private static final Logger LOGGER = Logger.getLogger(AccessControllFilter.class);
    private FilterConfig filterConfig = null;

    public AccessControllFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;

        //set header to prevent caching from browser
        httpRes.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpRes.setHeader("Pragma", "no-cache");
        httpRes.setDateHeader("Expire", 0);
        String resource = httpReq.getRequestURI();
        int lastIndex = resource.lastIndexOf("/");
        String action = resource.substring(lastIndex + 1);

        try {
            ServletContext context = request.getServletContext();
            Properties accessControll = (Properties) context.getAttribute("ACCESS_CONTROLL");
            String url = accessControll.getProperty(action);
            if (url != null) {
                HttpSession session = httpReq.getSession(false);
                boolean alreadyLogin = false;
                if (session != null && session.getAttribute("USER") != null) {
                    alreadyLogin = true;
                }
                if (alreadyLogin) {
                    chain.doFilter(request, response);
                } else if (url.equals("notAllowed")) {
                    httpRes.sendRedirect(AppConstants.DispatchFeatures.LOGIN_PAGE);
                } else if (url.equals("allow")) {
                    chain.doFilter(request, response);
                }
            } else {
                if (action.contains(".css")) {
                    chain.doFilter(request, response);
                } else {
                    httpRes.sendRedirect(AppConstants.DispatchFeatures.ERROR_PAGE);
                }

            }

        } catch (Throwable t) {
            LOGGER.warn("AccessControllFilter" + t.getMessage());
        }

    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {
    }

    /**
     * Init method for this filter
     *
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("AccessControllFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AccessControllFilter()");
        }
        StringBuffer sb = new StringBuffer("AccessControllFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
