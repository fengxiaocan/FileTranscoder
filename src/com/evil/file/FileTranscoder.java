package com.evil.file;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.util.ArrayList;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JList;
import javax.swing.JToggleButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JTextPane;
import javax.swing.JScrollPane;

public class FileTranscoder {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileTranscoder window = new FileTranscoder();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FileTranscoder() {
		initialize();
	}

	private ArrayList<String> sCharsetList = CharsetList.getCharsetList();
	private JButton btnStart;
	private JButton btnSelect;
	private JComboBox comboBox1;
	private JComboBox comboBox2;
	private JLabel label;
	private JLabel label_1;
	private int result = 0;
	private String selectPath = null;
	private JTextField textField_1;
	private JLabel label_2;
	private JTextArea textArea;

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(400, 200, 700, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		btnStart = new JButton("开始转码");
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectPath == null || selectPath.equals("")) {
					println("请选择转码文件路径");
				} else {
					startTrans();
				}
			}
		});
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setEditable(false);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setColumns(10);

		btnSelect = new JButton("选择转码文件或文件夹");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileSystemView fsv = FileSystemView.getFileSystemView(); // 注意了，这里重要的一句
				FileNameExtensionFilter filter1 = new FileNameExtensionFilter("text", "txt");
				fileChooser.addChoosableFileFilter(filter1);
				FileNameExtensionFilter filter2 = new FileNameExtensionFilter("java", "java");
				fileChooser.addChoosableFileFilter(filter2);
				FileNameExtensionFilter filter3 = new FileNameExtensionFilter("xml", "xml");
				fileChooser.addChoosableFileFilter(filter3);
				fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
				fileChooser.setDialogTitle("请选择要转码的文件或文件夹");
				fileChooser.setApproveButtonText("确定");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				result = fileChooser.showOpenDialog(frame);
				if (JFileChooser.APPROVE_OPTION == result) {
					selectPath = fileChooser.getSelectedFile().getPath();
					println("已选择转码文件路径:" + selectPath);
				}
			}
		});

		comboBox1 = new JComboBox();
		addCharsetItem(comboBox1);
		// 下拉选择器
		comboBox2 = new JComboBox();
		addCharsetItem(comboBox2);
		comboBox2.setSelectedIndex(1);

		label = new JLabel("选择原编码");

		label_1 = new JLabel("选择转编码");

		textField_1 = new JTextField();

		label_2 = new JLabel("输入过滤后缀名");

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE).addGap(14)
						.addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE).addGap(53)
						.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE).addGap(6)
						.addComponent(comboBox2, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(106, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup().addGap(298).addComponent(btnStart).addContainerGap(363,
						Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup().addGap(219).addComponent(label_2).addGap(18)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(236, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout
						.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(groupLayout.createSequentialGroup().addGap(8).addComponent(btnSelect)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 505, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.LEADING,
								groupLayout.createSequentialGroup().addContainerGap().addComponent(scrollPane,
										GroupLayout.PREFERRED_SIZE, 662, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(70, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(13)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnSelect, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
				.addGap(10)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(comboBox2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBox1, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup().addGap(1).addComponent(label_1,
								GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE).addComponent(label_2))
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnStart)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 487, GroupLayout.PREFERRED_SIZE).addGap(12)));

		textArea = new JTextArea();
		textArea.setMargin(new Insets(10, 10, 10, 10));
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
		textArea.setLineWrap(false);
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		frame.getContentPane().setLayout(groupLayout);
	}

	public void startTrans() {
		textArea.setText("");
		final File transFile = new File(selectPath);
		if (transFile == null) {
			println("选择转码文件错误!");
		} else {
			if (transFile.isFile()) {
				println("开始转码!");
				newFileToUtf(transFile);
				println("转码完成!");
			} else {
				new Thread(new Runnable() {
					public void run() {
						println("开始转码!");
						String dir = transFile.getAbsolutePath();
						String parent = dir + "copy";
						File parentDir = new File(parent);
						findFile(transFile, parentDir);
						println("转码完成!");
					}
				}).start();
			}
		}
	}

	/**
	 * 给下拉选择框设置选择文本
	 * 
	 * @param jcb
	 *            选择器
	 */
	private void addCharsetItem(JComboBox jcb) {
		for (String string : sCharsetList) {
			jcb.addItem(string);
		}
	}

	/**
	 * 打印
	 * 
	 * @param str
	 *            打印文本
	 */
	public void print(String str) {
		textArea.append(str);
	}

	public void println(String str) {
		textArea.append(str);
		textArea.append("\r\n");
	}

	/**
	 * 查找文件
	 * 
	 * @param file
	 *            文件
	 * @param filePar
	 *            父文件
	 */
	public void findFile(File file, File filePar) {
		if (file == null) {
			return;
		}
		File[] files1 = file.listFiles(new java.io.FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if (pathname.isDirectory()) {
					return true;
				}
				String wtfName = textField_1.getText();
				if (wtfName == null || wtfName.equals("")) {
					return true;
				}
				if (pathname.getName().endsWith(wtfName)) {
					return true;
				}
				return false;
			}
		});
		if (files1 == null) {
			return;
		}
		for (File file1 : files1) {
			if (file1.isDirectory()) {
				File dir = new File(filePar, file1.getName());
				dir.mkdirs();
				findFile(file1, dir);
			} else {
				newFileToUtf(file1, filePar);
			}
		}
	}

	/**
	 * 把文件转换为新的编码 执行单个文件的转换
	 * 
	 * @param file
	 */
	public void newFileToUtf(File file) {
		try {
			println("正在转码:" + file.getAbsolutePath());
			FileInputStream stream = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(stream, (String) comboBox1.getSelectedItem()));
			String name = file.getName();
			int pos = name.lastIndexOf('.');
			String newName = name.substring(0, pos) + "_copy" + name.substring(pos, name.length());
			FileOutputStream os = new FileOutputStream(new File(file.getParent(), newName));
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(os, (String) comboBox2.getSelectedItem()));

			char[] arr = new char[1024 * 8];
			int len;
			while ((len = reader.read(arr)) > 0) {
				writer.write(arr, 0, len);
				writer.flush();
			}
			reader.close();
			writer.close();
		} catch (Exception e) {
			println("转码异常:" + e.getMessage());
			println("转码异常文件:" + file.getAbsolutePath());
		}
	}

	/**
	 * 把文件转换为新的编码 执行文件夹下多个文件的转换
	 * 
	 * @param file
	 */
	public void newFileToUtf(File file, File filePar) {
		if (!filePar.exists()) {
			filePar.mkdirs();
		}
		try {
			println("正在转码:" + file.getAbsolutePath());
			FileInputStream stream = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(stream, (String) comboBox1.getSelectedItem()));
			FileOutputStream os = new FileOutputStream(new File(filePar, file.getName()));
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(os, (String) comboBox2.getSelectedItem()));

			char[] arr = new char[1024 * 8];
			int len;
			while ((len = reader.read(arr)) > 0) {
				writer.write(arr, 0, len);
				writer.flush();
			}
			reader.close();
			writer.close();
		} catch (Exception e) {
			println("转码异常:" + e.getMessage());
			println("转码异常文件:" + file.getAbsolutePath());
		}
	}
}
