package laborator1;

import java.io.BufferedReader;
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
import java.util.Map;
import java.util.Properties;
import java.util.Random;
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
    //private final Object lockProperties = new Object();
    //private final Object lockText = new Object();
    Map<Integer,Entry> map;
    File outputFile, outputPropertiesFile;
    
    PrintWriter txtWriter;
    FileReader fileReader;
    BufferedReader bufferedReader;
    
    InputStream propertiesFileInputStream;
    OutputStream propertiesFileOutputStream;
    
    Properties prop;
    int requestId;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String submitType = request.getParameter("submit");
        if(submitType != null && submitType.equals("javaapp")){
            
            synchronized(lock){
                try (PrintWriter out = response.getWriter()) {
                 
                    out.print("(Java App) ");
                    for(Integer key : map.keySet()){

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

                showHTMLResponseFromMap(out);
                //showHTMLResponseFromTXTFile(out);
                //showHTMLResponseFromPropertiesFile(out);
                
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
        
        //generate random id
//        Random random = new Random();
//        String id = String.valueOf(random.nextInt(1000000 + 1));
//        while(map.get(id) != null){
//        
//            id = String.valueOf(random.nextInt(1000000 + 1));
//        }
        //end of generate random id
        
        if ((name == null || name.trim().length() == 0) && (email == null || email.trim().length() == 0)) {
            
            out.println("<p>Name and Email values are missing...Please try again...</p>");
         }else if((name == null || name.trim().length() == 0)){
        
            out.println("<p>Name value is missing...Please try again...</p>");
        }else if(email == null || email.trim().length() == 0){
        
            out.println("<p>Email value is missing...Please try again...</p>");
        }else{
        
            Entry entry = new Entry(name,email, timestamp);
            exportData(entry);

        }
        
        processRequest(request,response);
        
    }
    
    
    public void exportData(Entry entry){
            synchronized(lock){
                addToMap(entry);
                writeToTXTFile(entry);
                writeToPropertiesFile(entry);
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
        outputFile = new File(System.getProperty("user.home") + "/Desktop/JavaEE_Lab1/servletInfo.txt");
        
//        if(outputFile.exists()){
//            
//            outputFile.delete();
//        }
        
        try {
            
            outputFile.createNewFile();
            //writer txt
            txtWriter = new PrintWriter(new FileWriter(outputFile,true));
            //reader txt
            fileReader = new FileReader(outputFile);
            bufferedReader = new BufferedReader(fileReader);
                
        } catch (FileNotFoundException ex) {
            
            Logger.getLogger(Servlet1.class.getName()).log(Level.SEVERE, "Problem occured while trying to create file reader", ex);
        
        } catch (IOException ex) {
            
            Logger.getLogger(Servlet1.class.getName()).log(Level.SEVERE, "File/Stream (printWriter) wasn't created...", ex);
        }
        
        /**
         * .properties file
         */
        
        prop = new Properties();
        requestId = 1;
        
        outputPropertiesFile = new File(System.getProperty("user.home") + "/Desktop/JavaEE_Lab1/servletInfo.properties");
        
//        if(outputPropertiesFile.exists()){
//            
//            outputPropertiesFile.delete();
//        }
        
        try {
            
            outputPropertiesFile.createNewFile();
            propertiesFileOutputStream = new FileOutputStream(outputPropertiesFile,true);
            propertiesFileInputStream = new FileInputStream(outputPropertiesFile);
            prop.load(propertiesFileInputStream);
            requestId = (prop.size()/3) + 1;
            
        } catch (FileNotFoundException ex) {
            
            Logger.getLogger(Servlet1.class.getName()).log(Level.SEVERE, "File wasn't found (\".properties\" file)...", ex);
        
        } catch (IOException ex) {
            
            Logger.getLogger(Servlet1.class.getName()).log(Level.SEVERE, "File wasn't created (\".properties\" file)...", ex);
        }
        

        
    }
    
    
    @Override
    public void destroy(){
        
        try {
            
            txtWriter.close();
            propertiesFileOutputStream.close();
            bufferedReader.close();
            propertiesFileInputStream.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Servlet1.class.getName()).log(Level.SEVERE, "Something went wrong (close -destroy) ...", ex);
        }
        
    }
    
    void addToMap(Entry entry){
        map.put(requestId, entry);
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
    
    
    public void showHTMLResponseFromMap(PrintWriter out){
    
        out.println( "<p><i>(The response is read from map)</i></p>");
        if(map.size() > 0){

            out.println("<p> maps no of elements: " + map.size() + "</p>");

            out.println("<p> Map's values: </p>");
            synchronized(lock){
            for(Integer key : map.keySet()){

                Entry entry = map.get(key);

                out.println("<p>key: " + key + "</p>");
                entry.doPrintEntry(out);

                out.println();
            }
            }
        }else{

            out.println("<p> The Map data structure has no value(s). </p>");
        }
    }
    
    /**
     * .txt file
     * @param entry : name, email, timestamp
     */
    public void writeToTXTFile(Entry entry){
    
        try {

            if(!outputFile.canWrite()){
                
                outputFile.setWritable(true);
            }   
                        // Write to file
            txtWriter.println("Map - new entry (key):  " + requestId + System.getProperty( "line.separator" ) );
            txtWriter.println("   (entry) Name:  " + entry.getName());
            txtWriter.println("   (entry) Email:  " + entry.getEmail());
            txtWriter.println("   (entry) TimeStamp:  " + entry.getTimestamp());
            txtWriter.println("-----------------------------------------------------------" + System.getProperty( "line.separator" ));
       
            
        } catch (Exception ex) {
            
            Logger.getLogger(Servlet1.class.getName()).log(Level.SEVERE, "Problem occured during the writing to \".txt\" file ... ", ex);
        }
    }
    
    
    public void showHTMLResponseFromTXTFile(PrintWriter out){
    
        out.println( "<p><i>(The response is read from file)</i></p>");
        
        try {
            
            if(outputFile.exists()){

                String line;
                synchronized(lock){
                    while((line = bufferedReader.readLine()) != null) {

                        out.println("<p>" + line + "</p>");
                    }
                }
            }else{
                
                out.println("file not exist...");
            }
        } catch (IOException ex) {
            
            Logger.getLogger(Servlet1.class.getName()).log(Level.SEVERE, "Problem occured the reading from \".txt\" file ... ", ex);
        }
    }
    
    /**
     * .properties file
     * @param entry : name, email, timestamp
     */
    
    public void writeToPropertiesFile(Entry entry){
        
            prop = new Properties();
//            prop.setProperty("id." + counterProp, id);
//            prop.setProperty("entry." + counterProp + ".id", entry.getKey());
//            prop.setProperty("entry." + counterProp + ".name", entry.getName());
//            prop.setProperty("entry." + counterProp + ".email", entry.getEmail());
//            prop.setProperty("entry." + counterProp + ".timestamp", entry.getTimestamp());
            //prop.setProperty("id." + counterProp, id);
            //prop.setProperty("entry." + counterProp + ".id", entry.getKey());
            prop.setProperty("entry." + requestId + ".name", entry.getName());
            prop.setProperty("entry." + requestId + ".email", entry.getEmail());
            prop.setProperty("entry." + requestId + ".timestamp", entry.getTimestamp());
       
            try {
                requestId++;
                prop.store(propertiesFileOutputStream, "Value no.: " + requestId);
            } catch (IOException ex) {
           
                Logger.getLogger(Servlet1.class.getName()).log(Level.SEVERE, "!Something went wrong while trying to store the properties n properties file output...", ex);
            }
            

        
        
    }
    
    
    public void showHTMLResponseFromPropertiesFile(PrintWriter out){
        
        out.println( "<p><i>(The response is read from file (.properties file)...</i></p>");
            
        try {
            
            prop.load(propertiesFileInputStream);

//            int counter = 1;
//            while(prop.get("id." + counter) != null){
//
//                out.println("<p>ID: " + prop.get("id." + counter) + "</p>");
//                out.println("<pre>" + "   (entry) key:  " +  counterProp + " </pre>");
//                out.println("<pre>" + "   (entry) Name:  " +  prop.get("entry." + counter + ".name")  + " </pre>");
//                out.println("<pre>" + "   (entry) Email:  " +  prop.get("entry." + counter + ".email")  + " </pre>");
//                out.println("<pre>" + "   (entry) TimeStamp:  " +  prop.get("entry." + counter + ".timestamp")  + " </pre>");
//                
//                counter++;
//            }
            for(int i=1; i<=requestId; i++){
                out.println("<p>ID: " + i + "</p>");
                out.println("<pre>" + "   (entry) Name:  " +  prop.get("entry." + i + ".name")  + " </pre>");
                out.println("<pre>" + "   (entry) Email:  " +  prop.get("entry." + i + ".email")  + " </pre>");
                out.println("<pre>" + "   (entry) TimeStamp:  " +  prop.get("entry." + i + ".timestamp")  + " </pre>");
            }
        } catch (IOException ex) {
            
            Logger.getLogger(Servlet1.class.getName()).log(Level.SEVERE, "Something went wrong while trying to load the \".properties\" file...", ex);
        }
    }

}
