import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Carnival extends Assets{
    public Carnival() throws IOException {
        super(7, 140, 0, ImageIO.read(new File("icon.png")));
    }
}
