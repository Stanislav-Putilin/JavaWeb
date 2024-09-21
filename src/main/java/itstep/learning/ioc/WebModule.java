package itstep.learning.ioc;

import com.google.inject.servlet.ServletModule;
import itstep.learning.filters.CharsetFilter;
import itstep.learning.filters.ControlFilter;
import itstep.learning.servlets.HomeServlet;
import itstep.learning.servlets.IndexServlet;
import itstep.learning.servlets.ServletsServlet;
import itstep.learning.servlets.SignupServlet;

public class WebModule extends ServletModule {
    @Override
    protected void configureServlets() {
        filter("/").through(ControlFilter.class);
        filter("/*").through(CharsetFilter.class);

        serve("/").with(HomeServlet.class);
        serve("/index").with(IndexServlet.class);
        serve("/servlets").with(ServletsServlet.class);
        serve("/signup").with(SignupServlet.class);
    }
}