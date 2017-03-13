package drawing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import global.Config;
import helper.ImageHelper;
import helper.MathHelper;

public class ResultDrawing {
	
	protected final Color background_color;
	protected final Color foreground_color;
	
	public ResultDrawing(Color background_color, Color foreground_color) {
		this.background_color = background_color;
		this.foreground_color = foreground_color;
	}
	
	public ResultDrawing() {
		this(Color.BLACK, Color.WHITE);
	}
	
	// TODO 应该用对数图吗? 固定交易volume用线性图, 固定最大volume用对数图.
	public void drawScatterDiagram(final String filename, final float[] balance, final int height) {
		final float max_balance = MathHelper.Max(balance);
		final float min_balance = MathHelper.Min(balance);
//		final float max_balance_log_ceil = (float) Math.log10(max_balance);
//		final float min_balance_log_floor = (float) Math.log10(min_balance);
		
		final float scale = (height * 0.99f) / (max_balance - min_balance);
//		final float scale = (height * 0.99f) / (max_balance_log_ceil - min_balance_log_floor);
		BufferedImage bi = new BufferedImage(balance.length, height, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < balance.length; i++) {
			for (int j = 0; j < height; j++) {
				bi.setRGB(i, j, background_color.getRGB());
			}
			bi.setRGB(i, (int) (((balance[i]) - min_balance) * scale), foreground_color.getRGB());
//			bi.setRGB(i, (int) ((Math.log10(balance[i]) - min_balance_log_floor) * scale), foreground_color.getRGB());
		}

		String filename_with_path = Config.ResultDir + filename;
		try {
			ImageIO.write(ImageHelper.flipImage(bi), "png", new File(filename_with_path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void drawScatterDiagram(final String filename, final float[] balance) {
		drawScatterDiagram(filename, balance, 1000);
	}
}
