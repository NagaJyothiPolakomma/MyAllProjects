package com.jyo;

import org.apache.synapse.MessageContext; 
import org.apache.synapse.mediators.AbstractMediator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.commons.logging.LogFactory;
public class DbCustom extends AbstractMediator { 

 
    private static final Log log = LogFactory.getLog(DbCustom.class);

    @Override
    public boolean mediate(MessageContext mc) {
    
        try {
              String host = (String) mc.getProperty("host");
                String port = (String) mc.getProperty("port");
                String dbName = (String) mc.getProperty("dbname");
                String userName = (String) mc.getProperty("userName");
                String password = (String) mc.getProperty("password");
                String driverClass = (String) mc.getProperty("driverClass");
                String jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?allowPublicKeyRetrieval=true&useSSL=false";
                String query = (String) mc.getProperty("sqlQuery"); 
    
            JSONArray jsonArray = new JSONArray();
            Class.forName(driverClass);
            try (Connection con = DriverManager.getConnection(jdbcUrl, userName, password);
                 Statement stmt = con.createStatement()) {

                 if (query.trim().toUpperCase().startsWith("SELECT") || query.trim().toUpperCase().startsWith("CALL")) {
                        try (ResultSet rs = stmt.executeQuery(query)) {
                            while (rs.next()) {
                                JSONObject jsonObject = new JSONObject();
                                int columnCount = rs.getMetaData().getColumnCount();
                                for (int i = 1; i <= columnCount; i++) {
                                    String columnName = rs.getMetaData().getColumnLabel(i);
                                    Object columnValue = rs.getObject(i);

                                    if (columnValue instanceof String) {
                                        try {
                                            JSONObject columnJson = new JSONObject((String) columnValue);
                                            jsonObject.put(columnName, columnJson);
                                        } catch (JSONException e) {
                                            jsonObject.put(columnName, columnValue);
                                        }
                                    } else {
                                        jsonObject.put(columnName, columnValue);
                                    }
                                }
                                jsonArray.put(jsonObject); 
                            }
                        }
                    }else {
                   
                    int affectedRows = stmt.executeUpdate(query);
                  
                    JSONObject responseObj = new JSONObject();
                    responseObj.put("affectedRows", affectedRows);
                    responseObj.put("status", "Success");
                    responseObj.put("message", "Query executed successfully");
                    jsonArray.put(responseObj);
                }

                
                    JSONObject responseObj = new JSONObject();
                    responseObj.put("response", jsonArray);
                    responseObj.put("message", "Successfully Executed The Query");
                    responseObj.put("status", "Success");
                    String response = responseObj.toString();
                  mc.setProperty("Result", response);


            } }catch (Exception e) {
                handleException(mc, e);
            }
            return true;
        }

        private void handleException(MessageContext mc, Exception e) {
        log.error("Error occurred: " + e.getMessage(), e);

            JSONObject errorObj = new JSONObject();
            try {
                JSONArray jsonArray = new JSONArray();
                errorObj.put("response", jsonArray);
                errorObj.put("status", "Fail");
                errorObj.put("message", e.getMessage());
            } catch (JSONException ex) {
                log.error("Error while constructing error JSON: " + ex.getMessage(), ex);
            }

            String response = errorObj.toString();
            mc.setProperty("Result", response);
           log.info(response);
        }
    }

	

