import java.util.Map;

public class CacheManager {
    private static CacheManager instance;
    private Map<String, String> servletPathMap;

    private CacheManager() {
    }

    public static synchronized CacheManager getInstance() {
        if (instance == null) {
            instance = new CacheManager();
        }
        return instance;
    }

    public Map<String, String > getServletPathMap() {
        if (servletPathMap == null) {
            servletPathMap = XMLLoaderAndParser.loadServletPathMap();
        }
        return servletPathMap;
    }
}
