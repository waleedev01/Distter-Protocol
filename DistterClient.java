import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class DistterClient {

    /**
     * Initialise a new client. To run the client, call run().
     */
    public DistterClient() {}

    /**
     * Runs the client.
     * @throws IOException
     */
    public void run() throws IOException {
        /** Connecting to the TCP Server **/
        InetAddress host = InetAddress.getByName("distter.city.ac.uk");
        int port = 20111;
        System.out.println("DistterClient connecting to " + host.toString() + ":" + port);
        Socket clientSocket = new Socket(host, port);

        ArrayList<String> listPosts = new ArrayList<String>(); // Create an ArrayList that stores the post ids
        Scanner sc = new Scanner(System.in);//scanner for getting the user input

        boolean validSince = false;//check if post header is valid
        boolean validHeader = false;//check if post header is valid

        String msg;//input entered by the user
        String cmd="";//formatted input used for retrievin the requests
        ArrayList<Post> posts = new ArrayList<Post>(); // Create an ArrayList that stores posts object

        Post p = null;//creating a null post
        Post test = new Post();//storing the post data provided in the guidelines

        test.setPostId("SHA-256 bd4fd422f16f44ec6262e79f12c6269afab4ccdd48cd9ce8a75a572e2fddafe3");
        test.setCreated("1648000000");
        test.setAuthor("martin.brain@city.ac.uk");
        test.setTags("#distter");
        test.setTags("#IN2011");
        test.setTags("#2022");
        test.setContent("2");
        test.setLines("Hello Everyone!");
        test.setLines("Welcome to Distter!");

        posts.add(test);//adding the new post to the array list

        //getting current time
        Date d = new Date();
        int now = (int) (d.getTime()/1000);// divided by 1000 so the time format is in seconds

        boolean exit=false;//boolean for exiting the main while loop

        // Set up readers and writers for convenience
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        Writer writer = new OutputStreamWriter(clientSocket.getOutputStream());

        //Printing first line
        System.out.println(reader.readLine());


        // Hello is the first protocol request
        msg = sc.nextLine();//HELLO? DISTTER/1.0 wa
        writer.write(msg+"\n");
        //check that the hello request is correctly made
        if(msg.startsWith("HELLO? DISTTER/1.0 ")) {
            while(true) {//if correct a connection is established with the server

                msg = sc.nextLine();//this variable stores the input of the user

                if(!msg.equals("WHEN?")){//when does not have space
                    if (msg.startsWith("POSTS?") || msg.startsWith("FETCH?") || msg.startsWith("HELLO? DISTTER/1.0"))
                        try { cmd = msg.substring(0, msg.indexOf(' ')); } catch (Exception e) { }}//formatting the input to easily manage the cmds
                else
                    cmd = msg;

                if(msg.startsWith("GOODBYE!"))//goodbye is managed differently since it CAN have a message after
                    if(msg.contains(" "))
                        try { cmd = msg.substring(0, msg.indexOf(' ')); } catch (Exception e) { }
                    else
                        cmd = msg;
                //switch with the 5 protocol requests
                switch (cmd) {
                    case "POSTS?":
                        writer.write(msg+"\n");
                        writer.flush();
                        String[] headersCount = msg.split(" ", 3);//divide in three segments
                        //check that the time is not more than the current and that it's not empty
                        try { if(headersCount[1]!=""){ if(Integer.parseInt(headersCount[1]) < now)
                            validSince = true; }} catch (Exception e) { }
                        //check that header is more than 0 and not empty
                        try { if(headersCount[2]!=""){
                            if(Integer.parseInt(headersCount[2]) >= 0)
                                validHeader = true;
                        } } catch (Exception e) { }
                        //if both paramters are valid then the request is processed
                        if(validSince && validHeader) {
                            if (Integer.parseInt(headersCount[2]) >= 0) {
                                for (int i = 0; i < Integer.parseInt(headersCount[2]); i++) {
                                    msg = sc.nextLine();//get the hashtags
                                    writer.write(msg + "\n");
                                    writer.flush();
                                }
                            }
                            String option = reader.readLine();//get the options number
                            System.out.println(option);
                            String[] segments = option.split(" ", -1);
                            int postsNumber = Integer.parseInt(segments[1]);
                            for (int i = 0; i < postsNumber; i++) {
                                listPosts.add(reader.readLine());//save the posts in the array list
                            }

                            for (int i = 0; i < listPosts.size(); i++) {
                                System.out.println(listPosts.get(i));//print the array list with the posts
                            }

                        }
                        else{//if post request is not correcly made then an error message is shown
                            System.out.println(reader.readLine());
                            exit= true;
                        }
                        break;
                    case"GOODBYE!"://goodbye request
                        writer.write(msg+"\n");
                        writer.flush();
                        System.out.println(reader.readLine());
                        exit = true;//exit from the while loop
                        break;
                    case"WHEN?"://print
                        writer.write(msg+"\n");
                        writer.flush();
                        System.out.println(reader.readLine());
                        break;
                    case"HELLO?"://hello request returns an error since it can only be the first request
                        writer.write(msg+"\n");
                        writer.flush();
                        System.out.println(reader.readLine());
                        exit=true;
                        break;
                    case "FETCH?":
                        boolean postExit = false;//boolean used to exit the loop
                        writer.write(msg+"\n");
                        writer.flush();
                        String found = reader.readLine();//check if the element searched has been found
                        if(found.equals("FOUND") && msg.length()==79) {//if has been found
                            System.out.println("FOUND");
                            p = new Post();//create a new post
                            String tag="";
                            p.setPostId(reader.readLine().substring(9));//set the post id, we know that it's the id since it is always the first element
                            System.out.println("Post-id: " + p.getPostId());//print post id
                            while (true) {
                                cmd = reader.readLine();//get the next field
                                String parts[] = cmd.split(" ", 2);//divide the fields in two (before space and after)
                                if (parts[0].startsWith("#")) {//for hashtags is differently managed since there are no spaces
                                    tag=parts[0];
                                    parts[0] = "#";//every hashtags starts with # so we can have a general case.
                                }
                                //switch going through the post headers.
                                switch (parts[0]) {
                                    case "Created:"://set created field
                                        p.setCreated(parts[1]);
                                        System.out.println("Created: " + p.getCreated());
                                        break;
                                    case "Author:"://set author field
                                        p.setAuthor(parts[1]);
                                        System.out.println("Author: " + p.getAuthor());
                                        break;
                                    case "#"://set #s field
                                        p.setTags(tag);
                                        System.out.println(p.getTags());
                                        break;
                                    case "Reply:"://set reply field
                                        p.setReplies(parts[1]);
                                        System.out.println("Reply: " + p.getReplies());
                                        break;
                                    case "Contents:"://set content field
                                        p.setContent(parts[1]);
                                        System.out.println("Content: " + p.getContent());
                                        //for loop for getting every line
                                        for (int i = 0; i < Integer.parseInt(p.getContent()); i++) {
                                            p.setLines(reader.readLine());
                                        }
                                        //print the lines
                                        for (int i = 0; i< p.getLines().size();i++)
                                            System.out.println(p.getLines().get(i));
                                        //we can exit the while loop since contents is always the last header.
                                        postExit = true;
                                        break;
                                }
                                posts.add(p);//add the new post to the arraylist
                                if (postExit)//if it's true it will exit from the FETCH loop
                                    break;
                            }
                            break;
                        }
                        else if(msg.length()!=79) {//if message lenght is not 79 (fetch? + hash size
                            System.out.println("mbpm.DistterProtocolException: Hash should have 64 hex digits (i.e. 256 bits)\n");
                            exit = true;
                        }
                        else if(found.equals("SORRY") )//if post is not found
                            System.out.println("SORRY");
                        break;
                    default://if the command entered is not correct
                        System.out.println("GOODBYE! mbpm.DistterProtocolException: Unknown request found");
                        exit=true;
                }
                if(exit)
                    break;
            }
        }
        else{//if the first command is not a hello requeset
            System.out.println("GOODBYE! mbpm.DistterProtocolException: Header expected but empty line found");
        }
        //close the connection
        clientSocket.close();
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        DistterClient client = new DistterClient();
        client.run();
    }
}
