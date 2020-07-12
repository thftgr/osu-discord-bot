//package com.thftgr.z_notuse;
//
//import net.dv8tion.jda.api.entities.MessageChannel;
//import net.sourceforge.tess4j.ITesseract;
//import net.sourceforge.tess4j.Tesseract;
//import net.sourceforge.tess4j.TesseractException;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.awt.image.ColorModel;
//import java.awt.image.WritableRaster;
//import java.io.File;
//import java.io.IOException;
//import java.net.URL;
//
//
//
//public class detectText {
//    public static void main(String[] args) throws Exception {
//        new detectText().lineFinder();
//
//    }
//
//
//    void lineFinder() throws Exception {
//        BufferedImage image = ImageIO.read(new File("1.png"));
//        BufferedImage bf = copyImage(image);
//        BufferedImage bff = copyImage(image);
//        ImageIO.write(bf, "PNG", new File("org-1.PNG"));
//        ImageIO.write(bff, "PNG", new File("org-2.PNG"));
//    }
//
//
//    public BufferedImage copyImage(BufferedImage coverImage) {
//        ColorModel colorModel = coverImage.getColorModel();
//        boolean isAlphaPremultiplied = coverImage.isAlphaPremultiplied();
//        WritableRaster raster = coverImage.copyData(null);
//        BufferedImage newImage = new BufferedImage(colorModel, raster, isAlphaPremultiplied, null);
//        return newImage;
//    }
//
//
//    public void getImgText(MessageChannel channel, String downloadurl) {
//        ITesseract instance = new Tesseract();
//
//        try {
//
//            //URL uploadedFile = new URL(downloadurl).openConnection().getInputStream();
//            BufferedImage image = ImageIO.read(new URL(downloadurl).openConnection().getInputStream());
//            BufferedImage bf = image;
//            BufferedImage bff = image;
//            ImageIO.write(bf, "PNG", new File("org-1.PNG"));
//            ImageIO.write(bff, "PNG", new File("org-2.PNG"));
//
//
//            double w = bf.getWidth();
//            double h = bf.getHeight();
//
//            if (!(((h / w * 100) > 55.75 && (h / w * 100) < 56.75) | ((h / w * 100) > 59.5 && (h / w * 100) < 60.5))) {
//                return;
//            }
//
//
//            instance.setDatapath("./tessdata");
//            instance.setLanguage("eng");
//
//            String title = instance.doOCR(imgSetting(bf.getSubimage(
//                    (int) (w * 0.027),
//                    0,
//                    (int) (w - ((w * 0.027))),
//                    (int) (h * 0.032) //36.16
//            ), "title"));
////            channel.sendMessage("title: " + title).queue();
//            System.out.println(title);
//
//
//            instance.setLanguage("eng");
//            String artist = instance.doOCR(imgSetting(bf.getSubimage(
//                    (int) (w * 0.027),
//                    (int) (h * 0.031) - 2,
//                    (int) (w * 0.250),
//                    (int) (h * 0.019) + 4
//            ), "artist"));
//
//            if (!artist.contains("Mapped by")) {
//                artist = instance.doOCR(imgSetting(bf.getSubimage(
//                        (int) (w * 0.027) + 70,
//                        (int) (h * 0.031) - 2,
//                        (int) (w * 0.25) - 100,
//                        (int) (h * 0.019) + 4
//                ), "artist-kr"));
//
//            }
//
////            channel.sendMessage("artist: " + artist.replaceAll("Mapped by ", "")).queue();
//            System.out.println(artist);
//
//
//        } catch (IOException | TesseractException e) {
//            System.err.println(e.getMessage());
//        }
//
//
//    }
//
//    BufferedImage imgSetting(BufferedImage img, String filename) throws IOException {
//        int width = img.getWidth();
//        int height = img.getHeight();
//        BufferedImage imgg = img;
//        ImageIO.write(img, "PNG", new File("org-" + filename + ".PNG"));
//        int white = new Color(255, 255, 255, 255).getRGB();
//        int black = new Color(0, 0, 0, 255).getRGB();
//
//        for (int w = 0; w < width; w++) {
//            for (int h = 0; h < height; h++) {
//
//                if (new Color(img.getRGB(w, h)).getRGB() == -1) {
//
//                    imgg.setRGB(w, h, white);
//
//                } else {
//
//                    imgg.setRGB(w, h, black);
//                }
//            }
//        }
//        ImageIO.write(imgg, "PNG", new File("edit-" + filename + ".PNG"));
//        return imgg;
//    }
//
//}