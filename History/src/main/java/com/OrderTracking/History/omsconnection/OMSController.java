package com.OrderTracking.History.omsconnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
@RestController
@RequestMapping ("/oms")
public class OMSController {
	
	    @Value("${oms.endpoint}")
	    private String omsEndpoint;
 
	    @Value("${oms.apiKey}")
	    private String apiKey;
 
	    @GetMapping("/omsConnect")
	    public ResponseEntity<String> connectToOMS() {
	        ResponseEntity<String> apiKeyValidationResult = validateApiKey();
	        ResponseEntity<String> omsEndpointValidationResult = validateOmsEndpoint();
 
	        if (apiKeyValidationResult != null && omsEndpointValidationResult != null) {
	            String errorMessage = "Invalid apiKey and omsEndpoint. Connection failed";
	            System.err.println("API Connection failed: " + errorMessage);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
	        } else if (apiKeyValidationResult != null) {
	            return apiKeyValidationResult;
	        } else if (omsEndpointValidationResult != null) {
	            return omsEndpointValidationResult;
	        }
 
	        OMSConnectionResult connectionResult = OMSConnector.establishConnection(omsEndpoint, apiKey);
	        if (connectionResult.isSuccess()) {
	            System.out.println("API Connection is successful");
	            return ResponseEntity.ok("Connection Successful: " + connectionResult.getMessage());
	        } else {
	            System.err.println("API Connection failed: " + connectionResult.getMessage());
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(connectionResult.getMessage());
	        }
	    }
 
	    private ResponseEntity<String> validateApiKey() {
	        if (apiKey == null || apiKey.isEmpty()) {
	            System.err.println("API Connection failed: apiKey is not filled");
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("apiKey needs to be filled, Connection failed");
	        } else if (apiKey.length() > 10) {
	            System.err.println("API Connection failed: Maximum length exceeded for apiKey. Maximum length is 10 characters");
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Maximum length exceeded for apiKey. Maximum length is 10 characters, Connection failed");
	        }
	        return null;
	    }
 
	    private ResponseEntity<String> validateOmsEndpoint() {
	        if (omsEndpoint == null || omsEndpoint.isEmpty()) {
	            System.err.println("API Connection failed: omsEndpoint is not filled");
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("omsEndpoint needs to be filled, Connection failed");
	        } else if (omsEndpoint.length() > 100) {
	            System.err.println("API Connection failed: Maximum length exceeded for omsEndpoint. Maximum length is 5 characters");
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Maximum length exceeded for omsEndpoint. Maximum length is 5 characters, Connection failed");
	        }
	        return null;
	    }

		public void setOmsEndpoint(String string) {
			// TODO Auto-generated method stub
			
		}

		public void setApiKey(String string) {
			// TODO Auto-generated method stub
			
		}

		public void setRestTemplate(RestTemplate restTemplateMock) {
			// TODO Auto-generated method stub
			
		}

		public void setConnector(OMSConnector mockConnector) {
			// TODO Auto-generated method stub
			
		}
}