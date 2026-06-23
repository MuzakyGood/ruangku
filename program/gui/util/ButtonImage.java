package program.gui.util;

import java.awt.Image;

import javax.swing.JButton;
import javax.swing.ImageIcon;

// Class agar button bisa di tempel gambar

public class ButtonImage
{
    private JButton btn;
    private ImageIcon img;
    private String path;

    public ButtonImage(){ btn = new JButton(); }
    public ButtonImage(String path) {
        this.path = path; 
        btn = new JButton();
    }

    public void setLoadFile(String path) { this.path = path; }
    public void setBounds(int x, int y, int width, int high)
    {
        ImageIcon origin = new ImageIcon(path);
        Image changerImg = origin.getImage().getScaledInstance(width - 10, high - 10, Image.SCALE_SMOOTH);
        img = new ImageIcon(changerImg);

        btn.setIcon(img);
        btn.setBounds(x, y, width, high);
    }

    public JButton getButton() { return btn; }
}