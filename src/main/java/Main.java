import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.xml.soap.Text;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Изменение размера картинки");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setPreferredSize(new Dimension(400, 600));
        frame.pack();
        frame.setResizable(false);
        frame.setLocation(500,100);
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.pink);
        mainPanel.setLayout(new FlowLayout());
        mainPanel.setSize(399, 399);
        JPanel wPanel = new JPanel();
        wPanel.setBorder(new TitledBorder("Ширину на:"));
        wPanel.setLayout(new FlowLayout());
        wPanel.setSize(100, 20);
        JPanel hPanel = new JPanel();
        hPanel.setBorder(new TitledBorder("Высоту на:"));
        hPanel.setLayout(new FlowLayout());
        hPanel.setSize(100, 20);
        JPanel pPanel = new JPanel();
        pPanel.setBorder(new TitledBorder("Папка"));
        pPanel.setSize(300, 40);
        TextField widthField = new TextField();
        TextField heightField = new TextField();
        TextField pathField = new TextField("");
        widthField.setPreferredSize(new Dimension(100, 20));
        heightField.setPreferredSize(new Dimension(100, 20));
        pathField.setPreferredSize(new Dimension(299, 20));
        wPanel.add(widthField);
        hPanel.add(heightField);
        pPanel.add(pathField);
        mainPanel.add(pPanel);
        mainPanel.add(wPanel);
        mainPanel.add(hPanel);
        TextArea messeges = new TextArea("Логи" + "\n" + "_________________________" + "\n");
        messeges.setPreferredSize(new Dimension(300, 300));
        JButton button = new JButton("Изменить");
        button.addActionListener(e -> {
            String path = pathField.getText();
            if (path.equals("")) {
                messeges.append("Введите адрес папки!");
                throw new IllegalArgumentException("Empty pathField!");
            }
            int nWidth;
            int nHeight;
            if (widthField.getText().equals("")){
                nWidth = 0;
            }
            else {
                nWidth = Integer.parseInt(widthField.getText());
            }
            if(heightField.getText().equals("")){
                nHeight = 0;
            }
            else {
                nHeight = Integer.parseInt(heightField.getText());
            }
            if (nWidth == 0 && nHeight == 0) messeges.append("Изменено: 0; заполните поля!" + "\n" );
            File folder = new File(path);
            File[] listOfFiles = folder.listFiles();
            assert listOfFiles != null;
            messeges.append("Найдено файлов: " + listOfFiles.length + "\n");
            BufferedImage img = null;
            BufferedImage tempPNG = null;
            BufferedImage tempJPG = null;
            File newFilePNG = null;
            File newFileJPG = null;
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    messeges.append("_________________________" + "\n" + "File " + listOfFiles[i].getName() +
                            "\n" );
                    try {
                        img = ImageIO.read(new File(path + listOfFiles[i].getName()));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                        messeges.append(e1.getMessage() + "\n");
                    }

                    if (Objects.requireNonNull(img).getWidth() < 700 || img.getHeight() < 700) {
                        tempJPG = Resize.resizeImage(img, Objects.requireNonNull(img).getWidth(), img.getHeight(), nWidth, nHeight);
                        newFileJPG = new File("C:\\Users\\evgen\\Desktop\\resizeTest\\" + listOfFiles[i].getName());
                        try {
                            ImageIO.write(tempJPG, "jpg", newFileJPG);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                            messeges.append(e1.getMessage() + "\n");
                        }
                    }
                    else {
                        messeges.append("не изменено" + "\n");
                    }
                }
            }
            messeges.append("_________________________" + "\n" + "DONE" + "\n");
        });
        button.setPreferredSize(new Dimension(300, 50));
        mainPanel.add(button);
        mainPanel.add(messeges);
        frame.add(mainPanel);
    }

}