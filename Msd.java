import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.Random;


public class Msd extends Applet {
    private BufferedImage secretImage1, secretImage2;
    private BufferedImage share1_1, share1_2, share2_1, share2_2;
    private BufferedImage revealed1, revealed2;
    private String statusMessage = "";
    private Panel imagePanel;
    private Panel buttonPanel;
       
    public void init() {
    setBackground(Color.pink);
    setLayout(null);     
    Label titleLabel = new Label("Image Sharing and Compression", Label.CENTER);
    titleLabel.setBounds(500, 20, 1000, 50); 
    Font titleFont = new Font("Arial", Font.BOLD, 40); 
    titleLabel.setFont(titleFont);

    add(titleLabel);

    
    Button loadSecret1 = new Button("Load Secret Image 1");
    Button loadSecret2 = new Button("Load Secret Image 2");
    Button process1 = new Button("Process 1 (Shares for Secret 1)");
    Button process2 = new Button("Process 2 (Shares for Secret 2)");
    Button reveal1 = new Button("Reveal Image 1");
    Button reveal2 = new Button("Reveal Image 2");
    Button compressButton1 = new Button("Compress Shares 1");
    Button compressButton2 = new Button("Compress Shares 2");
    Button refreshButton = new Button("Refresh");

   
    loadSecret1.setBounds(100, 90, 150, 30);
    loadSecret2.setBounds(280, 90, 150, 30);
    process1.setBounds(450, 90, 200, 30);
    process2.setBounds(680, 90, 200, 30);
    compressButton1.setBounds(900, 90, 150, 30);
    compressButton2.setBounds(1090, 90, 150, 30);
    reveal1.setBounds(1270, 90, 150, 30);
    reveal2.setBounds(1430, 90, 150, 30);
    refreshButton.setBounds(1600, 90, 150, 30);

  
    loadSecret1.addActionListener(e -> loadSecretImage1());
    loadSecret2.addActionListener(e -> loadSecretImage2());
    process1.addActionListener(e -> processShares1());
    process2.addActionListener(e -> processShares2());
    reveal1.addActionListener(e -> revealImage1());
    reveal2.addActionListener(e -> revealImage2());
    compressButton1.addActionListener(e -> showCompressedPanel1());
    compressButton2.addActionListener(e -> showCompressedPanel2());
    refreshButton.addActionListener(e -> refresh());

   
    add(loadSecret1);
    add(loadSecret2);
    add(process1);
    add(process2);
    add(compressButton1);
    add(compressButton2);
    add(reveal1);
    add(reveal2);
    add(refreshButton);

  
    imagePanel = new Panel() {
        public void paint(Graphics g) {
            super.paint(g);
            drawImages(g);
        }
    };
    imagePanel.setBounds(0, 180, 800, 420); // Adjust size and position of image panel
    add(imagePanel);
}


  
    private void loadSecretImage1() {
        secretImage1 = loadImage();
        if (secretImage1 != null) {
            statusMessage = "Loaded Secret Image 1";
            imagePanel.revalidate();
            imagePanel.repaint();
        }
        repaint();
    }

 
    private void loadSecretImage2() {
        secretImage2 = loadImage();
        if (secretImage2 != null) {
            statusMessage = "Loaded Secret Image 2";
            imagePanel.revalidate();
            imagePanel.repaint();
        }
        repaint();
    }

    
    private BufferedImage loadImage() {
        FileDialog fileDialog = new FileDialog((Frame) null, "Select Image", FileDialog.LOAD);
        fileDialog.setVisible(true);
        String file = fileDialog.getDirectory() + fileDialog.getFile();
        if (file != null && file.length() > 0) {
            try {
                return ImageIO.read(new File(file));
            } catch (Exception e) {
                statusMessage = "Error loading image: " + e.getMessage();
                repaint();
            }
        }
        return null;
    }

    
    private void downloadShare(BufferedImage share, String fileName) {
        FileDialog saveDialog = new FileDialog((Frame) null, "Save Share", FileDialog.SAVE);
        saveDialog.setFile(fileName);
        saveDialog.setVisible(true);

        String directory = saveDialog.getDirectory();
        String filename = saveDialog.getFile();

        if (filename != null) {
            File file = new File(directory, filename);
            try {
                ImageIO.write(share, "png", file);
                statusMessage = "Share saved: " + file.getAbsolutePath();
            } catch (IOException e) {
                statusMessage = "Error saving file: " + e.getMessage();
            }
        } else {
            statusMessage = "Save operation canceled.";
        }
        repaint();
    }

    
private void processShares1() {
    if (secretImage1 != null) {
        BufferedImage[] shares = createColorShares(secretImage1);
        share1_1 = shares[0];
        share1_2 = shares[1];
        statusMessage = "Shares created for Secret Image 1";
        imagePanel.revalidate();
        imagePanel.repaint();

       
        Frame shareFrame1 = new Frame("Shares for Secret Image 1");
        shareFrame1.setSize(800, 600);
        shareFrame1.setLayout(new BorderLayout());

      
        Panel sharePanel = new Panel() {
            public void paint(Graphics g) {
                int spaceBetweenShares = 50; 

                g.drawString("Share 1_1:", 10, 20);
                g.drawImage(share1_1, 10, 30, this); 

                g.drawString("Share 1_2:", 10 + share1_1.getWidth() + spaceBetweenShares, 20);
                g.drawImage(share1_2, 10 + share1_1.getWidth() + spaceBetweenShares, 30, this); 
            }
        };

      
        Panel downloadPanel = new Panel();
        Button downloadButton1 = new Button("Download Share1_1");
        Button downloadButton2 = new Button("Download Share1_2");

        downloadButton1.addActionListener(e -> downloadShare(share1_1, "Share1_1.png"));
        downloadButton2.addActionListener(e -> downloadShare(share1_2, "Share1_2.png"));

        downloadPanel.add(downloadButton1);
        downloadPanel.add(downloadButton2);

        shareFrame1.add(sharePanel, BorderLayout.CENTER);
        shareFrame1.add(downloadPanel, BorderLayout.SOUTH);

        shareFrame1.setVisible(true);
    } else {
        statusMessage = "No secret image loaded for Secret 1";
    }
    repaint();
}

private void processShares2() {
    if (secretImage2 != null) {
        BufferedImage[] shares = createColorShares(secretImage2);
        share2_1 = shares[0];
        share2_2 = shares[1];
        statusMessage = "Shares created for Secret Image 2";
        imagePanel.revalidate();
        imagePanel.repaint();

        
        Frame shareFrame2 = new Frame("Shares for Secret Image 2");
        shareFrame2.setSize(800, 600);
        shareFrame2.setLayout(new BorderLayout());

      
        Panel sharePanel = new Panel() {
            public void paint(Graphics g) {
                int spaceBetweenShares = 50; 

                g.drawString("Share 2_1:", 10, 20);
                g.drawImage(share2_1, 10, 30, this);

                g.drawString("Share 2_2:", 10 + share2_1.getWidth() + spaceBetweenShares, 20);
                g.drawImage(share2_2, 10 + share2_1.getWidth() + spaceBetweenShares, 30, this); 
            }
        };

        
        Panel downloadPanel = new Panel();
        Button downloadButton1 = new Button("Download Share2_1");
        Button downloadButton2 = new Button("Download Share2_2");

        downloadButton1.addActionListener(e -> downloadShare(share2_1, "Share2_1.png"));
        downloadButton2.addActionListener(e -> downloadShare(share2_2, "Share2_2.png"));

        downloadPanel.add(downloadButton1);
        downloadPanel.add(downloadButton2);

        shareFrame2.add(sharePanel, BorderLayout.CENTER);
        shareFrame2.add(downloadPanel, BorderLayout.SOUTH);

        shareFrame2.setVisible(true);
    } else {
        statusMessage = "No secret image loaded for Secret 2";
    }
    repaint();
}


    
private void revealImage1() {
    if (share1_1 != null && share1_2 != null) {
        revealed1 = combineColorShares(share1_1, share1_2);
        statusMessage = "Revealed Image 1";
        imagePanel.revalidate();
        imagePanel.repaint();

        
        Frame revealFrame1 = new Frame("Revealed Image 1");
        revealFrame1.setSize(800, 600);
        revealFrame1.setLayout(new BorderLayout());

     
        Panel revealPanel = new Panel() {
            public void paint(Graphics g) {
                g.drawImage(revealed1, 10, 30, this); 
            }
        };

      
        Button downloadButton = new Button("Download Revealed Image 1");
        downloadButton.addActionListener(e -> downloadShare(revealed1, "Revealed_Image1.png"));

        
        revealFrame1.add(revealPanel, BorderLayout.CENTER);
        revealFrame1.add(downloadButton, BorderLayout.SOUTH);
        revealFrame1.setVisible(true);
    } else {
        statusMessage = "No shares available to reveal Image 1.";
    }
    repaint();
}

private void revealImage2() {
    if (share2_1 != null && share2_2 != null) {
        revealed2 = combineColorShares(share2_1, share2_2);
        statusMessage = "Revealed Image 2";
        imagePanel.revalidate();
        imagePanel.repaint();

       
        Frame revealFrame2 = new Frame("Revealed Image 2");
        revealFrame2.setSize(800, 600);
        revealFrame2.setLayout(new BorderLayout());

      
        Panel revealPanel = new Panel() {
            public void paint(Graphics g) {
                g.drawImage(revealed2, 10, 30, this); 
            }
        };

     
        Button downloadButton = new Button("Download Revealed Image 2");
        downloadButton.addActionListener(e -> downloadShare(revealed2, "Revealed_Image2.png"));

     
        revealFrame2.add(revealPanel, BorderLayout.CENTER);
        revealFrame2.add(downloadButton, BorderLayout.SOUTH);
        revealFrame2.setVisible(true);
    } else {
        statusMessage = "No shares available to reveal Image 2.";
    }
    repaint();
}


    
    private void showCompressedPanel1() {
        if (share1_1 != null && share1_2 != null) {
            String compressed1_1 = rleCompress(share1_1);
            String compressed1_2 = rleCompress(share1_2);
            showCompressedShares("Compressed Shares for Secret Image 1", compressed1_1, compressed1_2);
        } else {
            statusMessage = "No shares available for Secret Image 1.";
        }
        repaint();
    }

    
    private void showCompressedPanel2() {
        if (share2_1 != null && share2_2 != null) {
            String compressed2_1 = rleCompress(share2_1);
            String compressed2_2 = rleCompress(share2_2);
            showCompressedShares("Compressed Shares for Secret Image 2", compressed2_1, compressed2_2);
        } else {
            statusMessage = "No shares available for Secret Image 2.";
        }
        repaint();
    }

    
    private void showCompressedShares(String title, String compressed1, String compressed2) {
        Frame compressedFrame = new Frame(title);
        compressedFrame.setSize(800, 400);
        compressedFrame.setLayout(new GridLayout(2, 1));

        TextArea textArea1 = new TextArea(compressed1);
        TextArea textArea2 = new TextArea(compressed2);
        textArea1.setEditable(false);
        textArea2.setEditable(false);

        compressedFrame.add(textArea1);
        compressedFrame.add(textArea2);

        Button downloadButton1 = new Button("Download Compressed Share 1");
        Button downloadButton2 = new Button("Download Compressed Share 2");

        downloadButton1.addActionListener(e -> downloadCompressedShare(compressed1, "Compressed_Share1.txt"));
        downloadButton2.addActionListener(e -> downloadCompressedShare(compressed2, "Compressed_Share2.txt"));

        Panel downloadPanel = new Panel();
        downloadPanel.add(downloadButton1);
        downloadPanel.add(downloadButton2);

        compressedFrame.add(downloadPanel, BorderLayout.SOUTH);
        compressedFrame.setVisible(true);
    }

   
    private void downloadCompressedShare(String compressedData, String fileName) {
        FileDialog saveDialog = new FileDialog((Frame) null, "Save Compressed Share", FileDialog.SAVE);
        saveDialog.setFile(fileName);
        saveDialog.setVisible(true);

        String directory = saveDialog.getDirectory();
        String filename = saveDialog.getFile();

        if (filename != null) {
            File file = new File(directory, filename);
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.print(compressedData);
                statusMessage = "Compressed share saved: " + file.getAbsolutePath();
            } catch (IOException e) {
                statusMessage = "Error saving file: " + e.getMessage();
            }
        } else {
            statusMessage = "Save operation canceled.";
        }
        repaint();
    }

 
    private String rleCompress(BufferedImage image) {
        StringBuilder compressed = new StringBuilder();
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                int count = 1;

                while (x + 1 < width && image.getRGB(x + 1, y) == pixel) {
                    count++;
                    x++;
                }

                compressed.append(pixel).append(",").append(count).append(";");
            }
            compressed.append("\n");
        }

       
        return ensureSize(compressed.toString());
    }

 
    private String ensureSize(String data) {
        if (data.length() > 51200) { 
            return data.substring(0, 51200); 
        }
        return data;
    }

    
    private BufferedImage[] createColorShares(BufferedImage secretImage) {
        int width = secretImage.getWidth();
        int height = secretImage.getHeight();

        BufferedImage share1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        BufferedImage share2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Random random = new Random();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = secretImage.getRGB(x, y);
                int randomPixel = random.nextInt(0xFFFFFF);
                share1.setRGB(x, y, randomPixel);
                share2.setRGB(x, y, pixel ^ randomPixel); 
            }
        }

        return new BufferedImage[] { share1, share2 };
    }

  
    private BufferedImage combineColorShares(BufferedImage share1, BufferedImage share2) {
        int width = share1.getWidth();
        int height = share1.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel1 = share1.getRGB(x, y);
                int pixel2 = share2.getRGB(x, y);
                int combinedPixel = pixel1 ^ pixel2; 
                result.setRGB(x, y, combinedPixel);
            }
        }

        return result;
    }



   
   public void refresh() {
    
    secretImage1 = null;
    secretImage2 = null;
    share1_1 = null;
    share1_2 = null;
    share2_1 = null;
    share2_2 = null;
    revealed1 = null;
    revealed2 = null;
    statusMessage = "Applet refreshed.";
    repaint();  

  imagePanel.revalidate(); 
    imagePanel.repaint();
}

  
    private void drawImages(Graphics g) {
    int x = 10; 
    int y = 30; 
    int maxHeight = 0; 

    g.drawString(statusMessage, 10, 20);

    
    if (secretImage1 != null) {
        g.drawImage(secretImage1, x, y, this);
        x += secretImage1.getWidth() + 30; 
        maxHeight = Math.max(maxHeight, secretImage1.getHeight());
    }
    
   /* if (share1_1 != null) {
        g.drawImage(share1_1, x, y, this);
        x += share1_1.getWidth() + 30; 
        maxHeight = Math.max(maxHeight, share1_1.getHeight());
    }
    
    if (share1_2 != null) {
        g.drawImage(share1_2, x, y, this);
        x += share1_2.getWidth() + 30; 
        maxHeight = Math.max(maxHeight, share1_2.getHeight());
    }
    
    if (revealed1 != null) {
        g.drawImage(revealed1, x, y, this);
        x += revealed1.getWidth() + 30; 
        maxHeight = Math.max(maxHeight, revealed1.getHeight());
    }*/

    
    y += maxHeight + 30; 
    x = 10; 

    
    if (secretImage2 != null) {
        g.drawImage(secretImage2, x, y, this);
        x += secretImage2.getWidth() + 30;
        maxHeight = Math.max(maxHeight, secretImage2.getHeight());
    }
    
  /*  if (share2_1 != null) {
        g.drawImage(share2_1, x, y, this);
        x += share2_1.getWidth() + 30;
        maxHeight = Math.max(maxHeight, share2_1.getHeight());
    }
    
    if (share2_2 != null) {
        g.drawImage(share2_2, x, y, this);
        x += share2_2.getWidth() + 30;
        maxHeight = Math.max(maxHeight, share2_2.getHeight());
    }
  
    if (revealed2 != null) {
        g.drawImage(revealed2, x, y, this);
    }*/
   }
  
    public static void main(String[] args) {
        Frame frame = new Frame("Msd Applet");
        Msd applet = new Msd();
        applet.init(); 

        frame.add(applet);
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                frame.dispose();
            }
        });
    }


}
