package user;

import java.util.*;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import opDB.*;
import prepare.*;

public class main {

    private String filepic = null;//保存获取的图库路径
    private String filepath = null;//保存获取的文件路径
    /*胡path*/
    private String pathtxt = "C:\\Users\\Charles.h\\Desktop\\CBIR-Content-Based-Image-Retrieval--main\\path.txt";//保存路径
    private String colortxt = "C:\\Users\\Charles.h\\Desktop\\CBIR-Content-Based-Image-Retrieval--main\\color.txt";//保存获取的颜色特征
    private String texturetxt = "C:\\Users\\Charles.h\\Desktop\\CBIR-Content-Based-Image-Retrieval--main\\texture.txt";//保存获取的纹理特征
    private String shapetxt = "C:\\Users\\Charles.h\\Desktop\\CBIR-Content-Based-Image-Retrieval--main\\shape.txt";//保存获取的形状特征
    /*丁path
    private String pathtxt = "D:\\Desktop\\CBIR-Content-Based-Image-Retrieval--main\\path.txt";//保存路径
    private String colortxt = "D:\\Desktop\\CBIR-Content-Based-Image-Retrieval--main\\color.txt";//保存获取的颜色特征
    private String texturetxt = "D:\\Desktop\\CBIR-Content-Based-Image-Retrieval--main\\texture.txt";//保存获取的纹理特征
    private String shapetxt = "D:\\Desktop\\CBIR-Content-Based-Image-Retrieval--main\\shape.txt";//保存获取的形状特征*/

    JFrame frame = new JFrame("Tomato Leaf Disease Detection System");
    private JLabel selectedImageLabel;//选择的图片
    private JTextField imageLibraryTextField;//图库路径
    private JTextField imagePathTextField;//图片路径
    private JTextField colorWeightTextField;//图片路径
    private JTextField textureWeightTextField;//图片路径
    private JTextField shapeWeightTextField;//图片路径
    private JLabel currentImageLabel = new JLabel("Current Image:");
    JLabel[] img = new JLabel[12];
    JLabel[] imgpath = new JLabel[12];

    //输出的结论部分
    private JLabel identificationLabel;
    private JLabel plantAfftedtLabel;
    private JLabel finalResultLabel;

    double[] color = new double[9];//检索图像的颜色特征
    double[] texture = new double[9];//检索图像的纹理特征
    double[] shape = new double[8];//检索图像的形状特征

    private void picture_prepare() {

    }

