/**
 * This file is part of SounDJ.
 *
 * SounDJ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SounDJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SounDJ.  If not, see <http://www.gnu.org/licenses/>.
 */
package ie.davidlynch.soundj.server.icecast;

import ie.davidlynch.soundj.model.Play;
import ie.davidlynch.soundj.model.icecast.IceCastPlayBuilder;
import ie.davidlynch.soundj.server.IMediaServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * IceCast Media Server. This class is interested in the meta-data surrounding the broadcast only, and
 * makes no attempt to relay the encoded stream data. 
 *
 * @author David Lynch
 */
public class IceCastMediaServer implements IMediaServer, Runnable 
{
	protected static Logger logger =  LogManager.getLogger(IceCastMediaServer.class);
	
	private final int 				port;
	private ServerSocket 			serverSocket;
	private PrintWriter 			out;
	private BufferedReader 			in;
	private Socket		 			clientSocket;
	private BlockingQueue<Play>		playsQueue;
	
	private AtomicBoolean   isRunning = new AtomicBoolean(true);
	
	private Thread			thread;
	
	public IceCastMediaServer(int port, BlockingQueue<Play> playsQueue) 
	{
		this.port = port;
		thread = new Thread(this);
		this.playsQueue=playsQueue;
	}
	
	public void bind() throws IOException 
	{
		logger.info("Binding IceServer to port " + port);
		serverSocket = new ServerSocket(port);
	}
	
	public void run() 
	{
		while(isRunning.get()) 
		{
			try {
				acceptConnection();
				prepareConversationStreams();
				processData();
				sendOkToClient();
			}
			catch(Exception ex) 
			{
				logger.error("Error processing connection from client.", ex);
			}
			finally {
				try 
				{
					logger.info("Cleaning up");
					if(clientSocket!=null && (clientSocket.isConnected() || clientSocket.isBound())) { clientSocket.close(); };
					if(in!=null) { in.close(); };
					if(out!=null) { out.close(); };
				}
				catch(Exception ex) {
					logger.error("Error cleaning up connection.", ex);
				}
			}
		}
	}
	
	private void acceptConnection() throws IOException
	{
		logger.info("Waiting on client connection.");
		clientSocket = serverSocket.accept();
	}
	
	private void prepareConversationStreams() throws IOException
	{
		logger.info("Preparing conversation streams.");
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in 	= new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}
	
	private void processData() throws IOException
	{
		logger.info("Processing data.");
		String inputLine;
		while((inputLine = in.readLine()) != null && !inputLine.isEmpty()) 
		{
			if(inputLine.contains("metadata")) {
				Play play = IceCastPlayBuilder.build(inputLine);
				if(!play.isNull())
					playsQueue.offer(play);
			}
		}
	}
	
	private void sendOkToClient() 
	{
		out.print("HTTP/1.0 200 OK\r\n\r\n");
		out.flush();
		out.close();
	}

	@Override
	public void start() throws Exception {
		bind();
		thread.start();
	}

	@Override
	public void stop() 
	{
		isRunning.getAndSet(false);
	}
	
	@Override
	public void join() 
	{
		try {
			thread.join();
		} catch (InterruptedException e) 
		{
			logger.info("Processing data.");
		}
	}
}
