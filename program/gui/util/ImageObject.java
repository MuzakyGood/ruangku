package program.gui.util;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class ImageObject
{
    private ImageIcon img;
    private JLabel imgLabel;

    public ImageObject(){}
    public ImageObject(String path)
    { 
        img = new ImageIcon(path);
        imgLabel = new JLabel(img);
    }

    public void setPosition(int x, int y)
    {
        imgLabel.setBounds(x, y, img.getIconWidth(), img.getIconHeight());
    }
    
    public JLabel getImgLabel() { return this.imgLabel; }
}