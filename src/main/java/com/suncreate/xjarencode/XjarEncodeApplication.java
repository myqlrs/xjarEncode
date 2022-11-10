package com.suncreate.xjarencode;

import io.xjar.XConstants;
import io.xjar.XKit;
import io.xjar.boot.XBoot;
import io.xjar.key.XKey;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.security.NoSuchAlgorithmException;

public class XjarEncodeApplication{

    private static String password = "m18855133103";
    private static JButton openItem, saveItem;
    private static FileDialog openDia, saveDia;// 定义“打开、保存”对话框
    private static String filePathOld;//旧文件路径
    private static String filePathNew;//新文件路径
    private static String fileName;//文件名
    static JFrame f = null;
    static JTextField locationText, typeText;

    public static void main(String[] args) {

        int gap = 5;
        f = new JFrame("加密程序");
        ImageIcon icon = new ImageIcon("src\\images\\favicon.ico");
        f.setIconImage(icon.getImage());// 给窗体设置图标方法
        f.setSize(550, 310);
        f.setLocation(200, 200);
        f.setLayout(null);

        JPanel pInput = new JPanel();
        pInput.setBounds(gap, gap, 520, 60);
        pInput.setLayout(new GridLayout(2, 3, gap, gap));


        JLabel location = new JLabel("JAR路径:");
        locationText = new JTextField(10);
        openItem = new JButton("选取");// 创建“打开"菜单项

        JLabel type = new JLabel("保存路径:");
        typeText = new JTextField(10);
        saveItem = new JButton("选取");// 创建“保存"菜单项

        openDia = new FileDialog(f, "打开", FileDialog.LOAD);
        saveDia = new FileDialog(f, "保存", FileDialog.SAVE);

        JButton b = new JButton("生成");

        pInput.add(location);
        pInput.add(locationText);
        pInput.add(openItem);
        pInput.add(type);
        pInput.add(typeText);
        pInput.add(saveItem);

        //文本域
        final JTextArea ta = new JTextArea();
        ta.setLineWrap(true);
        b.setBounds(220, 60 + 30, 80, 30);
        ta.setBounds(gap, 80 + 60, 523, 120);

        f.add(pInput);
        f.add(b);
        f.add(ta);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        myEvent();
        //鼠标监听
        b.addActionListener(new ActionListener() {
            boolean checkedpass = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                XKey xKey = null;
                System.out.println(" --- : "+fileName);
                String newPath = filePathNew+"\\"+fileName+"-xjar.jar";
                String oldPath = filePathOld;
                try {
                    xKey = XKit.key(password);
                    XBoot.encrypt(oldPath, newPath, xKey, XConstants.MODE_DANGER);
                    ta.append(" Success! ");
                } catch (NoSuchAlgorithmException eg) {
                    eg.printStackTrace();
                } catch (Exception eg) {
                    eg.printStackTrace();
                }
                checkedpass = true;
                checkEmpty(locationText, "JAR路径");
                checkEmpty(typeText, "保存路径");

                if (checkedpass) {
                    String model = "加密jar包保存路径 ：%s";
                    String result = String.format(model, newPath);
                    ta.setText("");
                    ta.append(result);
                }

            }

            //检验是否为空
            private void checkEmpty(JTextField tf, String msg) {
                if (!checkedpass) {
                    return;
                }
                String value = tf.getText();
                if (value.length() == 0) {
                    JOptionPane.showMessageDialog(f, msg + " 不能为空");
                    tf.grabFocus();
                    checkedpass = false;
                }
            }
        });
    }

    //选取按钮监听
    private static void myEvent() {

        // 选取监听
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDia.setVisible(true);//显示打开文件对话框
                String dirpath = openDia.getDirectory();//获取打开文件路径并保存到字符串中。
                String Name = openDia.getFile();//获取打开文件名称并保存到字符串中
                System.out.println(" --- : " + Name.split(".jar")[0]);
                fileName = Name.split(".jar")[0];
                filePathOld = dirpath+Name;
                if (dirpath == null || Name == null)//判断路径和文件是否为空
                    return;

                locationText.setText(dirpath+Name);
            }
        });

        // 选取监听
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                chooser.showDialog(new JLabel(), "选择");
                File file = chooser.getSelectedFile();
                if (file == null) {
                    System.out.println(" 路径 ："+file.getAbsoluteFile().toString());
                    typeText.setText(file.getAbsoluteFile().toString());
                    filePathNew = file.getAbsoluteFile().toString();
                }else{
                    System.out.println(" 路径 ："+file.getAbsoluteFile().toString());
                    typeText.setText(file.getAbsoluteFile().toString());
                    filePathNew = file.getAbsoluteFile().toString();
                }
            }
        });
        // 窗体关闭监听
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
