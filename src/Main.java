import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javax.swing.JFrame;


public class Main {
	public static void main (String [] args) {
		JFrame app = new JFrame("Pac Man");
		app.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we){
				System.exit(0);
			}
		});
		PacModel world = new PacModel();
		PacView screen = new PacView(world);

		app.add(screen);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setBackground(Color.BLACK);
		app.pack();
		app.setResizable(false);
		app.setVisible(true);

		screen.addKeyListener(new PacListener(world));
		
		while(!world.gameOver()){
			world.tick();
			screen.repaint();

			try {
				Thread.sleep(25);
			}catch (InterruptedException e){
				e.printStackTrace();
			}


		}
	}
}
