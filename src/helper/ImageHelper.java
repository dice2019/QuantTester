package helper;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public final class ImageHelper {
	/**
	 * @see http://cping1982.blog.51cto.com/601635/130066
	 * @param bufferedimage
	 * @return 沿Y轴翻转的图像
	 */
    public final static BufferedImage flipImage(final BufferedImage bufferedimage) {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(w, h, bufferedimage
                .getColorModel().getTransparency())).createGraphics())
                .drawImage(bufferedimage, 0, 0, w, h, 0, h, w, 0, null);
        graphics2d.dispose();
        return img;
    }
    
    /**
     * 待合并的两张图必须满足这样的前提，如果水平方向合并，则高度必须相等；如果是垂直方向合并，宽度必须相等。
     * mergeImage方法不做判断，自己判断。
     *
     * @see http://www.ablanxue.com/prone_3205_1.html
     * @param img1
     *            待合并的第一张图
     * @param img2
     *            带合并的第二张图
     * @param isHorizontal
     *            为true时表示水平方向合并，为false时表示垂直方向合并
     * @return 返回合并后的BufferedImage对象
     * @throws IOException
     */
	public final static BufferedImage mergeImage(final BufferedImage img1, final BufferedImage img2, final boolean isHorizontal) {
		int w1 = img1.getWidth();
		int h1 = img1.getHeight();
		int w2 = img2.getWidth();
		int h2 = img2.getHeight();

		// 从图片中读取RGB
		int[] ImageArrayOne = new int[w1 * h1];
		ImageArrayOne = img1.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 逐行扫描图像中各个像素的RGB到数组中
		int[] ImageArrayTwo = new int[w2 * h2];
		ImageArrayTwo = img2.getRGB(0, 0, w2, h2, ImageArrayTwo, 0, w2);

		// 生成新图片
		BufferedImage DestImage = null;
		if (isHorizontal) { // 水平方向合并
			DestImage = new BufferedImage(w1 + w2, h1, BufferedImage.TYPE_INT_RGB);
			DestImage.setRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
			DestImage.setRGB(w1, 0, w2, h2, ImageArrayTwo, 0, w2);
		} else { // 垂直方向合并
			DestImage = new BufferedImage(w1, h1 + h2, BufferedImage.TYPE_INT_RGB);
			DestImage.setRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
			DestImage.setRGB(0, h1, w2, h2, ImageArrayTwo, 0, w2); // 设置下半部分的RGB
		}

		return DestImage;
	}
}
