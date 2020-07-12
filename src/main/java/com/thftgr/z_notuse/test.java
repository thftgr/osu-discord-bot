package com.thftgr.z_notuse;


import com.lowagie.text.html.simpleparser.Img;
import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


public class test {
    //public static JsonObject settingValue;

    public static void main(String[] arg) {
        //settingValue = (JsonObject) JsonParser.parseReader(new Gson().newJsonReader(new FileReader("setting/deb.json")));
        OpenCV.loadShared();
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
        //Mat tmm = Imgcodecs.imread("D:\\Users\\thftg\\Desktop\\screenshot350.png");
        //System.out.println("asdasdasd");
        try {
            File[]  ff = new File("imgsample/").listFiles();

            for (int i = 0; i <ff.length & i < 20 ; i++) {
                System.out.println(ff[i].toString());
                new test().testContour(ff[i]);
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void testContour(File f) {
        Mat imageMat =Imgcodecs.imread(f.toString(), Imgcodecs.IMREAD_COLOR) ;
        Mat rgb = new Mat();  //rgb color matrix
        rgb = imageMat.clone();
        Mat grayImage = new Mat();  //grey color matrix
        Imgproc.cvtColor(rgb, grayImage, Imgproc.COLOR_RGB2GRAY);
//        Mat = new Mat();
        Mat gradThresh = new Mat();  //matrix for threshold
        Mat hierarchy = new Mat();    //matrix for contour hierachy
        Mat bler = new Mat();
        Mat nonBler = grayImage.clone();

        Imgproc.GaussianBlur(nonBler,nonBler,new Size(),3);

        Imgproc.dilate(nonBler,nonBler,new Mat(),new Point(),5);

//        Imgproc.morphologyEx(nonBler,nonBler,Imgproc.MORPH_OPEN,new Mat(),new Point(),3);

        Imgproc.adaptiveThreshold(nonBler, nonBler, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY_INV, 21, 10);  //block size 3 21 99
        removeVerticalLines(nonBler,10);
        Imgproc.Canny(nonBler, nonBler, 50, 200);
        Imgproc.morphologyEx(nonBler,nonBler,Imgproc.MORPH_CLOSE,new Mat(),new Point(),3);

        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(nonBler, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);
        Imgcodecs.imwrite("imgout/_GB2_"+f.getName(), nonBler);

//        Imgproc.drawContours(rgb, contours, Imgproc.CONTOURS_MATCH_I3, new Scalar(0,255,0), 5);

        if(contours.size()>0) {
            for(int idx = 0; idx < contours.size(); idx++) {
                Rect rect = Imgproc.boundingRect(contours.get(idx));
                if (rect.height > 30 && rect.width > 50){
                    Imgproc.rectangle(imageMat, new Point(rect.br().x - rect.width, rect.br().y - rect.height)
                            , rect.br()
                            , new Scalar(0, 255, 0), 5);
                }

            }

            Imgcodecs.imwrite("imgout/_"+f.getName(), imageMat);
        }


//        Imgproc.findContours(gradThresh, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));
//        Imgcodecs.imwrite("doc_originald.jpg", rgb);
//
//        Imgproc.drawContours(rgb,contours,-1,new Scalar(0,255,0));
//        if(contours.size()>0) {
//            for(int idx = 0; idx < contours.size(); idx++) {
//                Rect rect = Imgproc.boundingRect(contours.get(idx));
//                if (rect.height > 10 && rect.width > 40 && !(rect.width >= 512 - 5 && rect.height >= 512 - 5)){
//                    rectangle(imageMat, new Point(rect.br().x - rect.width, rect.br().y - rect.height)
//                            , rect.br()
//                            , new Scalar(0, 255, 0), 5);
//                }
//
//            }
//            Imgcodecs.imwrite("doc_original.jpg", rgb);
//            Imgcodecs.imwrite("doc_gray.jpg", grayImage);
//            Imgcodecs.imwrite("doc_thresh.jpg", gradThresh);
//            Imgcodecs.imwrite("doc_contour.jpg", imageMat);
//        }
    }


    public void removeVerticalLines(Mat img, int limit) {
        Mat lines=new Mat();
        int threshold = 100; //선 추출 정확도
        int minLength = 80; //추출할 선의 길이
        int lineGap = 5; //5픽셀 이내로 겹치는 선은 제외
        int rho = 1;
        Imgproc.HoughLinesP(img, lines, rho, Math.PI/180, threshold, minLength, lineGap);
        for (int i = 0; i < lines.total(); i++) {
            double[] vec=lines.get(i,0);
            Point pt1, pt2;
            pt1=new Point(vec[0],vec[1]);
            pt2=new Point(vec[2],vec[3]);
            double gapY = Math.abs(vec[3]-vec[1]);
            double gapX = Math.abs(vec[2]-vec[0]);
            if(gapY>limit && limit>0) {
                //remove line with black color
                Imgproc.line(img, pt1, pt2, new Scalar(0, 0, 0), 10);
            }
        }
    }


}









