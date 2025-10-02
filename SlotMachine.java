import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SlotMachine extends Assets{
    public SlotMachine() throws IOException {
        super(6, 120, 0, ImageIO.read(new File("slot_machine.png")));
    }
}
