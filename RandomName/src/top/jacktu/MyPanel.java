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
	private static String currentName = "�����ʼ";
	private static final long serialVersionUID = 1519189380171034209L;
	private static MyPanel panel;
	private static JFrame frame;
	private static int finalTime = 5 * 1000;
	// ���ڿ��
	public static final int WID = 350;
	public static final int HEI = 250;
	// �ȴ���ʾ����
	private static List<Person> people;
	// ������Ϣ
	public static String errInfo = "";
	// ����ͼ��
	public static BufferedImage icon;

	private static void init() {
		people = new ArrayList<Person>();
		try {
			icon = ImageIO.read(top.jacktu.MyPanel.class.getResource("jacktu.jpg"));
		} catch (IOException ioe) {
		}
		try {
			// ��ʼ������
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
				errInfo += "\nû����Ա����!";
			}
		} catch (FileNotFoundException e) {
			errInfo += "\nnames.txt�ļ�δ�ҵ�!";
		} catch (UnsupportedEncodingException e) {
			errInfo += "\n��֧�ֵı����쳣!";
		} catch (IOException e) {
			errInfo += "\nδ֪��IO�쳣!";
		}
	}

	/** ���㾭����ʱ�� ,���� */
	private static int millisecond = 0;

	private void event() {
		MouseAdapter l = new MouseAdapter() {
			/** ����¼� */
			@Override
			public void mouseClicked(MouseEvent e) {
				// ������ʼ
				start();
			}
		};
		this.addMouseListener(l);// ֧�ֵ���,����,�Ƴ��¼�
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

	/** ����ı���ʾ�е����� */
	private void changeName() {
		int ran = (int) (Math.random() * people.size());
		currentName = people.get(ran).getName();
	}

	@Override
	public void paint(Graphics g) {
		// �д�����Ϣ��ʾ������Ϣ
		if (!"".equals(errInfo)) {
			g.setColor(Color.RED);
			g.setFont(new Font("����", Font.BOLD, 20));
			g.drawString(errInfo, 40, 20);
		} else if (currentName.equals("�����ʼ")) {
			g.setColor(new Color(180, 184, 185));
			g.fillRect(-1, -1, WID + 1, HEI + 1);
			g.setColor(new Color(8, 135, 203));
			g.setFont(new Font("����", Font.BOLD, 60));
			g.drawString(currentName, WID / 8, HEI / 100 * 60);
		} else {
			g.setColor(new Color(180, 184, 185));
			g.fillRect(-1, -1, WID + 1, HEI + 1);
			g.setColor(new Color(8, 135, 203));
			int font_size = 240/currentName.length();
			if (currentName.length()>3) {
				g.setFont(new Font("����", Font.BOLD, font_size));
			}else if(currentName.length()>2){
				g.setFont(new Font("����", Font.BOLD, font_size));
			}else {
				g.setFont(new Font("����", Font.BOLD, font_size));
			}
			g.drawString(currentName, 45 , 135);
		}
	}

	/** ������ʾ�ķ��� */
	private MyPanel showWin() {
		MyPanel win = new MyPanel();
		JFrame frame = new JFrame("����");
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
		frame = new JFrame("�������");
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
