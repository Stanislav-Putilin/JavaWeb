package itstep.learning.servlets;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import itstep.learning.dal.dao.UserDao;
import itstep.learning.models.form.UserSignupFormModel;
import itstep.learning.rest.RestResponse;
import itstep.learning.services.files.FileService;
import itstep.learning.services.formparse.FormParseResult;
import itstep.learning.services.formparse.FormParseService;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.logging.Logger;

@Singleton
public class SignupServlet extends HttpServlet
{
    private final FormParseService formParseService;
//    private final FileService fileService;
//    private final UserDao userDao;
//    private final Logger logger;
//    , FileService fileService, UserDao userDao, Logger logger

    @Inject
    public SignupServlet(FormParseService formParseService) {
        this.formParseService = formParseService;
//        this.fileService = fileService;
//        this.userDao = userDao;
//        this.logger = logger;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute( "page", "signup" );
        req.getRequestDispatcher("WEB-INF/views/_layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
    {
        RestResponse restResponse = new RestResponse();
        resp.setContentType( "application/json" );

        UserSignupFormModel model;
        try {
            model = getModelFromRequest( req );
        }
        catch( Exception ex ) {
            restResponse.setStatus( "Error" );
            restResponse.setData( ex.getMessage() );
            resp.getWriter().print(
                    new Gson().toJson( restResponse )
            );
            return;
        }

        restResponse.setStatus( "Ok" );
        restResponse.setData( model );
        resp.getWriter().print(
                new Gson().toJson( restResponse )
        );


//        // передаємо на БД
//        User user = userDao.signup( model );
//        if( user == null ) {
//            restResponse.setStatus( "Error" );
//            restResponse.setData( "500 DB Error, details on server logs" );
//            resp.getWriter().print(
//                    new Gson().toJson( restResponse )
//            );
//            return;
//        }

        //        FormParseResult res = formParseService.parse(req);
//        Map<String, String> fields = res.getFields();
//        Map<String, FileItem> files = res.getFiles();
//
//        for (Map.Entry<String, String> entry : fields.entrySet()) {
//            req.setAttribute(entry.getKey(), entry.getValue());
//        }
//
//        if (!files.isEmpty()) {
//            FileItem fileItem = files.values().iterator().next();  // Получаем первый файл
//            req.setAttribute("fileName", fileItem.getName());
//            req.setAttribute("fileSize", fileItem.getSize());
//        }
//
//        req.getRequestDispatcher("WEB-INF/views/signupResult.jsp").forward(req, resp);
    }

    private UserSignupFormModel getModelFromRequest(HttpServletRequest req ) throws Exception {
        SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");
        FormParseResult res = formParseService.parse( req );
        UserSignupFormModel model = new UserSignupFormModel();
        model.setName( res.getFields().get("user-name") );

        if( model.getName() == null || model.getName().isEmpty() ) {
            throw new Exception( "Missing or empty required field: 'user-name'" );
        }

        model.setEmail(res.getFields().get("user-email"));
        if (model.getEmail() == null || !model.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new Exception("Invalid email format");
        }

        model.setPassword(res.getFields().get("user-password"));
        String repeatPassword = res.getFields().get("user-repeat");

        if (model.getPassword() == null || model.getPassword().length() < 4) {
            throw new Exception("Password must be at least 4 characters long");
        }

        if (!model.getPassword().equals(repeatPassword)) {
            throw new Exception("Passwords do not match");
        }

        try {
            model.setBirthdate(dateParser.parse(res.getFields().get("user-birthdate")));
        } catch (ParseException ex) {
            throw new Exception(ex.getMessage());
        }

//        // зберігаємо файл-аватарку та одержуємо його збережене ім'я
//        String uploadedName = null;
//        FileItem avatar = res.getFiles().get( "user-avatar" );
//        if( avatar.getSize() > 0 ) {
//            uploadedName = fileService.upload( avatar );
//            model.setAvatar( uploadedName );
//        }
//        System.out.println( uploadedName );

        return model;
    }
}