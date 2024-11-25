import com.google.gson.JsonObject;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecurityParamFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String URI = (request.getServletPath()).split("/")[1];

        List<ValidationRule> ValidationRules =  XMLLoaderAndParser.getXMLFormat(URI,request.getMethod());
        Map<String,String[]> RequestParams = request.getParameterMap();
        String ValidationError = APIParamValidator.ParamValidator(ValidationRules, RequestParams);
        if(ValidationError != null){
            ResponseUtil rm = new ResponseUtil(true,ValidationError,404);
            rm.sen_response((HttpServletResponse) servletResponse,new JsonObject());
        }

        filterChain.doFilter(request,servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
