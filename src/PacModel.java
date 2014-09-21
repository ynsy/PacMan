import java.awt.Color;
import java.util.*;

public class PacModel {
	private String map =
						"##################################\n"
					+	"#o..............................o#\n"
					+	"#..###................##.........#\n"
					+	"#.################################\n"
					+	"#......##........................##\n"
					+	"#................................#\n"
					+	"#................................#\n"
					+	"#................................#\n"
					+	"#................................#\n"
					+	"#......####...........####.......#\n"
					+	"#................................#\n"
					+	"#.....................####.......#\n"
					+	"#......####......................#\n"
					+	"#......#############.....#########\n"
					+	"#o..............................o#\n"
					+   "##################################\n";

	private ArrayList<ArrayList<Character>> board = new ArrayList<ArrayList<Character>>();
	public int time = 0;
	public float pacx = 5, pacy = 6;
	public final static int STILL = 0, UP =1, RIGHT =2, DOWN = 3, LEFT = 4;
	public int pacdir = RIGHT;
	public int nextpacdir = pacdir;
	public int pacmouth = 10;
	private boolean closing = true;

	public ArrayList<Ghost> ghosts = new ArrayList<Ghost>();

	public PacModel(){
		for(String row : map.split("\n")){
			ArrayList<Character> r = new ArrayList<Character>();
			for(int i =0; i<row.length(); i++){
				r.add(row.charAt(i));
			}
			board.add(r);
		}
		ghosts.add(new Ghost(3,10,Color.PINK));
		ghosts.add(new Ghost(3,12,Color.CYAN));

	}

	public int getHeight() {
		return board.size();
	}

	public int getWidth() {
		return board.get(0).size();
	}

	private void setCell(int x, int y, char c) {
		board.get(y).set(x, c);

	}

	public char getCell(int x, int y) {
		return board.get(y).get(x);
	}

	public void tick() {

		time += 1;

		if(pacdir == RIGHT && nextpacdir == LEFT)
			pacdir = LEFT;
		else if (pacdir == LEFT && nextpacdir == RIGHT)
			pacdir = RIGHT;
		else if (pacdir == UP && nextpacdir == DOWN)
			pacdir = DOWN;
		else if (pacdir == DOWN && nextpacdir == UP)
			pacdir = UP;
		else if((Math.abs(pacx - (int)pacx)<.1) && (Math.abs(pacy - (int)pacy)<.1))
			pacdir = nextpacdir;

		if(pacmouth == 0){
			closing = false;
		}else if(pacmouth == 10){
			closing = true;
		}
		if(closing)
			pacmouth--;
		else 
			pacmouth++;

		int x =Math.round(pacx);
		int y =Math.round(pacy);

		if(getCell(x,y) == '.')
			setCell(x,y,' ');

		if((pacdir == RIGHT && getCell(x+1,y) == '#' && pacx >= x) 
				|| (pacdir == LEFT && getCell(x-1,y) == '#' && pacx <= x) 
				|| (pacdir == UP && getCell(x,y-1) == '#' && pacy <= y) 
				|| (pacdir == DOWN && getCell(x,y+1) == '#' && pacy >= y)) {
			pacdir = STILL;
			pacx = x;
			pacy = y;
		}


		// move pacman
		if(pacdir == RIGHT){
			pacx +=.1;
		}else if(pacdir == LEFT){
			pacx -=.1;
		}else if(pacdir == UP){
			pacy -=.1;
		}else if(pacdir == DOWN){
			pacy +=.1;
		}

		for(Ghost g : ghosts){
			if(Math.abs(g.x - (int)g.x)< .1 && Math.abs(g.y - (int)g.y)< .1){
				g.chooseDir(this);
			}
			if(g.dir == LEFT)
				g.x -=.08;
			else if (g.dir == RIGHT)
				g.x += .08;
			else if (g.dir == UP)
				g.y -= .08;
			else
				g.y += .08;
		}
	}


	public boolean gameOver() {
		return false;
	}
}
