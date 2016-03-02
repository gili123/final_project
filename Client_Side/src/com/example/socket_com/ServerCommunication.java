package com.example.socket_com;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Intent;
import android.os.AsyncTask;

public class ServerCommunication {
	public static final int hit=0,connect=1,getGamesList=2,createGame=3,disconnect=4;
	
	
	
	public  class MyClientTask_ListenToPakcets extends AsyncTask<Void, Void, Void> {


		String response = "";

		@Override
		protected Void doInBackground(Void... arg0) {

			GamePacket packet = null;
			while(true){

				//reading "packet" object from client
				try {
					ObjectInputStream inFromClient = new ObjectInputStream(MainActivity.socket.getInputStream());
					packet=(GamePacket) inFromClient.readObject();

				} 
				catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					response = "UnknownHostException: " + e.toString();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					response = "IOException: " + e.toString();
				}



				if(packet.isHit()){
					MainActivity.player.Hit(packet.getHitArea());
					GameInterface.hitRecvied();

				}
				/*finally
			{
				if(MainActivity.socket != null){
					try {
						MainActivity.socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}*/
			}
		}

		@Override
		protected void onPostExecute(Void result) {
			//textResponse.setText(response);
			super.onPostExecute(result);
		}

	}






	public class MyClientTask_SendPakcet extends AsyncTask<Void, Void, Void> {


		String response = "";
		GamePacket packet;


		public void setPacket(GamePacket packet){
			this.packet=packet;
		}

		@Override
		protected Void doInBackground(Void... arg0) {


			try {

				//writing object
				ObjectOutputStream outToServer = new ObjectOutputStream(MainActivity.socket.getOutputStream());
				outToServer.writeObject(packet);
				/*
				//writing texts
				DataOutputStream out = new DataOutputStream(MainActivity.socket.getOutputStream());
				out.writeUTF("I am Client");
				 */

			}
		
			catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response = "UnknownHostException: " + e.toString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response = "IOException: " + e.toString();
			}
			/*finally
			{
				if(MainActivity.socket != null){
					try {
						MainActivity.socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}*/
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			//textResponse.setText(response);
			super.onPostExecute(result);
		}

	}
	public class MyClientTask_Disconnect extends AsyncTask<Void, Void, Void> {


		String response = "",nickname,password;
		GamePacket packet;
		
		
		public MyClientTask_Disconnect(String nickname,String password){
			this.nickname=nickname;
			this.password=password;
					
			
		}
		@Override
		protected Void doInBackground(Void... arg0) {


			try {
				packet =new GamePacket(this.nickname,this.password, disconnect, "", "game 1", -1);
				//writing object
				ObjectOutputStream outToServer = new ObjectOutputStream(MainActivity.socket.getOutputStream());
				outToServer.writeObject(packet);
			}
		
			catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response = "UnknownHostException: " + e.toString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response = "IOException: " + e.toString();
			}
			/*finally
			{
				if(MainActivity.socket != null){
					try {
						MainActivity.socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}*/
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			//textResponse.setText(response);
			super.onPostExecute(result);
		}

	}
	public class MyClientTask_getGameList extends AsyncTask<Void, Void, Void> {


		String response = "";
		GamePacket packet;


		public void setPacket(GamePacket packet){
			this.packet=packet;
		}

		@Override
		protected Void doInBackground(Void... arg0) {


			try {

				//reading "packet" object from client
				ObjectInputStream inFromClient = new ObjectInputStream(MainActivity.socket.getInputStream());

				packet=(GamePacket) inFromClient.readObject();


			}
			catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response = "UnknownHostException: " + e.toString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response = "IOException: " + e.toString();
			}
			/*finally
			{
				if(MainActivity.socket != null){
					try {
						MainActivity.socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}*/
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			//textResponse.setText(response);
			super.onPostExecute(result);
		}

	}


	public class MyClientTask_Connect extends AsyncTask<Void, Void, Void> {

		private String dstAddress;
		private int dstPort;
		private String response = "true";
		private String nickname,password;



		public  MyClientTask_Connect(String addr, int port,String nickname,String password){
			this.dstAddress = addr;
			this.dstPort = port;
			this.nickname=nickname;
			this.password=password;
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			try {

/**
				String isExists=GameDB.isExists(nickname,password);
				if(!isExists.equals("exists")){
					//textResponse.setText(isExists);
				}
*/

				MainActivity.socket = new Socket(dstAddress, dstPort);

				ObjectOutputStream outToServer = new ObjectOutputStream(MainActivity.socket.getOutputStream());
				outToServer.writeObject(new GamePacket(nickname, password,GamePacket.connect,null,"game 1",-1));



			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response = "UnknownHostException: " + e.toString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response = "IOException: " + e.toString();
			}
			/*finally{
					if(socket != null){
						try {
							socket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
			 */
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
		}
		public String getResponse(){
		return this.response;
		}

	}




	public boolean ConnectToServer(String addr, int port,String nickname,String password){
		MyClientTask_Connect connect=new MyClientTask_Connect(addr,port, nickname,password);
		connect.execute();
		return connect.getResponse().equals("true");
	}

	public  MyClientTask_ListenToPakcets getServerListener(){
		return new MyClientTask_ListenToPakcets();
	}
	
	public  MyClientTask_ListenToPakcets get(){
		return new MyClientTask_ListenToPakcets();
	}

	public MyClientTask_SendPakcet getServerDataSender(){
		return new MyClientTask_SendPakcet();
	}
	public void disconnectFromServer(String nickname,String password){
		MyClientTask_Disconnect disconnect=new MyClientTask_Disconnect(nickname, password);
		disconnect.execute();
	}
}
