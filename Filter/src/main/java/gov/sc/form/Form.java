package gov.sc.form;

import gov.sc.form.listener.BegButListener;
import gov.sc.form.listener.ExitMIListener;
import gov.sc.form.listener.HelpMIListener;
import gov.sc.form.listener.OpenMIListener;
import gov.sc.form.listener.ScanButListnener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

public class Form {
	public final JFrame jFrame = new JFrame("解析Excel文件");
	public final JTextField srcPthTxtFiled = new JTextField(30);
	public final JTextField tarColTxtFiled = new JTextField(8);
	public final JButton scanBut = new JButton("浏览");
	public final JButton begBut = new JButton("开始");
	public final JProgressBar progressbar = new JProgressBar(0, 1000);
	public final JMenu fileMenu = new JMenu("文件");;
	public final JMenu helpMenu = new JMenu("帮助");
	public final JMenuItem openMI = new JMenuItem("  " + "打开");;
	public final JMenuItem exitMI = new JMenuItem("  " + "退出");;
	public final JMenuItem helpMI = new JMenuItem("  " + "使用说明");;
	public final JLabel selectFile = new JLabel("请选择文本:");
	public final JLabel selectCol = new JLabel("请输入列标:");
	public final JLabel selectTim = new JLabel("请输入时间列标:");
	public final JTextField tarTimTxtFiled = new JTextField(8);

	public void createForm(){

		JPanel jpanelb = new JPanel();
		JPanel jpanelc = new JPanel();
		JMenuBar menuBar = new JMenuBar();
		progressbar.setStringPainted(true);
		progressbar.setForeground(Color.GREEN);
		jpanelb.add(selectFile);
		jpanelb.add(srcPthTxtFiled);
		jpanelb.add(scanBut);
		jpanelb.add(selectCol);
		jpanelb.add(tarColTxtFiled);
		jpanelb.add(selectTim);
		jpanelb.add(tarTimTxtFiled);
		jpanelb.add(begBut);
		jpanelc.add(progressbar);
		jFrame.add(jpanelb, BorderLayout.CENTER);
		jFrame.add(jpanelc, BorderLayout.SOUTH);
		jFrame.setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		fileMenu.add(openMI);
		fileMenu.add(exitMI);
		helpMenu.add(helpMI);

		progressbar.setSize(540, 5);
		srcPthTxtFiled.setSize(17, 5);
		selectFile.setFont(new Font("宋体", Font.BOLD, 18));
		scanBut.setFont(new Font("宋体", Font.BOLD, 15));
		selectCol.setFont(new Font("宋体", Font.BOLD, 18));
		selectTim.setFont(new Font("宋体", Font.BOLD, 18));
		begBut.setFont(new Font("宋体", Font.BOLD, 15));
		openMI.setIcon(new ImageIcon("./image/open.jpg"));
		exitMI.setIcon(new ImageIcon("./image/exit.jpg"));
		helpMI.setIcon(new ImageIcon("./image/help.jpg"));
		ImageIcon icon = new ImageIcon("./image/filter.jpg");
		jFrame.setIconImage(icon.getImage());
		jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// 整个程序关闭
		srcPthTxtFiled.setEditable(false);
		jFrame.pack();
		jFrame.setVisible(true);
		jFrame.setBounds(new Rectangle(100, 100, 540, 200));
		jFrame.setResizable(false);// 大小不变

	}

	public void setListener() {
		helpMI.addActionListener(new HelpMIListener());
		exitMI.addActionListener(new ExitMIListener(this));
		openMI.addActionListener(new OpenMIListener(this));
		scanBut.addActionListener(new ScanButListnener(this));
		begBut.addActionListener(new BegButListener(this));
	}

	public static void main(String[] args) {
		Form form = new Form();
		form.createForm();
		form.setListener();
	}
}
