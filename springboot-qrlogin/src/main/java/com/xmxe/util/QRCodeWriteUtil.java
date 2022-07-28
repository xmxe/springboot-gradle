package com.xmxe.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 二维码、条形码工具类
 */
public class QRCodeWriteUtil {

	/**
	 * CODE_WIDTH：二维码宽度，单位像素
	 * CODE_HEIGHT：二维码高度，单位像素
	 * FRONT_COLOR：二维码前景色，0x000000 表示黑色
	 * BACKGROUND_COLOR：二维码背景色，0xFFFFFF 表示白色
	 * 演示用 16 进制表示，和前端页面 CSS 的取色是一样的，注意前后景颜色应该对比明显，如常见的黑白
	 */
	private static final int CODE_WIDTH = 400;
	private static final int CODE_HEIGHT = 400;
	private static final int FRONT_COLOR = 0x000000;
	private static final int BACKGROUND_COLOR = 0xFFFFFF;

	/**
	 * 生成二维码 并保存为图片
	 */
	public static void createCodeToFile(String codeContent) {
		try {
			//获取系统目录
			String filePathDir = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
			//随机生成 png 格式图片
			String fileName = new Date().getTime() + ".png";

			/*
			 * com.google.zxing.EncodeHintType：编码提示类型,枚举类型
			 * EncodeHintType.CHARACTER_SET：设置字符编码类型
			 * EncodeHintType.ERROR_CORRECTION：设置误差校正
			 * ErrorCorrectionLevel：误差校正等级，L = ~7% correction、M = ~15% correction、Q = ~25% correction、H = ~30% correction
			 * 不设置时，默认为 L 等级，等级不一样，生成的图案不同，但扫描的结果是一样的
			 * EncodeHintType.MARGIN：设置二维码边距，单位像素，值越小，二维码距离四周越近
			 * */
			Map<EncodeHintType, Object> hints = new HashMap();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
			hints.put(EncodeHintType.MARGIN, 1);

			/*
			 * MultiFormatWriter:多格式写入，这是一个工厂类，里面重载了两个 encode 方法，用于写入条形码或二维码
			 * encode(String contents,BarcodeFormat format,int width, int height,Map<EncodeHintType,?> hints)
			 * contents:条形码/二维码内容
			 * format：编码类型，如 条形码，二维码 等
			 * width：码的宽度
			 * height：码的高度
			 * hints：码内容的编码类型
			 * BarcodeFormat：枚举该程序包已知的条形码格式，即创建何种码，如 1 维的条形码，2 维的二维码 等
			 * BitMatrix：位(比特)矩阵或叫2D矩阵，也就是需要的二维码
			 */
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			BitMatrix bitMatrix = multiFormatWriter.encode(codeContent, BarcodeFormat.QR_CODE, CODE_WIDTH, CODE_HEIGHT, hints);

			/*
			 * java.awt.image.BufferedImage：具有图像数据的可访问缓冲图像，实现了 RenderedImage 接口
			 * BitMatrix 的 get(int x, int y) 获取比特矩阵内容，指定位置有值，则返回true，将其设置为前景色，否则设置为背景色
			 * BufferedImage 的 setRGB(int x, int y, int rgb) 方法设置图像像素
			 * x：像素位置的横坐标，即列
			 * y：像素位置的纵坐标，即行
			 * rgb：像素的值，采用 16 进制,如 0xFFFFFF 白色
			 */
			BufferedImage bufferedImage = new BufferedImage(CODE_WIDTH, CODE_HEIGHT, BufferedImage.TYPE_INT_BGR);
			for (int x = 0; x < CODE_WIDTH; x++) {
				for (int y = 0; y < CODE_HEIGHT; y++) {
					bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? FRONT_COLOR : BACKGROUND_COLOR);
				}
			}

			/*
			 * javax.imageio.ImageIO java 扩展的图像IO
			 * write(RenderedImage im,String formatName,File output)
			 * im：待写入的图像
			 * formatName：图像写入的格式
			 * output：写入的图像文件，文件不存在时会自动创建
			 *
			 * 即将保存的二维码图片文件
			 */
			File codeImgFile = new File(filePathDir, fileName);
			ImageIO.write(bufferedImage, "png", codeImgFile);

			System.out.println("二维码图片生成成功：" + codeImgFile.getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成二维码 并保存为图片
	 */
	public static void createCodeToOutputStream(String codeContent, OutputStream outputStream) {
		try {

			/*
			 * com.google.zxing.EncodeHintType：编码提示类型,枚举类型
			 * EncodeHintType.CHARACTER_SET：设置字符编码类型
			 * EncodeHintType.ERROR_CORRECTION：设置误差校正
			 * ErrorCorrectionLevel：误差校正等级，L = ~7% correction、M = ~15% correction、Q = ~25% correction、H = ~30% correction
			 * 不设置时，默认为 L 等级，等级不一样，生成的图案不同，但扫描的结果是一样的
			 * EncodeHintType.MARGIN：设置二维码边距，单位像素，值越小，二维码距离四周越近
			 * */
			Map<EncodeHintType, Object> hints = new HashMap();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
			hints.put(EncodeHintType.MARGIN, 1);

			/*
			 * MultiFormatWriter:多格式写入，这是一个工厂类，里面重载了两个 encode 方法，用于写入条形码或二维码
			 * encode(String contents,BarcodeFormat format,int width, int height,Map<EncodeHintType,?> hints)
			 * contents:条形码/二维码内容
			 * format：编码类型，如 条形码，二维码 等
			 * width：码的宽度
			 * height：码的高度
			 * hints：码内容的编码类型
			 * BarcodeFormat：枚举该程序包已知的条形码格式，即创建何种码，如 1 维的条形码，2 维的二维码 等
			 * BitMatrix：位(比特)矩阵或叫2D矩阵，也就是需要的二维码
			 */
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			BitMatrix bitMatrix = multiFormatWriter.encode(codeContent, BarcodeFormat.QR_CODE, CODE_WIDTH, CODE_HEIGHT, hints);

			/*
			 * java.awt.image.BufferedImage：具有图像数据的可访问缓冲图像，实现了 RenderedImage 接口
			 * BitMatrix 的 get(int x, int y) 获取比特矩阵内容，指定位置有值，则返回true，将其设置为前景色，否则设置为背景色
			 * BufferedImage 的 setRGB(int x, int y, int rgb) 方法设置图像像素
			 * x：像素位置的横坐标，即列
			 * y：像素位置的纵坐标，即行
			 * rgb：像素的值，采用 16 进制,如 0xFFFFFF 白色
			 */
			BufferedImage bufferedImage = new BufferedImage(CODE_WIDTH, CODE_HEIGHT, BufferedImage.TYPE_INT_BGR);
			for (int x = 0; x < CODE_WIDTH; x++) {
				for (int y = 0; y < CODE_HEIGHT; y++) {
					bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? FRONT_COLOR : BACKGROUND_COLOR);
				}
			}
			/*
			 * 将生成的图片流转成base64的格式，然后返回给前端进行展示。
			 *
			 * //定义字节输出流，将bufferedImage写入
			 * ByteArrayOutputStream out = new ByteArrayOutputStream();
			 * ImageIO.write(bufferedImage, "png", out);
			 * //将输出流转换成base64
			 * String str64 = Base64.getEncoder().encodeToString(out.toByteArray());
			 * 然后将str64以json格式返回给前端进行展示
			 */


			ImageIO.write(bufferedImage, "png", outputStream);
			System.out.println("二维码图片生成成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String codeContent1 = "Hello World";
		createCodeToFile(codeContent1);

		String codeContent2 = "https://www.baidu.com/";
		createCodeToFile(codeContent2);
	}
}