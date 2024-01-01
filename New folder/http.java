import java.util.*;

public class RequestParser {

    private Set<String> validAuthTokens;

    public RequestParser(Set<String> validAuthTokens) {
        this.validAuthTokens = validAuthTokens;
    }

    public Map<String, String> parseRequest(String[] request) {
        Map<String, String> result = new HashMap<>();

        if (request.length < 2) {
            result.put("status", "INVALID");
            return result;
        }

        String requestType = request[0];
        String url = request[1];

        // Extract authentication token
        String authToken = extractAuthToken(url);

        if (authToken == null || !validAuthTokens.contains(authToken)) {
            result.put("status", "INVALID");
            return result;
        }

        if ("POST".equalsIgnoreCase(requestType)) {
            // For POST requests, validate CSRF token
            String csrfToken = extractCsrfToken(url);
            if (csrfToken == null || !isValidCsrfToken(csrfToken)) {
                result.put("status", "INVALID");
                return result;
            }
        }

        // If all validations pass, return "VALID" and the comma-separated string of parameters
        result.put("status", "VALID");
        result.put("parameters", parseParameters(url));

        return result;
    }

    private String extractAuthToken(String url) {
        // Implement logic to extract the authentication token from the URL
        // For example, assuming "token" is the parameter name
        return extractParameter(url, "token");
    }

    private String extractCsrfToken(String url) {
        // Implement logic to extract the CSRF token from the URL
        // For example, assuming "csrf_token" is the parameter name
        return extractParameter(url, "csrf_token");
    }

    private String extractParameter(String url, String paramName) {
        String[] params = url.split("[?&]");
        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2 && paramName.equals(keyValue[0])) {
                return keyValue[1];
            }
        }
        return null;
    }

    private boolean isValidCsrfToken(String csrfToken) {
        // Implement CSRF token validation logic
        return csrfToken.matches("[a-zA-Z0-9]{8,}");
    }

    private String parseParameters(String url) {
        // Implement logic to parse URL parameters as a comma-separated string
        // For example, extract key-value pairs and concatenate them
        // This is a simplified example; actual implementation depends on your URL structure
        StringBuilder parameters = new StringBuilder();
        String[] params = url.split("[?&]");
        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2) {
                if (parameters.length() > 0) {
                    parameters.append(",");
                }
                parameters.append(param);
            }
        }
        return parameters.toString();
    }

    public static void main(String[] args) {
        Set<String> validAuthTokens = new HashSet<>(Arrays.asList("token123", "secretToken"));
        RequestParser requestParser = new RequestParser(validAuthTokens);

        // Example requests
        String[] request1 = {"GET", "example.com/action?token=token123&param1=value1"};
        String[] request2 = {"POST", "example.com/action?token=secretToken&csrf_token=abc123&param2=value2"};

        // Parse and print results
        System.out.println(requestParser.parseRequest(request1));
        System.out.println(requestParser.parseRequest(request2));
    }
}









import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestParser {

    public static List<String> parseRequest(List<String> validAuthTokens, List<String[]> requests) {
        List<String> result = new ArrayList<>();
        

        for (String[] request : requests) {
            String requestType = request[0];
            String url = request[1];

            // Extracting authentication token from URL
            Pattern tokenPattern = Pattern.compile("token=([^&]+)");
            Matcher tokenMatcher = tokenPattern.matcher(url);       
            Pattern csrfPattern = Pattern.compile("csrf=([a-z0-9]+)");
            Matcher csrfMatcher = csrfPattern.matcher(url);
            Pattern idPattern  = Pattern.compile("id=([a-z0-9]+)");
            Matcher idMatcher = idPattern.matcher(url);
            Pattern namePattern  = Pattern.compile("name=([a-z0-9]+)");
            Matcher nameMatcher = namePattern.matcher(url);

            if (tokenMatcher.find()) {
                String authToken = tokenMatcher.group(1);
                String csrfToken = csrfMatcher.find() ? csrfMatcher.group(1) : null;
                String id = idMatcher.find() ? idMatcher.group(1) : null;
                String name = nameMatcher.find() ? nameMatcher.group(1) : null;
                
                // Validating authentication token
                if (validAuthTokens.contains(authToken)) {
                    // Validating CSRF token for POST requests
                    if (requestType.equals("POST") && csrfToken != null && csrfToken.matches("[a-z0-9]+") && csrfToken.length() >= 8) {
                        result.add("VALID," + "id," + id + ","+ "name," + name);
                    } else if (requestType.equals("GET")) {
                        result.add("VALID," + "id," + id + ","+ "name," + name);
                    } else {
                        result.add("INVALID");
                    }
                } else {
                    result.add("INVALID");
                }
            } else {
                result.add("INVALID");
            }
        }

        return result;
    }

    public static void main(String[] args) {
        List<String> validAuthTokens = List.of("token123", "token456");
        List<String[]> requests = List.of(
                new String[]{"GET", "http://example.com/resource?token=token123&id=123g5&name=ashok"},
                new String[]{"POST", "http://example.com/action?token=token456&csrf=abc+12378&id=123g578&name=ashokkkk"},
                new String[]{"GET", "http://example.com/data?token=invalidtoken"}
        );

        List<String> result = parseRequest(validAuthTokens, requests);
        for (String status : result) {
            System.out.println(status);
        }
    }
}












