package com.xmxe.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码、条形码工具类
 */
public class QRCodeReadUtil {

	/**
	 * 解析二维码内容(文件)
	 */
	public static String parseQRCodeByFile(File file) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(file);
		return parseQRCode(bufferedImage);
	}

	/**
	 * 解析二维码内容(网络链接)
	 */
	public static String parseQRCodeByUrl(URL url) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(url);
		return parseQRCode(bufferedImage);
	}

	private static String parseQRCode(BufferedImage bufferedImage){
		try {
			/*
			 * com.google.zxing.client.j2se.BufferedImageLuminanceSource：缓冲图像亮度源
			 * 将java.awt.image.BufferedImage转为zxing的缓冲图像亮度源
			 * 关键就是下面这几句:HybridBinarizer用于读取二维码图像数据，BinaryBitmap二进制位图
			 */
			LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
			Map<DecodeHintType, Object> hints = new HashMap<>();
			hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");

			/*
			 * 如果图片不是二维码图片，则decode抛异常：com.google.zxing.NotFoundException
			 * MultiFormatWriter的encode用于对内容进行编码成2D矩阵
			 * MultiFormatReader的decode用于读取二进制位图数据
			 */
			Result result = new MultiFormatReader().decode(bitmap, hints);
			return result.getText();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("-----解析二维码内容失败-----");
		}
		return null;
	}

	public static void main(String[] args) throws IOException {
		File localFile = new File("/Users/Desktop/1632403131016.png");
		String content1 = parseQRCodeByFile(localFile);
		System.out.println(localFile + " 二维码内容：" + content1);
		URL url = new URL("http://cdn.pzblog.cn/1951b6c4b40fd81630903bf6f7037156.png");
		String content2 = parseQRCodeByUrl(url);
		System.out.println(url + " 二维码内容：" + content2);
	}
}