package ee.aiskov.test;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestServicesClient {

    @Test
    public void requestClientsUsingGet() throws Exception {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL restAPIUrl = new URL("http://localhost:8989/api/v1/clients");
            //link to swagger: http://localhost:8989/swagger-ui.html#!/client-controller/listUsingGET

            connection = (HttpURLConnection) restAPIUrl.openConnection();
            connection.setRequestMethod("GET");

            // Read the response
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder jsonData = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonData.append(line);
            }

            System.out.println(jsonData.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Clean up
            IOUtils.closeQuietly(reader);
            if (connection != null)
                connection.disconnect();
        }
    }


}
