package com.example.socket_com;
import java.net.*;
import java.util.Vector;

import javax.swing.JPanel;

import java.io.*;
//note: in case that the two computers are on separate lan and are remote, you need to open a port forwarding on 
//your router.
public class connectThread extends Thread
{
	private ServerSocket serverSocket;

	private Server server;
	public  connectThread(Server server) throws IOException
	{
		this.server=server;
		serverSocket = new ServerSocket(server.getServerPort());
		serverSocket.setSoTimeout(1000000);

	}

	public void run()
	{
		while(true)
		{
			try
			{



				//waiting to connect from client
				//serverLogs.add("Waiting for client on port " +serverSocket.getLocalPort() + "...");
				//	Main.panel.update();
				Socket socket = serverSocket.accept();//inf loop until client connects


	


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
				ObjectInputStream inFromClient = new ObjectInputStream(socket.getInputStream());
				try {

					packet=(GamePacket) inFromClient.readObject();

				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//adding new player
				if(packet.isConnect()){
					if(!server.isPlayerConnected(packet.getNickName())){
						//printing the client IP address
						server.getServerLogs().add("Just connected to "+ socket.getRemoteSocketAddress());
						//adding to server
						server.addPlayer(new Player(socket,packet.getNickName()));
						server.getServerLogs().add(packet.getNickName()+"has just connected!");
						server.getPanel().update();
					}
					//serverLogs.add(packet.getPassword());
				}
			

				try
				{
					//if(!game.isConnected(packet.getNickName())){
					Thread t = new ListenToPlayersThread(socket,server);
					t.start();
					//}

				}catch(IOException e)
				{
					e.printStackTrace();
				}	

				//System.out.println(Main.game.toString());

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
				server.getServerLogs().add("Socket timed out!");
				server.getPanel().update();
				break;
			}catch(IOException e)
			{
				e.printStackTrace();
				break;
			}
		}
	}

}