package laborator1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/Servlet1"})
public class Servlet1 extends HttpServlet {

    private final Object lock = new Object();
    Map<String,Entry> map;
    File outputFile, outputPropertiesFile;
    OutputStream propertiesFileOutput = null;
    Properties prop;
    int counterProp;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String python = request.getParameter("submit");
        if(python.equals("javaapp"))
        {
            synchronized(lock){
                 try (PrintWriter out = response.getWriter()) {
                 
                 out.print("this is PYTHON !!!");
                 for(String key : map.keySet()){

                    Entry entry = map.get(key);
                
                    out.print(key+" ");
                    out.print(entry.getEmail()+" ");
                    out.print(entry.getName()+" ");

                    out.println();
                 }
                 
                 }
            }

        }
        else{
            try (PrintWriter out = response.getWriter()) {

                returnResponsFromMap(out);
                //returnResponsFromFile(out);
                //returnResponseFromPropertiesFile(out);
                /**
                 * write in server log
                 */
                showUserInfoInServerLog(request);
            }
        }
    }
    
  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
       
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        
        SimpleDateFormat newFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        String timestamp = newFormat.format(new Timestamp(System.currentTimeMillis()));
        
        Random random = new Random();
        String id = String.valueOf(random.nextInt(1000000 + 1));
        while(map.get(id) != null){
        
            id = String.valueOf(random.nextInt(1000000 + 1));
        }
        
        if ((name == null || name.trim().length() == 0) && (email == null || email.trim().length() == 0)) {
            
            out.println("<p>Name and Email values are missing...Please try again...</p>");
         }else if((name == null || name.trim().length() == 0)){
        
            out.println("<p>Name value is missing...Please try again...</p>");
        }else if(email == null || email.trim().length() == 0){
        
            out.println("<p>Email value is missing...Please try again...</p>");
        }else{
        
            Entry entry = new Entry(id,name,email, timestamp);
            //map.put(id, entry);
            addToMap(id,entry);
            //writeToFile(id, entry);
            
            //writeToPropertiesFile(id,entry);
        }
        
        processRequest(request,response);
        
    }
    void addToMap(String id, Entry entry)
    {
        synchronized(lock){
            map.put(id, entry);
        }

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        
        if(request.getParameter("submit") != null){
        
            response.setContentType("text/html");

            PrintWriter out = response.getWriter();
            out.println("<h1>Hello, World!</h1>");
            out.println("<h1>" + request.getParameter("name")+ "</h1>");
        }
    }

    @Override
    public String getServletInfo() {
        return "This is the first laboratory from Java Technologies - MISS 2018-2019";
    }// </editor-fold>

    
    @Override
    public void init(){
    
        map = new TreeMap<>();
        outputFile = new File(System.getProperty("user.home") + "/Desktop/servletInfo.txt");
        
        if(outputFile.exists()){
            
            outputFile.delete();
        }
        
        try {
            outputFile.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Servlet1.class.getName()).log(Level.SEVERE, "File wasn't created...", ex);
        }
        
        /**
         * .properties file
         */
        
        prop = new Properties();
        counterProp = 1;
        
        outputPropertiesFile = new File(System.getProperty("user.home") + "/Desktop/servletInfo.properties");
        
        if(outputPropertiesFile.exists()){
            
            outputPropertiesFile.delete();
        }
        
        try {
            
            outputPropertiesFile.createNewFile();
        } catch (IOException ex) {
            
            Logger.getLogger(Servlet1.class.getName()).log(Level.SEVERE, "File wasn't created (\".properties\" file)...", ex);
        }

      
    }
    
    public void showUserInfoInServerLog(HttpServletRequest request){
    
        System.out.println("-------------info-----------------");
    
        System.out.println("Http method used: " + request.getMethod());
        System.out.println("IP-Adress of the client: " + request.getLocalAddr());

        if(request.getHeader("user-agent") == null){
        
            System.out.println("The header \"user-agent\" wasn't found ...");
        }else{
        
            System.out.println("User agent: " + request.getHeader("user-agent"));
        }
        
        if(request.getHeader("accept-language") == null){
        
            System.out.println("The header \"accept-language\" wasn't found ...");
        }else{
        
            System.out.println("Client languague(s): " + request.getHeader("accept-language"));
            
        }
        
        Map<String,String[]> mapParameters = request.getParameterMap();
        if(mapParameters.size() > 0){
        
            System.out.println("Parameter of the request: " );
            for(String key : mapParameters.keySet()){
                
                System.out.print("\t>> " + key + ": " );
                String [] values = mapParameters.get(key);
                for (String value : values) {
                    
                    System.out.print(value);
                }
                
                System.out.println();
            }
        }else{
            
            System.out.println("The function \".getParameterMap()\" from the request hasn't return any value... " );
        }
        
    }
    
    public void returnResponsFromMap(PrintWriter out){
    
        out.println( "<p><i>(The response is read from map)</i></p>");
        if(map.size() > 0){

            out.println("<p> maps no of elements: " + map.size() + "</p>");

            out.println("<p> Map's values: </p>");

            for(String key : map.keySet()){

                Entry entry = map.get(key);

                out.println("<p>key: " + key + "</p>");
                entry.doPrintEntry(out);

                out.println();
            }
        }else{

            out.println("<p> The Map data structure has no value(s). </p>");
        }
    }
    
    /**
     * .txt file
     */
    public void writeToFile(String id, Entry entry) throws IOException{
    
        try {

            PrintWriter writer;
            writer = new PrintWriter(new FileWriter(outputFile,true));
            if(!outputFile.canWrite()){
                
                outputFile.setWritable(true);
            }   
                        // Write to file
            writer.println("Map - new entry (key):  " + id + System.getProperty( "line.separator" ) );
            writer.println("   (entry) key:  " + entry.getKey());
            writer.println("   (entry) Name:  " + entry.getName());
            writer.println("   (entry) Email:  " + entry.getEmail());
            writer.println("   (entry) TimeStamp:  " + entry.getTimestamp());
            writer.println("-----------------------------------------------------------" + System.getProperty( "line.separator" ));
       
            writer.flush();
            writer.close();
            
        } catch (FileNotFoundException ex) {
            
            Logger.getLogger(Servlet1.class.getName()).log(Level.SEVERE, "Problem occured during the writing to \".txt\" file ... ", ex);
        }
    }
    
    public void returnResponsFromFile(PrintWriter out){
    
        out.println( "<p><i>(The response is read from file)</i></p>");

        try {
            
            if(outputFile.exists()){
                
                FileReader fileReader = new FileReader(outputFile);

                BufferedReader bufferedReader;
                bufferedReader = new BufferedReader(fileReader);

                String line;
                while((line = bufferedReader.readLine()) != null) {

                    out.println("<p>" + line + "</p>");
                }

                bufferedReader.close(); 
            }else{
                
                out.println("file not exist...");
            }
            
        } catch (IOException ex) {
            
            Logger.getLogger(Servlet1.class.getName()).log(Level.SEVERE, "Problem occured the reading from \".txt\" file ... ", ex);
        }
    }
    
    /**
     * .properties file
     */
    
    public void writeToPropertiesFile(String id, Entry entry){
        //prop.clear();
        prop.setProperty("id." + counterProp, id);
        prop.setProperty("entry." + counterProp + ".id", entry.getKey());
        prop.setProperty("entry." + counterProp + ".name", entry.getName());
        prop.setProperty("entry." + counterProp + ".email", entry.getEmail());
        prop.setProperty("entry." + counterProp + ".timestamp", entry.getTimestamp());
       
        try {
            
            propertiesFileOutput = new FileOutputStream(outputPropertiesFile);
        
            prop.store(propertiesFileOutput, "Value no.: " + counterProp);
            propertiesFileOutput.close();
       
        } catch (FileNotFoundException ex) {
            
            Logger.getLogger(Servlet1.class.getName()).log(Level.SEVERE, "output stream problem...", ex);
        } catch (IOException ex) {
            
            Logger.getLogger(Servlet1.class.getName()).log(Level.SEVERE, "Something went wrong ... the property wasn't add to file...", ex);
        }
        
        counterProp++;
    }
    
    
    public void returnResponseFromPropertiesFile(PrintWriter out){
        
        out.println( "<p><i>(The response is read from file (.properties file)...</i></p>");
        InputStream input = null;

	try {

            input = new FileInputStream(outputPropertiesFile);

            // load a properties file
            prop.load(input);

            int counter = 1;
            while(prop.get("id." + counter) != null){

                out.println("<p>ID: " + prop.get("id." + counter) + "</p>");
                out.println("<pre>" + "   (entry) key:  " +  prop.get("entry." + counter + ".id")  + " </pre>");
                out.println("<pre>" + "   (entry) Name:  " +  prop.get("entry." + counter + ".name")  + " </pre>");
                out.println("<pre>" + "   (entry) Email:  " +  prop.get("entry." + counter + ".email")  + " </pre>");
                out.println("<pre>" + "   (entry) TimeStamp:  " +  prop.get("entry." + counter + ".timestamp")  + " </pre>");
                
                counter++;
            }
                
	} catch (IOException ex) {
            
		ex.printStackTrace();
	} finally {
            
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
    }

}
