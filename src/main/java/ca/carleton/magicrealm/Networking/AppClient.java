import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class AppClient implements Runnable {

	private int ID = 0;
	private Socket socket = null;
	private Thread thread = null;
	private BufferedReader streamIn = null;
	private BufferedWriter streamOut = null;
	private AppClient clnt = null;
	
	
	public AppClient(String serverName, int serverPort){
	
			try{
				this.socket = new Socket(serverName,serverPort);
				this.ID =socket.getLocalPort();
				System.out.println(ID +"Connected to Server:" +socket.getInetAddress());
				this.start();
			}
			catch(IOException ioe){
				System.out.println("Couuld not connect to serever");	
			}			
	}
	
	
	public void write(String message){
		try {
			streamOut.write(message);
			streamOut.newLine();
			streamOut.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String read() {
		String line = null;
		
		try {
			line = streamIn.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return line;
	}
	
	private void start(){
		try {
			streamIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			streamOut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			if(thread==null){
				thread = new Thread(this);
				thread.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void open() throws IOException{
		System.out.println(ID+":Opening buffer streams");
		streamIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		streamOut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		if(streamIn != null && streamOut != null){
			System.out.println("Buffered streams opened on thread:"+ ID);
		}
	}
	

	@Override
	public void run(){
		System.out.println(ID + ": Client Started");
		String line = read();
		while(line!= null){
			System.out.println(line);
			line = read();
		}		
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		AppClient clnt = new AppClient(Config.DEFAULT_HOST,Config.DEFAULT_HOST_PORT);
		clnt.open(); 
	}

}