    private void init() {

        frame.setBounds(100, 100, 1500, 920);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");

        // 设置窗口图标（同时影响任务栏和窗口左上角）
        frame.setIconImage(icon);
        //输出结果的部分
        identificationLabel = new JLabel("Final Identification Result:");
        identificationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        identificationLabel.setBounds(1185, 470, 250, 25);

        plantAfftedtLabel = new JLabel("The plant may be affected by");
        plantAfftedtLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        plantAfftedtLabel.setBounds(1185, 500, 300, 25);

        finalResultLabel = new JLabel();
        finalResultLabel.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        finalResultLabel.setBounds(1185, 520, 300, 50);

        JLabel imageLibraryLabel = new JLabel("Load Image Library:");
        imageLibraryLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        imageLibraryLabel.setBounds(15, 15, 140, 25);
        frame.add(imageLibraryLabel);

        imageLibraryTextField = new JTextField();
        imageLibraryTextField.setBounds(155, 15, 170, 25);
        frame.add(imageLibraryTextField);
        imageLibraryTextField.setColumns(10);

        JButton brows = new JButton("Browse");
        brows.setFont(new Font("Arial", Font.PLAIN, 15));
        brows.setBounds(325, 15, 85, 25);
        frame.add(brows);
        //弹窗按钮监听
        brows.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openImageLibraryDialog();
            }
        });

        JButton loadLibraryButton = new JButton("Load");
        loadLibraryButton.setFont(new Font("Arial", Font.PLAIN, 15));
        loadLibraryButton.setBounds(410, 15, 70, 25);
        frame.add(loadLibraryButton);
        //弹窗按钮监听
        loadLibraryButton.addActionListener(e -> {
            // 创建模态进度弹窗
            JDialog loadingDialog = new JDialog((Frame)null, "Processing", true); // true表示模态
            loadingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // 禁止关闭
            loadingDialog.setSize(300, 150);
            loadingDialog.setLocationRelativeTo(null); // 居中显示
            loadingDialog.setIconImage(icon);
            // 内容面板
            JPanel contentPane = new JPanel(new BorderLayout(10, 10));
            contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // 添加文本和进度条
            contentPane.add(new JLabel("Loading database...", JLabel.CENTER), BorderLayout.CENTER);

            JProgressBar progressBar = new JProgressBar();
            progressBar.setIndeterminate(true); // 不确定进度模式
            contentPane.add(progressBar, BorderLayout.SOUTH);

            loadingDialog.setContentPane(contentPane);

            // 在后台线程执行加载任务
            new Thread(() -> {
                try {
                    Loading(); // 执行数据库加载
                    // 加载完成后显示成功弹窗

                } catch (Exception ex) {
                    ex.printStackTrace();
                    // 异常处理（可选弹窗提示）

                } finally {
                    // 确保在EDT线程关闭弹窗
                    SwingUtilities.invokeLater(() -> {
                        loadingDialog.dispose();
                    });
                }
            }).start();

            // 显示模态弹窗（会阻塞用户操作其他窗口）
            loadingDialog.setVisible(true);
        });
        JSeparator bottomSeparator = new JSeparator(SwingConstants.HORIZONTAL);
        bottomSeparator.setBounds(0, 860, 1700, 2);  // 水平分割线
        bottomSeparator.setForeground(Color.BLACK);
        frame.add(bottomSeparator);
        JLabel readyLabel = new JLabel("ready");
        readyLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        readyLabel.setBounds(1430, 860, 120, 25);
        frame.add(readyLabel);

        JLabel selectlabel = new JLabel("Select File:");
        selectlabel.setFont(new Font("Arial", Font.PLAIN, 15));
        selectlabel.setBounds(490, 15, 120, 25);
        frame.add(selectlabel);

        imagePathTextField = new JTextField();
        imagePathTextField.setBounds(570, 15, 170, 25);
        frame.add(imagePathTextField);
        imagePathTextField.setColumns(10);

        JButton browButton = new JButton("Browse");
        browButton.setFont(new Font("Arial", Font.PLAIN, 15));
        browButton.setBounds(740, 15, 90, 25);
        frame.add(browButton);
        //弹窗按钮监听
        browButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    openImageFileDialog();
                } catch (Exception ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                }
            }
        });


        currentImageLabel.setVisible(false);
        currentImageLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        currentImageLabel.setBounds(1180, 170, 200, 25);
        frame.add(currentImageLabel);

        selectedImageLabel = new JLabel("");
        selectedImageLabel.setBounds(1180, 200, 256, 256);
        frame.add(selectedImageLabel);

        for (int i = 0; i < 12; i++) {
            img[i] = new JLabel();
            imgpath[i] = new JLabel();
        }

        JSeparator topSeparator = new JSeparator(SwingConstants.HORIZONTAL);
        topSeparator.setBounds(0, 50, 1700, 2);  // 水平分割线
        topSeparator.setForeground(Color.BLACK);
        frame.add(topSeparator);

        JLabel SingleFeaturelabel = new JLabel("Single Feature Retrieval");
        SingleFeaturelabel.setFont(new Font("Arial", Font.PLAIN, 15));
        SingleFeaturelabel.setBounds(235, 120, 200, 25);
        frame.add(SingleFeaturelabel);

        JButton colorRetrievalButton = new JButton("Color-Based Retrieval");
        colorRetrievalButton.setFont(new Font("Arial", Font.PLAIN, 15));
        colorRetrievalButton.setBounds(15, 70, 190, 30);
        frame.add(colorRetrievalButton);
        //颜色检索按钮监听
        colorRetrievalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    color();//基于颜色检索
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton textureRetrievalButton = new JButton("Texture-Based Retrieval");
        textureRetrievalButton.setFont(new Font("Arial", Font.PLAIN, 15));
        textureRetrievalButton.setBounds(220, 70, 200, 30);
        frame.add(textureRetrievalButton);
        //纹理检索按钮监听
        textureRetrievalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    texture();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }//基于纹理检索
            }
        });

        JButton shapeRetrievalButton = new JButton("Shape-Based Retrieval");
        shapeRetrievalButton.setFont(new Font("Arial", Font.PLAIN, 15));
        shapeRetrievalButton.setBounds(435, 70, 190, 30);
        frame.add(shapeRetrievalButton);
        //纹理检索按钮监听
        shapeRetrievalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    shape();
                } catch (Exception ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                }//基于纹理检索
            }
        });

        JLabel multi_features = new JLabel("Multi-Features Retrieval (Please set feature weights):");
        multi_features.setFont(new Font("Arial", Font.PLAIN, 15));
        multi_features.setBounds(700, 120, 370, 25);
        frame.add(multi_features);

        JLabel multi_features_color = new JLabel("Color Weight (0.00-1.00):");
        multi_features_color.setFont(new Font("Arial", Font.PLAIN, 15));
        multi_features_color.setBounds(650, 75, 180, 25);
        frame.add(multi_features_color);
        colorWeightTextField = new JTextField();
        colorWeightTextField.setBounds(830, 75, 50, 25);
        frame.add(colorWeightTextField);
        colorWeightTextField.setColumns(10);

        JLabel multi_features_texture = new JLabel("Texture Weight (0.00-1.00):");
        multi_features_texture.setFont(new Font("Arial", Font.PLAIN, 15));
        multi_features_texture.setBounds(900, 75, 190, 25);
        frame.add(multi_features_texture);
        textureWeightTextField = new JTextField();
        textureWeightTextField.setBounds(1100, 75, 50, 25);
        frame.add(textureWeightTextField);
        textureWeightTextField.setColumns(10);

        JLabel multi_features_shape = new JLabel("Shape Weight (0.00-1.00):");
        multi_features_shape.setFont(new Font("Arial", Font.PLAIN, 15));
        multi_features_shape.setBounds(1170, 75, 180, 25);
        frame.add(multi_features_shape);
        shapeWeightTextField = new JTextField();
        shapeWeightTextField.setBounds(1370, 75, 50, 25);
        frame.add(shapeWeightTextField);
        shapeWeightTextField.setColumns(10);

        JButton multiFeatureRetrievalButton = new JButton("Multi-Features Retrieval");
        multiFeatureRetrievalButton.setFont(new Font("Arial", Font.PLAIN, 15));
        multiFeatureRetrievalButton.setBounds(1100, 120, 250, 30);
        frame.add(multiFeatureRetrievalButton);
        //综合检索按钮监听
        multiFeatureRetrievalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    multi_features();
                } catch (Exception ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                }//基于综合检索
            }
        });

        JSeparator featureSeparator = new JSeparator(SwingConstants.HORIZONTAL);
        featureSeparator.setBounds(0, 160, 1700, 2);  // 水平分割线
        featureSeparator.setForeground(Color.BLACK);
        frame.add(featureSeparator);

        frame.setVisible(true);
    }

    public String openImageLibraryDialog() {
        JFileChooser chooser = new JFileChooser("E:\\");
        chooser.setDialogTitle("Please select gallery");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //弹出选择框
        int returnVal = chooser.showOpenDialog(null);
        //如果选择了文件
        if (JFileChooser.APPROVE_OPTION == returnVal) {
            filepic = chooser.getSelectedFile().toString();//获取所选文件路径
            imageLibraryTextField.setText(filepic);//把路径值写到textField中
            return filepic;
        } else {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Gallery has not been selected", "Tips", JOptionPane.PLAIN_MESSAGE);
            System.out.println("Gallery has not been selected");
            return null;
        }
    }

    //加载图库
    public void Loading() throws Exception {
        //找到图库中所有jpg文件
        File file = new File(pathtxt);
        if (file.exists()) {
            file.delete();
        }
        File colorFeatureFile = new File(colortxt);
        if (colorFeatureFile.exists()) {
            colorFeatureFile.delete();
        }
        File textureFeatureFile = new File(texturetxt);
        if (textureFeatureFile.exists()) {
            textureFeatureFile.delete();
        }
        File shapeFeatureFile = new File(shapetxt);
        if (shapeFeatureFile.exists()) {
            shapeFeatureFile.delete();
        }
        FileWriter writer = new FileWriter(pathtxt, true);
        new getPicture().find(writer, filepic, "\\S+\\.jpg");
        writer.close();
        //提取所有文件的特征到文件
        getColor colorFeatureExtractor = new getColor();
        getTexture textureFeatureExtractor = new getTexture();
        getShape shapeFeatureExtractor = new getShape();
        //颜色特征
        ArrayList<String> paths = new ArrayList<String>();//路径
        paths = colorFeatureExtractor.getPath(pathtxt);
        int length = paths.size();
        for (int i = 0; i < length; i++) {
            colorFeatureExtractor.save_feature(colorFeatureExtractor.color_HSV_msv(paths.get(i)), colortxt);
        }
        //纹理特征

        for (int i = 0; i < length; i++) {
            System.out.println(paths.get(i));
            textureFeatureExtractor.save_feature(textureFeatureExtractor.texture(paths.get(i)), texturetxt);
        }
        //形状特征

        for (int i = 0; i < length; i++) {
            System.out.println(paths.get(i));
            shapeFeatureExtractor.save_feature(shapeFeatureExtractor.shape(paths.get(i)), shapetxt);
        }
        //将数据写入数据库
        Insert in = new Insert();
        ArrayList<String> SQL = in.readFile(pathtxt);
        SQL = in.pathInsertsql(SQL);
        in.insertdb(SQL, "path");
        SQL = in.readFile(colortxt);
        SQL = in.colorInsertsql(SQL);
        in.insertdb(SQL, "color");
        SQL = in.readFile(texturetxt);
        SQL = in.textureInsertsql(SQL);
        in.insertdb(SQL, "texture");
        SQL = in.readFile(shapetxt);
        SQL = in.shapeInsertsql(SQL);
        in.insertdb(SQL, "shape");
    }

    public String openImageFileDialog() throws Exception {
        JFileChooser chooser = new JFileChooser("E:\\data");
        chooser.setDialogTitle("Please select the image file");
        //设置为只能选择图片文件
        FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg", "jpg");
        chooser.setFileFilter(filter);
        //弹出选择框
        int returnVal = chooser.showOpenDialog(null);
        //如果选择了文件
        if (JFileChooser.APPROVE_OPTION == returnVal) {
            filepath = chooser.getSelectedFile().toString();//获取所选文件路径
            imagePathTextField.setText(filepath);//把路径值写到textField中
            //在当前面板上显示所选图像
            ImageIcon currentImageIcon = new ImageIcon(filepath);
            int width = currentImageIcon.getIconWidth();
            int height = currentImageIcon.getIconHeight();
            currentImageLabel.setVisible(true);
            selectedImageLabel.setIcon(currentImageIcon);
            return filepath;
        } else {
            System.out.println("No selected file");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JOptionPane.showMessageDialog(null, "No selected file", "Tips", JOptionPane.PLAIN_MESSAGE);
            return null;
        }
    }

    //基于颜色检索
    public void color() throws Exception {
        frame.remove(identificationLabel);
        frame.remove(plantAfftedtLabel);
        frame.remove(finalResultLabel);
        if (filepath == null) {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JOptionPane.showMessageDialog(null, "No selected file", "Tips", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        color = getColor.color_HSV_msv(filepath);
        Map<Integer, Double> map = new TreeMap<Integer, Double>();//相似度
        Select select = new Select();
        ArrayList<double[]> colors = new ArrayList();
        colors = select.selectfeature("color");
        int num = colors.size();
        double d = 0;//形状特征的相似度值
        for (int i = 0; i < num; i++) {
            d = Compare_color(color, colors.get(i));
            map.put(i, d);
        }
        ArrayList<Entry<Integer, Double>> entryArrayList = new ArrayList<>(map.entrySet());
        Collections.sort(entryArrayList, new Comparator<Map.Entry<Integer, Double>>() {
            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                return o1.getValue().compareTo(o2.getValue());//按value从小到大排序
            }
        });
        int[] best = new int[12];//最匹配的12张照片id
        for (int i = 0; i < 12; i++) {
            Entry<Integer, Double> p = entryArrayList.get(i);
            best[i] = p.getKey();
        }
        ArrayList<String> paths = new ArrayList();
        paths = select.selectpath(select.pathSelectsql(best));

        // 从路径中提取病害信息
        ArrayList<String> diseases = new ArrayList<>();
        for (String path : paths) {
            String[] parts = path.split("\\\\");
            for (String part : parts) {
                if (part.contains("___")) {
                    diseases.add(part);
                    break; // 找到病害信息后就跳出内层循环
                }
            }
        }

        // 统计每种病害出现的频率
        Map<String, Integer> diseaseCount = new HashMap<>();
        for (String disease : diseases) {
            diseaseCount.put(disease, diseaseCount.getOrDefault(disease, 0) + 1);
        }

        // 找出出现频率最高的病害
        String finalDisease = null;
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : diseaseCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                finalDisease = entry.getKey();
            }
        }

        int i = 0;
        for (int y = 170; y < 860; y += 230) {
            for (int x = 15; x < 1175; x += 290) {
                if (i < 12) {
                    ImageIcon resultImageIcon = new ImageIcon(paths.get(i));
                    img[i].setBounds(x, y, 300, 200);
                    img[i].setIcon(resultImageIcon);
                    // 显示图像路径和病害信息
                    imgpath[i].setText("Graph "+ (i+1) + " Plant Disease: " + diseases.get(i));
                    //imgpath[i].setText(i + 1 + ":" + paths.get(i) + " 病害: " + diseases.get(i));
                    imgpath[i].setFont(new Font("Arial", Font.PLAIN, 12));
                    imgpath[i].setBounds(x, y + 190, 300, 40); // 调整高度以显示病害信息

                    frame.add(img[i]);
                    frame.add(imgpath[i]);
                    i++;
                }
            }
        }

        // 更新最终结果
        finalResultLabel.setText(finalDisease);

        // 重新添加标签
        frame.add(identificationLabel);
        frame.add(plantAfftedtLabel);
        frame.add(finalResultLabel);

        frame.validate();
        frame.repaint();
    }

    //基于纹理检索
    public void texture() throws Exception {
        frame.remove(identificationLabel);
        frame.remove(plantAfftedtLabel);
        frame.remove(finalResultLabel);
        if (filepath == null) {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JOptionPane.showMessageDialog(null, "No selected file", "Tips", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        texture = getTexture.texture(filepath);
        Map<Integer, Double> map = new TreeMap<Integer, Double>();//相似度
        Select select = new Select();
        ArrayList<double[]> texs = new ArrayList();
        texs = select.selectfeature("texture");
        int num = texs.size();
        double d = 0;//形状特征的相似度值
        for (int i = 0; i < num; i++) {
            d = cos_similar(texture, texs.get(i));
            map.put(i, d);
        }
        ArrayList<Entry<Integer, Double>> entryArrayList = new ArrayList<>(map.entrySet());
        Collections.sort(entryArrayList, new Comparator<Map.Entry<Integer, Double>>() {
            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());//按value从大到小排序
            }
        });
        int[] best = new int[12];//最匹配的12张照片id
        for (int i = 0; i < 12; i++) {
            Entry<Integer, Double> p = entryArrayList.get(i);
            best[i] = p.getKey();
        }
        ArrayList<String> paths = new ArrayList();
        paths = select.selectpath(select.pathSelectsql(best));
        /*for(int i=0;i<12;i++) {
        	System.out.println(paths.get(i));
        }*/

        ArrayList<String> diseases = new ArrayList<>();
        for (String path : paths) {
            String[] parts = path.split("\\\\");
            for (String part : parts) {
                if (part.contains("___")) {
                    diseases.add(part);
                    break; // 找到病害信息后就跳出内层循环
                }
            }
        }
        // 统计每种病害出现的频率
        Map<String, Integer> diseaseCount = new HashMap<>();
        for (String disease : diseases) {
            diseaseCount.put(disease, diseaseCount.getOrDefault(disease, 0) + 1);
        }

        // 找出出现频率最高的病害
        String finalDisease = null;
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : diseaseCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                finalDisease = entry.getKey();
            }
        }

        int i = 0;
        for (int y = 170; y < 860; y += 230) {
            for (int x = 15; x < 1175; x += 290) {
                if (i < 12) {
                    ImageIcon resultImageIcon = new ImageIcon(paths.get(i));
                    img[i].setBounds(x, y, 300, 200);
                    img[i].setIcon(resultImageIcon);

                    // 显示图像路径和病害信息
                    imgpath[i].setText("Graph "+ (i+1) + " Plant Disease: " + diseases.get(i));
                    //imgpath[i].setText(i + 1 + ":" + paths.get(i) + " 病害: " + diseases.get(i));
                    imgpath[i].setFont(new Font("Arial", Font.PLAIN, 12));
                    imgpath[i].setBounds(x, y + 190, 300, 40); // 调整高度以显示病害信息


                    frame.add(img[i]);
                    frame.add(imgpath[i]);
                    i++;
                }
            }
        }
        // 更新最终结果
        finalResultLabel.setText(finalDisease);

        // 重新添加标签
        frame.add(identificationLabel);
        frame.add(plantAfftedtLabel);
        frame.add(finalResultLabel);

        frame.validate();
        frame.repaint();
    }

    //基于形状检索
    public void shape() throws Exception {
        frame.remove(identificationLabel);
        frame.remove(plantAfftedtLabel);
        frame.remove(finalResultLabel);
        if (filepath == null) {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JOptionPane.showMessageDialog(null, "No selected file", "Tips", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        shape = new getShape().shape(filepath);
        Map<Integer, Double> map = new TreeMap<Integer, Double>();//相似度
        Select select = new Select();
        ArrayList<double[]> shapes = new ArrayList();
        shapes = select.selectfeature("shape");
        int num = shapes.size();
        double d = 0;//形状特征的相似度值
        for (int i = 0; i < num; i++) {
            d = cos_similar(shape, shapes.get(i));
            map.put(i, d);
        }
        ArrayList<Entry<Integer, Double>> entryArrayList = new ArrayList<>(map.entrySet());
        Collections.sort(entryArrayList, new Comparator<Map.Entry<Integer, Double>>() {
            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());//按value从大到小排序
            }
        });
        int[] best = new int[12];//最匹配的12张照片id
        for (int i = 0; i < 12; i++) {
            Entry<Integer, Double> p = entryArrayList.get(i);
            best[i] = p.getKey();
        }
        ArrayList<String> paths = new ArrayList();
        paths = select.selectpath(select.pathSelectsql(best));
        /*for(int i=0;i<12;i++) {
        	System.out.println(paths.get(i));
        }*/
        // 从路径中提取病害信息
        ArrayList<String> diseases = new ArrayList<>();
        for (String path : paths) {
            String[] parts = path.split("\\\\");
            for (String part : parts) {
                if (part.contains("___")) {
                    diseases.add(part);
                    break; // 找到病害信息后就跳出内层循环
                }
            }
        }

        // 统计每种病害出现的频率
        Map<String, Integer> diseaseCount = new HashMap<>();
        for (String disease : diseases) {
            diseaseCount.put(disease, diseaseCount.getOrDefault(disease, 0) + 1);
        }

        // 找出出现频率最高的病害
        String finalDisease = null;
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : diseaseCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                finalDisease = entry.getKey();
            }
        }
        int i = 0;
        for (int y = 170; y < 860; y += 230) {
            for (int x = 15; x < 1175; x += 290) {
                if (i < 12) {
                    ImageIcon resultImageIcon = new ImageIcon(paths.get(i));
                    img[i].setBounds(x, y, 300, 200);
                    img[i].setIcon(resultImageIcon);
                    // 显示图像路径和病害信息
                    imgpath[i].setText("Graph "+ (i+1) + " Plant Disease: " + diseases.get(i));
                    //imgpath[i].setText(i + 1 + ":" + paths.get(i) + " 病害: " + diseases.get(i));
                    imgpath[i].setFont(new Font("Arial", Font.PLAIN, 12));
                    imgpath[i].setBounds(x, y + 190, 300, 40); // 调整高度以显示病害信息


                    frame.add(img[i]);
                    frame.add(imgpath[i]);
                    i++;
                }
            }
        }
        // 更新最终结果
        finalResultLabel.setText(finalDisease);

        // 重新添加标签
        frame.add(identificationLabel);
        frame.add(plantAfftedtLabel);
        frame.add(finalResultLabel);

        frame.validate();
        frame.repaint();

    }

    //综合
    //综合
    public void multi_features() throws Exception {
        // 先移除旧的标签和图片结果
        frame.remove(identificationLabel);
        frame.remove(plantAfftedtLabel);
        frame.remove(finalResultLabel);
        if (filepath == null) {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JOptionPane.showMessageDialog(null, "No selected file", "Tips", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        // 检查权重输入
        String colorWeightInput = colorWeightTextField.getText();
        String textureWeightInput = textureWeightTextField.getText();
        String shapeWeightInput = shapeWeightTextField.getText();

        if (colorWeightInput.isEmpty() || textureWeightInput.isEmpty() || shapeWeightInput.isEmpty()) {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JOptionPane.showMessageDialog(null, "Feature proportion is not specified", "Tips", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        try {
            double colorWeight = Double.parseDouble(colorWeightInput);
            double textureWeight = Double.parseDouble(textureWeightInput);
            double shapeWeight = Double.parseDouble(shapeWeightInput);

            // 检查权重值是否在0-1之间且和为1
            if (colorWeight < 0 || colorWeight > 1 || textureWeight < 0 || textureWeight > 1 || shapeWeight < 0 || shapeWeight > 1) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                JOptionPane.showMessageDialog(null, "Weight values must be between 0.00 and 1.00", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 检查权重和是否为1（允许一定的浮点数误差）
            double totalWeight = colorWeight + textureWeight + shapeWeight;
            if (Math.abs(totalWeight - 1.0) > 0.0001) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                JOptionPane.showMessageDialog(null, "The sum of the three weights must equal 1.00", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 如果验证通过，继续执行检索
            color = getColor.color_HSV_msv(filepath);
            texture = getTexture.texture(filepath);
            shape = new getShape().shape(filepath);
            Map<Integer, Double> map = new TreeMap<Integer, Double>();//相似度
            Select select = new Select();
            ArrayList<double[]> colors = new ArrayList();
            colors = select.selectfeature("color");
            ArrayList<double[]> textures = new ArrayList();
            textures = select.selectfeature("texture");
            ArrayList<double[]> shapes = new ArrayList();
            shapes = select.selectfeature("shape");
            int num = shapes.size();
            double colorSimilarity = 0, textureSimilarity = 0, shapeSimilarity = 0, d = 0;

            for (int i = 0; i < num; i++) {
                colorSimilarity = cos_similar(color, colors.get(i));
                textureSimilarity = cos_similar(texture, textures.get(i));
                shapeSimilarity = cos_similar(shape, shapes.get(i));
                d = colorSimilarity * colorWeight + textureSimilarity * textureWeight + shapeSimilarity * shapeWeight;
                map.put(i, d);
            }

            ArrayList<Entry<Integer, Double>> entryArrayList = new ArrayList<>(map.entrySet());
            Collections.sort(entryArrayList, new Comparator<Map.Entry<Integer, Double>>() {
                public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                    return o2.getValue().compareTo(o1.getValue());//按value从大到小排序
                }
            });

            int[] best = new int[12];//最匹配的12张照片id
            for (int i = 0; i < 12; i++) {
                Entry<Integer, Double> p = entryArrayList.get(i);
                best[i] = p.getKey();
            }

            ArrayList<String> paths = new ArrayList();
            paths = select.selectpath(select.pathSelectsql(best));

            // 从路径中提取病害信息
            ArrayList<String> diseases = new ArrayList<>();
            for (String path : paths) {
                String[] parts = path.split("\\\\");
                for (String part : parts) {
                    if (part.contains("___")) {
                        diseases.add(part);
                        break; // 找到病害信息后就跳出内层循环
                    }
                }
            }

            // 统计每种病害出现的频率
            Map<String, Integer> diseaseCount = new HashMap<>();
            for (String disease : diseases) {
                diseaseCount.put(disease, diseaseCount.getOrDefault(disease, 0) + 1);
            }

            // 找出出现频率最高的病害
            String finalDisease = null;
            int maxCount = 0;
            for (Map.Entry<String, Integer> entry : diseaseCount.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    finalDisease = entry.getKey();
                }
            }

            int i = 0;
            for (int y = 170; y < 860; y += 230) {
                for (int x = 15; x < 1175; x += 290) {
                    if (i < 12) {
                        ImageIcon resultImageIcon = new ImageIcon(paths.get(i));
                        img[i].setBounds(x, y, 300, 200);
                        img[i].setIcon(resultImageIcon);
                        imgpath[i].setText("Graph "+ (i+1) + " Plant Disease: " + diseases.get(i));
                        imgpath[i].setFont(new Font("Arial", Font.PLAIN, 12));
                        imgpath[i].setBounds(x, y + 190, 300, 40); // 调整高度以显示病害信息

                        frame.add(img[i]);
                        frame.add(imgpath[i]);
                        i++;
                    }
                }
            }
            // 更新最终结果
            finalResultLabel.setText(finalDisease);

            // 重新添加标签
            frame.add(identificationLabel);
            frame.add(plantAfftedtLabel);
            frame.add(finalResultLabel);

            frame.validate();
            frame.repaint();

        } catch (NumberFormatException e) {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JOptionPane.showMessageDialog(null, "Please enter valid numbers (0.00-1.00)", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //比较特征值 欧氏距离
    public double Compare_color(double[] a, double[] b) {
        double D, sum = 0;
        for (int i = 0; i < 9; i++) {
            sum = sum + Math.pow((a[i] - b[i]), 2);
        }
        D = Math.pow(sum, 0.5);
        return D;
    }

    //用余弦定理求匹配图片与数据库中图片的相似度
    public double cos_similar(double[] queryVector, double[] databaseVector) {
        double cosvalue = 1, dotProduct = 0, queryNormSquared = 0, databaseNormSquared = 0;
        for (int i = 0; i < databaseVector.length; i++) {
            dotProduct += queryVector[i] * databaseVector[i];
            queryNormSquared += queryVector[i] * queryVector[i];
            databaseNormSquared += databaseVector[i] * databaseVector[i];
        }
        queryNormSquared = Math.sqrt(queryNormSquared);
        databaseNormSquared = Math.sqrt(databaseNormSquared);
        cosvalue = dotProduct / (queryNormSquared * databaseNormSquared);
        return cosvalue;
    }

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        Locale.setDefault(Locale.ENGLISH);
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        new main().init();
    }

}