package program.gui.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

interface ComponentAllowed
{
    public void from(JButton obj, Runnable callback);
    public <T> void from(JComboBox<T> obj, Runnable callback);    
}

public class EventHandling implements ComponentAllowed
{
    public EventHandling(){}

    @Override
    public void from(JButton obj, Runnable callback)
    {
        obj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { callback.run(); }
        });
    }
    
    @Override
    public <T> void from(JComboBox<T> obj, Runnable callback)
    {
        obj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { callback.run(); }
        });
    }
}