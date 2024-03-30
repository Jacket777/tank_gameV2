
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;



/**
 * 测试图片
 */
public class ImageTest {


    @Test
    public void testLoadImage()throws IOException{
        // 第1种获取图片的方式
        BufferedImage image = ImageIO.read(new File("images/bulletD.gif"));
        assertNotNull(image);

        // 第1种获取图片的方式
        BufferedImage image2 = ImageIO.read(ImageTest.class.getResourceAsStream("images/bulletD.gif"));
        assertNotNull(image2);


    }
}
