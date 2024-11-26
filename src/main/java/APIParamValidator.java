import java.util.List;
import java.util.Map;

public class APIParamValidator {
    static String ParamValidator(Map<String, String> ValidationRules, Map<String,String[]> RequestParam) throws Exception{
        String paramValue;
        String regex;
        for (Map.Entry<String,String[]> param : RequestParam.entrySet()){
            paramValue = param.getValue()[0];
            regex = ValidationRules.get(param.getKey());
            if (regex == null){
                throw new Exception("Invalid Node :" + param.getKey());
            }
            if (!paramValue.matches(regex)){
                return param.getKey();
            }
        }
        return null;
    }
}
