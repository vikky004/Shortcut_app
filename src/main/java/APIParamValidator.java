import java.util.List;
import java.util.Map;

public class APIParamValidator {
    static String ParamValidator(List<ValidationRule> ValidationRules, Map<String,String[]> RequestParam){
        String paramValue;
        for (ValidationRule rule : ValidationRules){
            paramValue = RequestParam.get(rule.name)[0];
            if (!paramValue.matches(rule.regex)){
                return rule.name;
            }
        }
        return null;
    }
}
