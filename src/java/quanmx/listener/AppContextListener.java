package quanmx.listener;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import quanmx.utils.AppMapping;

/**
 * Web application lifecycle listener.
 *
 * @author Dell
 */
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        System.out.println("Deploying....");
        ServletContext context = sce.getServletContext();
        String log4jConfigFile = context.getInitParameter("log4j-config-location");
        String fullPath = context.getRealPath("/") + File.separator + log4jConfigFile;
        System.setProperty("PATH", context.getRealPath("/"));
        PropertyConfigurator.configure(fullPath);
        Logger LOGGER = Logger.getLogger(AppContextListener.class);

        try {
            AppMapping.loadSiteMaps(context);
            AppMapping.loadAccessControll(context);
        } catch (IOException ex) {
            LOGGER.error("MyAppServlet IO" + ex.getMessage());
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
//        System.out.println("Undeploy........................");

    }
}
