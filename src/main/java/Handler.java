import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Handler {
    void handleProcess(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException;
}
