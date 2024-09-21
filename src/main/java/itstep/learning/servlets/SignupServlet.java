package itstep.learning.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import itstep.learning.services.formparse.FormParseResult;
import itstep.learning.services.formparse.FormParseService;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Singleton
public class SignupServlet extends HttpServlet
{
    private final FormParseService formParseService;

    @Inject
    public SignupServlet(FormParseService formParseService) {
        this.formParseService = formParseService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute( "page", "signup" );
        req.getRequestDispatcher("WEB-INF/views/_layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
    {
        FormParseResult res = formParseService.parse(req);
        Map<String, String> fields = res.getFields();
        Map<String, FileItem> files = res.getFiles();

        for (Map.Entry<String, String> entry : fields.entrySet()) {
            req.setAttribute(entry.getKey(), entry.getValue());
        }

        if (!files.isEmpty()) {
            FileItem fileItem = files.values().iterator().next();  // Получаем первый файл
            req.setAttribute("fileName", fileItem.getName());
            req.setAttribute("fileSize", fileItem.getSize());
        }

        req.getRequestDispatcher("WEB-INF/views/signupResult.jsp").forward(req, resp);
    }
}