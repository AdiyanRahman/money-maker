import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedHashMap;

public class MoneyMakerRunner extends JFrame {
    public boolean ISACTIVE = false;
    public static final String FLIP_FORWARD = "flipForward";
    public static final String FLIP_BACKWARD = "flipBackward";
    public static final JPanel CENTER = new JPanel();
    public static final JPanel UPGRADES = new JPanel();
    public static final JPanel PURCHASABLES = new JPanel();
    public static final JPanel ICON_SPACE = new JPanel();
    public static final JLabel WORTH = new JLabel();
    public static final int WIDTH = 2400;
    public static final int HEIGHT = 1300;
    public BigInteger netWorth = new BigInteger("0");
    public BigInteger wps = new BigInteger("0");
    public int page = 0;
    public static final Color MONEYGREEN = new Color(62, 155, 10);
    public static final Color MARMALADE = new Color(250,110,6);
    public static final Color CREAM = new Color(220,220,187);
    public static final Color JEANS = new Color(66,94,106);
    public static final LinkedHashMap<String, Assets> CONNECTIONS = new LinkedHashMap<>();


    public MoneyMakerRunner() throws IOException {
        setTitle("Money Maker");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setUpConnections();
        addPanels();
        setVisible(true);
    }
    public void setUpConnections() throws IOException {
        CONNECTIONS.put("foodCart", new FoodCart());
        CONNECTIONS.put("marketStall", new MarketStall());
        CONNECTIONS.put("farm", new Farm());
        CONNECTIONS.put("factory", new Factory());
        CONNECTIONS.put("bank", new Bank());
        CONNECTIONS.put("slot machine", new SlotMachine());
        CONNECTIONS.put("carnival", new Carnival());
    }
    public void addPanels() throws IOException{
        setUpCenter(CENTER);
        add(CENTER, BorderLayout.CENTER);

        JPanel right = new JPanel();
        right.setLayout(new BorderLayout());
        right.setPreferredSize(new Dimension(WIDTH / 3, HEIGHT));

        UPGRADES.setPreferredSize(new Dimension( WIDTH / 3, HEIGHT / 4));
        UPGRADES.setBackground(CREAM);
        right.add(UPGRADES, BorderLayout.NORTH);

        setUpPurchasables();
        right.add(PURCHASABLES);
        add(right, BorderLayout.EAST);

        ICON_SPACE.setPreferredSize(new Dimension(WIDTH / 3, HEIGHT));
        ICON_SPACE.setBackground(MONEYGREEN);
        add(ICON_SPACE, BorderLayout.WEST);
    }
    public void setUpPurchasables() throws IOException {
        PURCHASABLES.setPreferredSize(new Dimension(WIDTH / 3, HEIGHT * 3 / 4));
        PURCHASABLES.setBackground(MARMALADE);
        PURCHASABLES.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0,0,0,0);
        Object[] objAry = CONNECTIONS.keySet().toArray();
        for(int i = 5 * page; i < 5 * (page + 1) && i < objAry.length; i++){
            setUpShop(PURCHASABLES, i % 5, (String)objAry[i], constraints);
        }
        constraints.gridy = 5;
        constraints.gridx = 1;
        JLabel pageNum = new JLabel("Page: " + (page + 1) + "/2");
        pageNum.setBackground(MARMALADE);
        PURCHASABLES.add(pageNum, constraints);
        constraints.gridx = 0;
        JButton leftArrow = new JButton(new ImageIcon(resize(ImageIO.read(new File("left_arrow.png")), 50, 50)));
        leftArrow.setBackground(MARMALADE);
        leftArrow.setBorder(null);
        leftArrow.setFocusPainted(false);
        PURCHASABLES.add(leftArrow, constraints);
        leftArrow.addActionListener(e -> flipPage(FLIP_BACKWARD, pageNum));
        constraints.gridx = 2;
        JButton rightArrow = new JButton(new ImageIcon(resize(ImageIO.read(new File("right_arrow.png")), 50, 50)));
        rightArrow.setBackground(MARMALADE);
        rightArrow.setBorder(null);
        rightArrow.setFocusPainted(false);
        rightArrow.addActionListener(e -> flipPage(FLIP_FORWARD, pageNum));
        PURCHASABLES.add(rightArrow, constraints);
    }
    public void flipPage(String flip, JLabel pageNum){
        if(flip.equals(FLIP_FORWARD) || flip.equals(FLIP_BACKWARD)){
            page = flip.equals("flipForward") ? Math.min(page + 1, CONNECTIONS.keySet().size() / 5 ):Math.max(page - 1, 0);
            pageNum.setText("Page: " + (page + 1) + "/2");
        }
        updatePurchasables();
    }
    public void updatePurchasables(){
        PURCHASABLES.setVisible(false);
        Component[] components = PURCHASABLES.getComponents();
        for (Component c : components) {
            if (!c.getBackground().equals(MARMALADE)) {
                PURCHASABLES.remove(c);
            }
        }
        Object[] objAry = CONNECTIONS.keySet().toArray();
        for(int i = 5 * page; i < 5 * (page + 1) && i < objAry.length; i++){
            setUpShop(PURCHASABLES, i % 5, (String)objAry[i], new GridBagConstraints());
        } PURCHASABLES.setVisible(true); } public void setUpCenter(JPanel center) { center.setLayout(new GridBagLayout()); GridBagConstraints constraints = new GridBagConstraints();
        center.setBackground(JEANS); constraints.insets = new Insets(0,0,20,0);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.ipadx = WIDTH / 4;
        constraints.ipady = (HEIGHT * 13) / 100;
        WORTH.setText("<html>Worth: $" + netWorth + "<br>CPS: $" + wps + "<html>");
        WORTH.setFont(new Font(Font.MONOSPACED, Font.BOLD, 35));
        WORTH.setPreferredSize(new Dimension(400, 200));
        JPanel worthDisplay = new JPanel();
        worthDisplay.setBackground(JEANS);
        worthDisplay.setLayout(new FlowLayout());
        worthDisplay.add(WORTH);
        constraints.ipadx = WIDTH / 8;
        constraints.ipady = WIDTH / 17;
        center.add(worthDisplay, constraints);
        for (int i = 1; i < 6; i++) {
            JPanel gimmick = new JPanel();
            gimmick.setBackground(Color.black);
            gimmick.setLayout(new FlowLayout());
            JLabel gimmickLabel = new JLabel("Coming Soon...");
            gimmickLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 35));
            gimmickLabel.setForeground(Color.green);
            gimmickLabel.setPreferredSize(new Dimension(300, 200));
            gimmick.add(gimmickLabel);
            constraints.gridy = i;
            center.add(gimmick, constraints);
        }
    }
    public void setUpShop(JPanel shop, int y, String fileName, GridBagConstraints c) {
        c.gridx = 0;
        c.gridy = y;
        c.ipadx = 100;
        c.ipady = 25;
        Assets asset = CONNECTIONS.get(fileName);
        BufferedImage shopIcon = resize(asset.icon, 100, 100);
        JLabel icon = new JLabel(new ImageIcon(shopIcon), SwingConstants.LEFT);
        shop.add(icon, c);
        c.gridx = 1;
        c.gridy = y;
        JLabel text = new JLabel("<html>Cost: $" + asset.cost + "<br>Refund: $"
                                        + asset.cost.divide(new BigInteger("2"))
                                        + "<br>Revenue: $" + asset.getRevenue()
                                        +"<br>Owned: " + asset.count + "<html>");
        text.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        text.setForeground(MONEYGREEN);
        text.setBackground(new Color(0, 0, 0, 0));
        shop.add(text, c);
        c.gridx = 2;
        c.gridy = y;
        c.ipady = 0;
        addBuySell(shop, c, asset);
    }
    public static BufferedImage resize(BufferedImage icon, int width, int height) {
        Image temp = icon.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage newIcon = new BufferedImage(width, height, icon.getType());
        Graphics2D g = newIcon.createGraphics();
        g.drawImage(temp, 0, 0, null);
        g.dispose();
        return newIcon;
    }
    public void addBuySell(JPanel panel, GridBagConstraints constraints, Assets a){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        JButton sell = new JButton("sell");
        sell.addActionListener(e -> {
            if(a.count.compareTo(new BigInteger("0")) > 0) {
                BigInteger subtracted = wps.subtract(a.revenue);
                if (subtracted.compareTo(new BigInteger("0")) >= 0) {
                    wps = subtracted;
                }
                a.decreaseCount();
                updatePurchasables();
                updateCenter();
            }
        });
        JButton buy = new JButton("buy");
        buy.addActionListener(e -> {
            wps = wps.add(a.revenue);
            a.increaseCount();
            updatePurchasables();
            updateCenter();
        });
        buttonPanel.add(sell, BorderLayout.SOUTH);
        buttonPanel.add(buy, BorderLayout.NORTH);
        panel.add(buttonPanel, constraints);
    }
    public void updateCenter() {
        CENTER.setVisible(false);
        if(!ISACTIVE) {
            Thread t = new Thread(() -> {
                ISACTIVE = true;
                while (true) {
                    netWorth = netWorth.add(wps);
                    WORTH.setText("<html>Worth: $" + netWorth + "<br>CPS: $" + wps + "<html>");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            t.start();
        }
        else{
            WORTH.setText("<html>Worth: $" + netWorth + "<br>CPS: $" + wps + "<html>");
        }
        CENTER.setVisible(true);
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        new MoneyMakerRunner();
    }
}