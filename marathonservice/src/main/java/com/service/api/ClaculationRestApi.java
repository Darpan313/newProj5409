package com.service.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class ClaculationRestApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClaculationRestApi.class);
    private static int id = 0;

    public ClaculationRestApi() {

    }


    @GET
    @Path("/fibonacci/{number}")
    public Response fibonacciSeries(@Context UriInfo uriInfo, @PathParam("number") int number) {
        id++;
        final List<BigInteger> result;
        if (number < 0) {
            return Response.status(Response.Status.FORBIDDEN).entity(new GenericAPIResponse() {
                @Override
                public int getStatus() {
                    return Response.Status.FORBIDDEN.getStatusCode();
                }

                @Override
                public Object getMessage() {
                    return "Negative number not allowed";
                }

            }).build();
        } else {
            result = calculateFibonacci(number);
            return Response.ok().entity(new GenericAPIResponse() {
                @Override
                public Object getMessage() {
                    return new HashMap<String, Object>() {
                        {
                            put("result", result);

                        }
                    };
                }
                @Override
                public int getRequestId(){
                    return id;
                }
            }).build();
        }
    }

    private static List<BigInteger> calculateFibonacci(int a) {
        List<BigInteger> result = new ArrayList<>();
        for (int i = 1; i <= a; i++) {
            result.add(calculateFib(i));
        }
        return result;
    }

    private static BigInteger calculateFib(int i) {
        //Refer GeeksForGeeks: https://www.geeksforgeeks.org/large-fibonacci-numbers-java/
        BigInteger a = BigInteger.valueOf(0);
        BigInteger b = BigInteger.valueOf(1);
        BigInteger c = BigInteger.valueOf(1);
        for (int j = 2; j <= i; j++) {
            c = a.add(b);
            a = b;
            b = c;
        }
        return (a);
    }
//factorial code goes here
	    @GET
    @Path("/factorial/{number}")
    public Response factorial(@Context UriInfo uriInfo, @PathParam("number") int number) {
        id++;
        final String result;
        if (number < 0) {
            return Response.status(Response.Status.FORBIDDEN).entity(new GenericAPIResponse() {
                @Override
                public int getStatus() {
                    return Response.Status.FORBIDDEN.getStatusCode();
                }

                @Override
                public Object getMessage() {
                    return "Negative number not allowed";
                }

            }).build();
        } else {
            result = calculateFactorial(number);
            return Response.ok().entity(new GenericAPIResponse() {
                @Override
                public Object getMessage() {
                    return new HashMap<String, Object>() {
                        {
                            put("result", result);

                        }
                    };
                }
                @Override
                public int getRequestId(){
                    return id;
                }
            }).build();
        }
    }
//Referred GeeksForGeeks: https://www.geeksforgeeks.org/factorial-large-number/
    private static String calculateFactorial(int a) {
        String result = "";
        int res[] = new int[500];
        res[0] = 1;
        int res_size = 1;

        for (int i = 2; i <= a; i++) {
            res_size = multiply(i,res,res_size);
        }
        for(int i = res_size-1;i>=0;i--){
            result+=res[i];
        }
        return result;
    }

    private static int multiply(int x, int res[], int res_size) {
        int carry = 0;
        for(int i=0; i<res_size;i++){
            int prod = res[i]*x + carry;
            res[i] = prod%10;
            carry=prod/10;
        }
        while (carry!=0){
            res[res_size]=carry%10;
            carry=carry/10;
            res_size++;
        }
        return  res_size;
    }
}
