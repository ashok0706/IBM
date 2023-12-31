import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/action")
public class RequestController {

    private Set<String> validAuthTokens = Set.of("token123", "secretToken");

    @GetMapping
    public Map<String, String> handleGetRequest(@RequestParam String token, @RequestParam Map<String, String> params) {
        Map<String, String> result = new HashMap<>();
        if (isValidAuthToken(token)) {
            String parameters = parseParameters(params);
            result.put("status", "VALID");
            result.put("parameters", parameters);
        } else {
            result.put("status", "INVALID");
        }
        return result;
    }

    @PostMapping
    public Map<String, String> handlePostRequest(@RequestParam String token,
                                                 @RequestParam(required = false) String csrfToken,
                                                 @RequestParam Map<String, String> params) {
        Map<String, String> result = new HashMap<>();
        if (isValidAuthToken(token) && isValidCsrfToken(csrfToken)) {
            String parameters = parseParameters(params);
            result.put("status", "VALID");
            result.put("parameters", parameters);
        } else {
            result.put("status", "INVALID");
        }
        return result;
    }

    private boolean isValidAuthToken(String token) {
        return validAuthTokens.contains(token);
    }

    private boolean isValidCsrfToken(String csrfToken) {
        return csrfToken == null || csrfToken.matches("[a-zA-Z0-9]{8,}");
    }

    private String parseParameters(Map<String, String> params) {
        StringBuilder parameters = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (parameters.length() > 0) {
                parameters.append(",");
            }
            parameters.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return parameters.toString();
    }
}
