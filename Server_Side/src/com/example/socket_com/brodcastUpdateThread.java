package com.example.socket_com;
import java.net.*;

import javax.swing.JPanel;

import java.io.*;
//note: in case that the two computers are on separate lan and are remote, you need to open a port forwarding on 
//your router.
public class brodcastUpdateThread extends Thread
{
	private ServerSocket serverSocket;

	public  brodcastUpdateThread(int port) throws IOException
	{
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(1000000);
	
	}

	public void run()
	{
		while(true)
		{
			try
			{
				//getting the public ip of the server
				URL whatismyip = new URL("http://checkip.amazonaws.com");
				BufferedReader in = new BufferedReader(new InputStreamReader(
						whatismyip.openStream()));
				String ip = in.readLine(); //you get the IP as a String


				//printing the server ip address
				System.out.println("server ip address:"+ip);        	 


				//waiting to connect from client
				System.out.println("Waiting for client on port " +
						serverSocket.getLocalPort() + "...");
				Socket server = serverSocket.accept();//inf loop until client connects


				//printing the client IP address
				System.out.println("Just connected to "+ server.getRemoteSocketAddress());



				//note:
				//Each socket has both an OutputStream and an InputStream.
				//The client's OutputStream is connected to the server's InputStream,
				//and the client's InputStream is connected to the server's OutputStream


	
/*
				//writing hello to client
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				out.writeUTF("You are connected to the server: "+ ip);
*/

				GamePacket packet = null;


				//reading "packet" object from client
				ObjectInputStream inFromClient = new ObjectInputStream(server.getInputStream());
				try {
		
					packet=(GamePacket) inFromClient.readObject();
			
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//adding new player
				Main.game.addPlayer(new Player(server.getRemoteSocketAddress(),packet.getNickName()));
				Main.panel.update();
				System.out.println(packet.getNickName());
				System.out.println(packet.getPassword());
				System.out.println(Main.game.toString());
				
				/*
				/////////////////////////
				String response="";
				//reading hello
				ByteArrayOutputStream byteArrayOutputStream = 
						new ByteArrayOutputStream(1024);
				byte[] buffer = new byte[1024];

				int bytesRead;
				InputStream inputStream = server.getInputStream();

//				
//				  notice:
//				  inputStream.read() will block if no data return
//				 
				while ((bytesRead = inputStream.read(buffer)) != -1){
					byteArrayOutputStream.write(buffer, 0, bytesRead);
					response += byteArrayOutputStream.toString("UTF-8");
				}
				System.out.println(response);
				//////////////////////////////
				
				*/
				
				//server.close();
			}catch(SocketTimeoutException s)
			{
				System.out.println("Socket timed out!");
				break;
			}catch(IOException e)
			{
				e.printStackTrace();
				break;
			}
		}
	}

}