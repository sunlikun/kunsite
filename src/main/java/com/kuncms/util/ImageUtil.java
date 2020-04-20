package com.kuncms.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
 
/**
 * 图片工具类
 */
public class ImageUtil {
 
	/**
	 * 裁剪PNG图片工具类
	 * 
	 * @param fromFile
	 *            源文件
	 * @param toFile
	 *            裁剪后的文件
	 * @param outputWidth
	 *            裁剪宽度
	 * @param outputHeight
	 *            裁剪高度
	 * @param proportion
	 *            是否是等比缩放
	 */
	public static void resizePng(File fromFile, File toFile, int outputWidth, int outputHeight,
			boolean proportion) {
		try {
			BufferedImage bi2 = ImageIO.read(fromFile);
			int newWidth;
			int newHeight;
			// 判断是否是等比缩放
			if (proportion) {
				// 为等比缩放计算输出的图片宽度及高度
				double rate1 = ((double) bi2.getWidth(null)) / (double) outputWidth + 0.1;
				double rate2 = ((double) bi2.getHeight(null)) / (double) outputHeight + 0.1;
				// 根据缩放比率大的进行缩放控制
				double rate = rate1 < rate2 ? rate1 : rate2;
				newWidth = (int) (((double) bi2.getWidth(null)) / rate);
				newHeight = (int) (((double) bi2.getHeight(null)) / rate);
			} else {
				newWidth = 500; // 输出的图片宽度
				newHeight = 750; // 输出的图片高度
			}
			BufferedImage to = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = to.createGraphics();
			to = g2d.getDeviceConfiguration().createCompatibleImage(newWidth, newHeight,
					Transparency.TRANSLUCENT);
			g2d.dispose();
			g2d = to.createGraphics();
			@SuppressWarnings("static-access")
			Image from = bi2.getScaledInstance(newWidth, newHeight, bi2.SCALE_AREA_AVERAGING);
			g2d.drawImage(from, 0, 0, null);
			g2d.dispose();
			ImageIO.write(to, "png", toFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	 /*
     * 图片缩放,w，h为缩放的目标宽度和高度
     * src为源文件目录，dest为缩放后保存目录
     */
    public static void zoomImage(String src,String dest,int w,int h) throws Exception {
		
        double wr=0,hr=0;
		File srcFile = new File(src);
		File destFile = new File(dest);

		BufferedImage bufImg = ImageIO.read(srcFile); //读取图片
		Image Itemp = bufImg.getScaledInstance(w, h, bufImg.SCALE_SMOOTH);//设置缩放目标图片模板
		
        //wr=w*1.0/bufImg.getWidth();     //获取缩放比例
		//hr=h*1.0 / bufImg.getHeight();
		   //获取缩放比例
	    hr=h*1.0 / bufImg.getHeight();
	    wr=hr;  

		AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
		Itemp = ato.filter(bufImg, null);
		try {
			ImageIO.write((BufferedImage) Itemp,dest.substring(dest.lastIndexOf(".")+1), destFile); //写入缩减后的图片
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
    
    
    public void hecheng() throws IOException{  

        InputStream imagein = new FileInputStream("D://1.jpg");  
        InputStream imagein2 = new FileInputStream("D://2.jpg");  


        BufferedImage image = ImageIO.read(imagein);  
        BufferedImage image2 = ImageIO.read(imagein2);  
        Graphics g = image.getGraphics();  
        g.drawImage(image2, image.getWidth() - image2.getWidth() - 15, image.getHeight() - image2.getHeight() - 10,  
                image2.getWidth() + 10, image2.getHeight() + 5, null);  

        OutputStream outImage = new FileOutputStream("D://3.jpg");  
//        String formatName = dstName.substring(dstName.lastIndexOf(".") + 1);   
//        ImageIO.write(image, /*"GIF"*/ formatName /* format desired */ , new File("custom" + j + "-" + i + ".jpg") /* target */ );    

        JPEGImageEncoder enc = JPEGCodec.createJPEGEncoder(outImage);  
        enc.encode(image);  
        imagein.close();  
        imagein2.close();  
        outImage.close();  


    } 
 
	/**
	 * 测试
	 */
	public static void main(String[] args) throws Exception {
		File fromFile = new File("E:/PotPlayer/Capture/【pergirls】朴孝敏__140623__No.9__永东大路世界杯应援舞台.mp4_20200419_135257.676.jpg");
		File toFile = new File("E:/PotPlayer/Capture/3.png");
		resizePng(fromFile, toFile, 100, 100, false);
		zoomImage("E:/PotPlayer/Capture/【pergirls】朴孝敏__140623__No.9__永东大路世界杯应援舞台.mp4_20200419_135257.676.jpg","E:/PotPlayer/Capture/2.png",500,750);
	}
}