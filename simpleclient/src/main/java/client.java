import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.opencsv.CSVWriter;
import org.apache.http.client.ClientProtocolException;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;

public class client {
    public static void main(String[] args) throws ClientProtocolException, IOException {
        //create Text file of 100 random numbers..
        //Read text file line by line
        //send request to fibonacci api & factorial api
        //store response and time diff in text file
        //store |Algorithm|N|Time in csv file.

        generateFile("input.txt");
        File file = new File("input.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));
        ArrayList<String> CSVrow = new ArrayList<>();
        ArrayList<String> logRow = new ArrayList<>();

        String st;
        while ((st = br.readLine()) != null){
            Client client = ClientBuilder.newClient( new ClientConfig().register( LoggingFilter.class ) );
            WebTarget webTarget = client.target("http://desktop-rq7tn9s:5557/").path("fibonacci").path(st);
            Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
            Response response = invocationBuilder.get();
            long start_time = System.nanoTime();
            response = invocationBuilder.get();
            long end_time = System.nanoTime();
            double difference = (end_time - start_time) / 1e6;
            String res = response.readEntity(String.class);
            //System.out.println(response.readEntity(String.class) + difference);
            CSVrow.add("Fibonacci;"+st+";"+difference);
            logRow.add(res+" N:"+st+" Response-time:"+difference);
            //generateLogFile(res,st,difference);
            webTarget = client.target("http://desktop-rq7tn9s:5557/").path("factorial").path(st);
            start_time = System.nanoTime();
            invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
            response = invocationBuilder.get();
            end_time = System.nanoTime();
            difference = (end_time - start_time) / 1e6;
            res = response.readEntity(String.class);
            CSVrow.add("Factorial;"+st+";"+difference);
            logRow.add(res+" N:"+st+" Response-time:"+difference);
        }
        br.close();
        generateCSV(CSVrow);
        generateLog(logRow);
    }
    public static void generateFile(String fileName){
        try{
            FileWriter fw = new FileWriter(fileName);
            ArrayList<Integer> numbers = new ArrayList();
            for(int i=1;i<=100;i++){
                numbers.add(i);
            }
            Collections.shuffle(numbers);
            for(int i=0;i<100;i++){
                int val = numbers.get(i);
                fw.write(String.valueOf(val));
                String newLine = System.getProperty("line.separator");
                fw.write(newLine.toString());
            }
            fw.flush();
            fw.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void generateCSV(ArrayList<String> rows){
        File file = new File("output.csv");
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = { "Algorithm", "Number", "Time" };
            writer.writeNext(header);

            for(int i=0;i<rows.size();i++){
                String[] data = rows.get(i).split(";");
                writer.writeNext(data);
            }
            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void generateLog(ArrayList<String> rows){
        try{
            FileWriter fw = new FileWriter("Log.txt");
            for(int i=0;i<rows.size();i++){
                String val = rows.get(i);
                fw.write(val);
                String newLine = System.getProperty("line.separator");
                fw.write(newLine.toString());
            }
            fw.flush();
            fw.close();

        }catch(IOException e){
            e.printStackTrace();
        }

    }
}