package itstep.learning.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import itstep.learning.dal.dao.UserDao;
import itstep.learning.services.hash.HashService;

import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class HomeServlet extends HttpServlet {

    private final HashService hashService;
    private final UserDao userDao;

    @Inject
    public HomeServlet(@Named("digest") HashService hashService, UserDao userDao) {
        this.hashService = hashService;
        this.userDao = userDao;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Boolean controlPassed = (Boolean) req.getAttribute("control");

        req.setAttribute( "page", "home" );
        req.setAttribute( "hash", hashService.digest("123") );
        req.setAttribute( "tables", userDao.installTables() ? "Tables OK" : "Tables Fail" );

        if (controlPassed != null && controlPassed) {
            req.setAttribute("controlStatus", "Контроль пройден");
        } else {
            req.setAttribute("controlStatus", "Нелегальный доступ");
        }

        req.getRequestDispatcher("WEB-INF/views/_layout.jsp").forward(req, resp);
    }
}