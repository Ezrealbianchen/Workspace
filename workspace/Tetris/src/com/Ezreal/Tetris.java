package com.Ezreal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Tetris extends JPanel{
	/*游戏当前状态*/
	private int state;
	public static final int RUNNING = 0;
	public static final int PAUSE  = 1;
	public static final int GAME_OVER = 2;
	
	
	private int score;
	private int lines;
	private Cell[][] wall;//二维数组引用
	private Tetromino tetromino;//正在下落的方块	
	private Tetromino nextOne;//下一个下落的方块
	
	//定时器
	private Timer timer;
	
	private int speed;
	private int level;
	
	//下落计数器
	private int index;
	
	private static BufferedImage background;
	public static BufferedImage T;
	public static BufferedImage S;
	public static BufferedImage I;
	public static BufferedImage L;
	public static BufferedImage J;
	public static BufferedImage O;
	public static BufferedImage Z;
	public static BufferedImage gameOver;
	public static BufferedImage pause;
	
	public static final int ROWS = 20;
	public static final int COLS = 10;
	
	
	//静态初始化块
	static {
		try {
			gameOver = ImageIO.read(Tetris.class.getResource("game-over.png"));
			pause = ImageIO.read(Tetris.class.getResource("pause.png"));
			
			background = ImageIO.read(Tetris.class.getResource("tetris.png"));
			T = ImageIO.read(Tetris.class.getResource("T.png"));
			I = ImageIO.read(Tetris.class.getResource("I.png"));
			S = ImageIO.read(Tetris.class.getResource("S.png"));
			Z = ImageIO.read(Tetris.class.getResource("Z.png"));
			J = ImageIO.read(Tetris.class.getResource("J.png"));
			L = ImageIO.read(Tetris.class.getResource("L.png"));
			O = ImageIO.read(Tetris.class.getResource("O.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//单帧绘制
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null);
		g.translate(15, 15);
		paintWall(g);
		paintTeromino(g);
		
		paintNextOne(g);
		paintScore(g);
		
		paintState(g);
		
	}
	
	private void paintState(Graphics g) {
		switch(state) {
		case PAUSE:
			g.drawImage(pause, -15, -15, null);
			break;
		case GAME_OVER:
			g.drawImage(gameOver, -15, -15, null);
			break;
		}
	}
	
	public static final int FONT_COLOR = 0X667799;
	public static final int FONT_SIZE = 30;
	
	private void paintScore(Graphics g) {
		int x = 290;
		int y = 160;
		g.setColor(new Color(FONT_COLOR));
		Font font = g.getFont();
		font = new Font(font.getName(),font.getStyle(),FONT_SIZE);
		g.setFont(font);
		String str = "SCORE:" + score;
		g.drawString(str, x, y);
		y += 56;
		str = "LINES:" + lines;
		g.drawString(str, x, y);
		
		y += 56;
		g.drawString("LEVEL:" + level, x, y);
	}
	
	public void action() {
		wall = new Cell[ROWS][COLS];//20*10
		tetromino = Tetromino.randomOne();	
		nextOne = Tetromino.randomOne();
		
		state = RUNNING;
		
		
		
		//键盘监听器
		KeyAdapter l = new KeyAdapter() {
			//匿名内部类。
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				switch (state) {
				case GAME_OVER:
					processGameoverKey(key);
					break;
				case PAUSE:
					processPauseKey(key);
					break;
				case RUNNING:
					processRunningkey(key);
				}
				repaint();
			}
		};
				
		
		//mark
		this.requestFocus();
		this.addKeyListener(l);
	
		timer = new Timer();
		
		//匿名内部类实现
		timer.schedule(new TimerTask() {
			public void run() {
				speed = 40 - (lines / 100);
				speed = speed < 1 ? 1 : speed;
				level = 41 -speed;
				if(state == RUNNING && index % speed == 0) {
					softDropAction();
				}
				index++;
				repaint();
			}
		},10,10);
		
		
		
		
		
		
	/*	使用常规方法实现		
		TimerTask t = new TimerTask(){
			public void run() {
				softDropAction();
				repaint();
			}
		};
		
		timer.schedule(t, 0,1000);*/
		
	
	}
	
	

	
	protected void processRunningkey(int key) {
		switch(key) {
		case KeyEvent.VK_Q:
			System.exit(0);
			break;
		case KeyEvent.VK_DOWN:
			softDropAction();
			break;
		case KeyEvent.VK_RIGHT:
			moveRightAction();
			break;
		case KeyEvent.VK_LEFT:
			moveLeftAction();
			break;
		case KeyEvent.VK_SPACE:
			hardDropAction();
			break;
		case KeyEvent.VK_UP:
			rotateRightAction();
			break;
		case KeyEvent.VK_Z:
			rotateLeftAction();
			break;
		case KeyEvent.VK_P:
			state = PAUSE;
			break;
		}
		
	}

	protected void processPauseKey(int key) {
		switch (key) {
		case KeyEvent.VK_Q:
			System.exit(0);
			break;
		case KeyEvent.VK_C:
			this.index = 0;
			state = RUNNING;
			break;
		}
		
	}

	protected void processGameoverKey(int key) {
		switch (key) {
		case KeyEvent.VK_Q:
			System.exit(0);
			break;
		case KeyEvent.VK_S:
			/*重新开始*/
			this.lines = 0;
			this.score = 0;
			this.wall = new Cell[ROWS][COLS];
			this.tetromino = tetromino.randomOne();
			this.nextOne = tetromino.randomOne();
			this.state = RUNNING;
			this.index = 0;
			break;
			
		}
		
	}

	//横坐标是否出界
	private boolean outOfBounds() {
		Cell[] cells = tetromino.cells;
		for(int i=0;i<cells.length;i++) {
			Cell cell = cells[i];
			int col = cell.getCol();
			if(col<0||col>=COLS) {
				return true;
			}
		}
		return false;
	}
	
	
	//是否重叠
	public boolean coincide() {
		Cell[] cells = tetromino.cells;
		for(int i=0;i<cells.length;i++) {
			Cell cell = cells[i];
			int row = cell.getRow();
			int col = cell.getCol();
			if(row>=0&&row<ROWS&&col>=0&&col<=COLS&&wall[row][col]!=null) {
				return true;//重叠
			}
		}
		return false;
	}
	
	//右移事件
	public void moveRightAction() {
		tetromino.moveRight();
		if(outOfBounds()||coincide()) {
			tetromino.moveLeft();
		}		
	}
	
	//左移事件
	public void moveLeftAction() {
		tetromino.moveLeft();
		if(outOfBounds()||coincide()) {
			tetromino.moveRight();
		}
	}
	
	//下落流程
	public void softDropAction() {
		if(canDrop()) {
			//如果可以下落则下落
			tetromino.softDrop();
		} else {
			landIntoWall();//置墙
			destoryLines();//销毁当前行
			
			if (isGameOver()) {
				state = GAME_OVER;
			}else {
				tetromino = nextOne;
			nextOne = Tetromino.randomOne();
			}
		}
			
	}
	
	//旋转流程控制
	public void rotateRightAction() {
		tetromino.rotateRight();//出现问题的时候，此步骤已经执行
		if(outOfBounds()||coincide()) {
			tetromino.rotateLeft();
		}
	}
	
	public void rotateLeftAction() {
		tetromino.rotateLeft();
		if(outOfBounds()||coincide()) {
			tetromino.rotateRight();
		}
	}
	
	
	/*检查游戏是否结束*/
	public boolean isGameOver() {
		for(int i=0;i<wall.length;i++) {
			if(wall[0][i] != null) {
				return true;
			}
		}
		return false;
	}
	
	//硬下落
	public void hardDropAction() {
		while(canDrop()){
			tetromino.softDrop();
		}
		landIntoWall();
		destoryLines();
		tetromino = nextOne;
		nextOne = Tetromino.randomOne();
	}
	
	private static int[] scoreTable = { 0,1,10,50,100};
	
	private void destoryLines() {
		int lines = 0;
		for(int row = 0;row<wall.length;row++) {
			if(fullCells(row)) {
				deleteRow(row);
				lines++;
			}
		}
		this.score += scoreTable[lines];
		this.lines += lines;
	}
	
	private void deleteRow(int row) {
		for(int i=row;i>=1;i--) {
			
			//将wall[i-1]行复制到wall[i]行中
			System.arraycopy(wall[i-1],0,wall[i],0,COLS);
		}
		
		//将null赋值给数组中的每一个元素
		Arrays.fill(wall[0], null);
	}
	
	private boolean fullCells(int row) {
		Cell[] line = wall[row];
		
		//foreach循环,判断一行元素是否充满
		for(Cell cell : line) {
			if(cell == null) {
				return false;
			}
		}
		return true;
	}
	
	
	//将方块落在墙上
	private void landIntoWall() {
		Cell[] cells = tetromino.cells;
		for(int i=0;i<cells.length;i++) {
			Cell cell = cells[i];
			int row = cell.getRow();
			int col = cell.getCol();
			wall[row][col] = cell;
		}
	}
	
	
	//是否能够下落
	private boolean canDrop() {
		Cell[] cells = tetromino.cells;
		for(int i=0;i<cells.length;i++) {
			Cell cell = cells[i];
			int row = cell.getRow();
			if(row == ROWS-1) {
				return false;
			}
		}
		for(Cell cell:cells) {
			int row = cell.getRow()+1 ;
			int col = cell.getCol();
			if(row >= 0 && row < ROWS && col >= 0 && col <= COLS
					&& wall[row][col]!=null) {
				return false;
			}
		}
		return true;
	}
	
	public static final int CELL_SIZE = 26;
	
	private void paintNextOne(Graphics g) {
		if(nextOne == null) {
			return;
		}
		Cell[] cells = nextOne.cells;
		for(int i=0;i<cells.length;i++) {
			Cell cell = cells[i];
			int x = (cell.getCol() + 10) * CELL_SIZE;
			int y = (cell.getRow() + 1 ) * CELL_SIZE;
			g.drawImage(cell.getImage(), x-1, y-1, null);
		}
	}
	
	private void paintWall(Graphics g) {
		//wall.length=20
		for(int row=0;row<wall.length;row++) {
			//wall是二维Cell的引用 wall[]是一维引用赋值给line[]可行
			Cell[] line = wall[row];
			//line代表墙每上一行
			for(int col=0;col<line.length;col++) {
				Cell cell = line[col];
				int x = col*CELL_SIZE;
				int y = row*CELL_SIZE;
				
				//如果当前位置没有方块则绘制边框
				if(cell == null) {
					g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
				}
				//否则绘制方块
				else {
					g.drawImage(cell.getImage(), x-1, y-1, null);
				}
				//g.drawString(row+","+col,x,y+CELL_SIZE);
			}
		}
	}
	
	public void paintTeromino(Graphics g) {
		if(tetromino == null) {
			return;
		}
		
		//将每个格子的row,col换算为x,y然后贴图
		Cell[] cells = tetromino.cells;
		for(int i=0;i<cells.length;i++) {
			//i=0 1 2 3
			Cell cell = cells[i];
			int x = cell.getCol()*CELL_SIZE;
			int y = cell.getRow()*CELL_SIZE;
			g.drawImage(cell.getImage(),x-1,y-1,null);
			
		}
	}
	
	
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("俄罗斯方块  by Ezreal");
		Tetris tetris = new Tetris();
		
		//测试用背景色
		//tetris.setBackground(new Color(0x0000ff));
		
		frame.add(tetris);
		frame.setSize(530, 580);
		
		
		//设置窗口的出现位置，默认则居中
		frame.setLocationRelativeTo(null);
		
		//退出并关闭
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//设置窗体可见，如果涉及画面经常改变的情况则放在方法尾部防止闪烁
		frame.setVisible(true);
		
		//置顶窗口
		frame.setAlwaysOnTop(true);
		
		//锁定窗口大小
		frame.setResizable(false);
		
		tetris.action();
		
	}
}
