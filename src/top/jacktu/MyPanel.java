package top.jacktu;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class MyPanel extends JPanel {
	private static String currentName = "点击开始";
	private static final long serialVersionUID = 1519189380171034209L;
	private static MyPanel panel;
	private static JFrame frame;
	private static int finalTime = 5 * 1000;
	// 窗口宽高
	public static final int WID = 350;
	public static final int HEI = 250;
	// 等待显示的人
	private static List<Person> people;
	// 错误信息
	public static String errInfo = "";
	// 窗口图标
	public static BufferedImage icon;

	private static void init() {
		people = new ArrayList<Person>();
		try {
			icon = ImageIO.read(top.jacktu.MyPanel.class.getResource("jacktu.jpg"));
		} catch (IOException ioe) {
		}
		try {
			// 初始化姓名
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File("names.txt")), "GBK"));
			String name = "";
			while ((name = reader.readLine()) != null) {
				name = name.trim();
				if (name != null && !"".equals(name)) {
					Person p = new Person();
					p.setName(name);
					people.add(p);
				}
			}
			reader.close();
			if (people.isEmpty()) {
				errInfo += "\n没有人员姓名!";
			}
		} catch (FileNotFoundException e) {
			errInfo += "\nnames.txt文件未找到!";
		} catch (UnsupportedEncodingException e) {
			errInfo += "\n不支持的编码异常!";
		} catch (IOException e) {
			errInfo += "\n未知的IO异常!";
		}
	}

	/** 计算经过的时间 ,毫秒 */
	private static int millisecond = 0;

	private void event() {
		MouseAdapter l = new MouseAdapter() {
			/** 鼠标事件 */
			@Override
			public void mouseClicked(MouseEvent e) {
				// 单击开始
				start();
			}
		};
		this.addMouseListener(l);// 支持单击,移入,移出事件
	}

	private void start() {
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				millisecond += 50;
				changeName();
				if (millisecond % finalTime == 0) {
					timer.cancel();
				}
				repaint();
			}
		}, 50, 50);
	}

	/** 随机改变显示中的人名 */
	private void changeName() {
		int ran = (int) (Math.random() * people.size());
		currentName = people.get(ran).getName();
	}

	@Override
	public void paint(Graphics g) {
		// 有错误信息显示错误信息
		if (!"".equals(errInfo)) {
			g.setColor(Color.RED);
			g.setFont(new Font("楷体", Font.BOLD, 20));
			g.drawString(errInfo, 40, 20);
		} else if (currentName.equals("点击开始")) {
			g.setColor(new Color(180, 184, 185));
			g.fillRect(-1, -1, WID + 1, HEI + 1);
			g.setColor(new Color(8, 135, 203));
			g.setFont(new Font("楷体", Font.BOLD, 60));
			g.drawString(currentName, WID / 8, HEI / 100 * 60);
		} else {
			g.setColor(new Color(180, 184, 185));
			g.fillRect(-1, -1, WID + 1, HEI + 1);
			g.setColor(new Color(8, 135, 203));
			int font_size = 240/currentName.length();
			if (currentName.length()>3) {
				g.setFont(new Font("楷体", Font.BOLD, font_size));
			}else if(currentName.length()>2){
				g.setFont(new Font("楷体", Font.BOLD, font_size));
			}else {
				g.setFont(new Font("楷体", Font.BOLD, font_size));
			}
			g.drawString(currentName, 45 , 135);
		}
	}

	/** 弹窗显示的方法 */
	private MyPanel showWin() {
		MyPanel win = new MyPanel();
		JFrame frame = new JFrame("错误");
		frame.setIconImage(null);
		frame.setSize(WID, HEI);
		frame.add(win);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		return win;
	}
	public static void main(String[] args) {
		init();
		panel = new MyPanel();
		frame = new JFrame("随机姓名");
		frame.setIconImage(icon);
		frame.setSize(WID, HEI);
		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		if (!"".equals(errInfo) || people.isEmpty()) {
			panel.showWin();
		} else {
			frame.setVisible(true);
			panel.event();
		}
	}
}
