package com.msb.tank;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * 资源管理类
 * 用于处理图片加载到内存中
 */
public class ResourceMgr {
	public static BufferedImage goodtankL, goodtankU, goodtankR, goodtankD;
	public static BufferedImage badtankL, badtankU, badtankR, badtankD;
	public static BufferedImage bulletL, bulletU, bulletR, bulletD;
	public static BufferedImage[] explodes = new BufferedImage[16];

	static {
		try {
			goodtankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/Goodtank1.png"));
			goodtankL = ImageUtil.rotateImage(goodtankU, -90);
			goodtankR = ImageUtil.rotateImage(goodtankU, 90);
			goodtankD = ImageUtil.rotateImage(goodtankU, 180);

			badtankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/Badtank1.png"));
			badtankL = ImageUtil.rotateImage(badtankU, -90);
			badtankR = ImageUtil.rotateImage(badtankU, 90);
			badtankD = ImageUtil.rotateImage(badtankU, 180);

			bulletU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.png"));
			bulletL = ImageUtil.rotateImage(bulletU, -90);
			bulletR = ImageUtil.rotateImage(bulletU, 90);
			bulletD = ImageUtil.rotateImage(bulletU, 180);

			for (int i = 0; i < 16; i++) {
				explodes[i] = ImageIO
						.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/e" + (i + 1) + ".gif"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}