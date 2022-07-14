/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanmx.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.ServletContext;

/**
 *
 * @author Dell
 */
public class AppMapping {

    public static void loadSiteMaps(ServletContext context) throws IOException {

        String siteMapPath = context.getInitParameter("SITEMAPS_FILE_PATH");
        InputStream is = null;
        Properties siteMaps = null;
        try {
            siteMaps = new Properties();
            is = context.getResourceAsStream(siteMapPath);
            siteMaps.load(is);
            context.setAttribute("SITEMAPS", siteMaps);
        } finally {
            if (is != null) {
                is.close();
            }
        }

    }

    public static void loadAccessControll(ServletContext context) throws IOException {

        String siteMapPath = context.getInitParameter("ACCESS_CONTROLL");
        InputStream is = null;
        Properties accessControll = null;
        try {
            accessControll = new Properties();
            is = context.getResourceAsStream(siteMapPath);
            accessControll.load(is);
            context.setAttribute("ACCESS_CONTROLL", accessControll);
        } finally {
            if (is != null) {
                is.close();
            }
        }

    }
}
