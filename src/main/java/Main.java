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
        JFrame frame = new JFrame("Изменение размера картинок");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setPreferredSize(new Dimension(400, 500));
        frame.pack();
        frame.setResizable(false);
        frame.setLocation(500, 100);
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.pink);
        mainPanel.setLayout(new FlowLayout());
        mainPanel.setSize(399, 399);
        JPanel wPanel = new JPanel();
        wPanel.setBorder(new TitledBorder("Изменить ширину на:"));
        wPanel.setLayout(new FlowLayout());
        wPanel.setPreferredSize(new Dimension(150, 50));
        JPanel hPanel = new JPanel();
        hPanel.setBorder(new TitledBorder("Изменить высоту на:"));
        hPanel.setLayout(new FlowLayout());
        hPanel.setPreferredSize(new Dimension(150, 50));
        JPanel pPanel = new JPanel();
        pPanel.setBorder(new TitledBorder("Папка (скопируй и вставь путь)"));
        pPanel.setSize(300, 40);
        TextField widthField = new TextField("400");
        TextField heightField = new TextField("400");
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
        TextArea messages = new TextArea("Loggs" + "\n" + "_________________________" + "\n");
        messages.setPreferredSize(new Dimension(300, 300));
        messages.setEditable(false);
        JButton button = new JButton("Изменить");
        button.addActionListener(e -> {
            messages.setText("Loggs" + "\n" + "_________________________" + "\n");
            //messages.append("Loggs" + "\n" + "_________________________" + "\n");
            String path = pathField.getText() + "/";
            if (path.equals("")) {
                messages.append("Введите адрес папки!");
                throw new IllegalArgumentException("Empty pathField!");
            }
            int nWidth;
            int nHeight;
            if (widthField.getText().equals("")) {
                nWidth = 0;
            } else {
                nWidth = Integer.parseInt(widthField.getText());
            }
            if (heightField.getText().equals("")) {
                nHeight = 0;
            } else {
                nHeight = Integer.parseInt(heightField.getText());
            }
            if (nWidth == 0 && nHeight == 0) messages.append("Изменено: 0; заполните поля!" + "\n");
            File folder = new File(path);
            File[] listOfFiles = folder.listFiles();
            assert listOfFiles != null;
            messages.append("Найдено файлов: " + listOfFiles.length + "\n");
            BufferedImage img = null;
            BufferedImage tempPNG = null;
            BufferedImage tempJPG = null;
            File newFilePNG = null;
            File newFileJPG = null;
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    messages.append("_________________________" + "\n" + "File " + listOfFiles[i].getName() + "\n"
                    + "Изменено" + "\n");
                    try {
                        img = ImageIO.read(new File(path + listOfFiles[i].getName()));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                        messages.append(e1.getMessage() + "\n");
                    }

                    tempJPG = Resize.resizeImage(img, Objects.requireNonNull(img).getWidth(), img.getHeight(), nWidth, nHeight);
                    newFileJPG = new File(path + listOfFiles[i].getName());
                    try {
                        ImageIO.write(tempJPG, "jpg", newFileJPG);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                        messages.append(e1.getMessage() + "\n");
                    }
                }
            }
            messages.append("_________________________" + "\n" + "DONE" + "\n");
        });
        button.setPreferredSize(new Dimension(300, 50));
        mainPanel.add(button);
        mainPanel.add(messages);
        frame.add(mainPanel);
    }

}