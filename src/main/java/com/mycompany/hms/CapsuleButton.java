/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.mycompany.hms;

import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
import java.beans.BeanProperty;
import javax.swing.plaf.ColorUIResource;


/**
 *
 * @author Muhammad Buckus
 */
public class CapsuleButton extends JButton{
    boolean mousePressed = false;
    boolean mouseOver = false;
    private Color colorDefualt = new Color(255,255,255);
    private Color colorPressed = new Color(153,153,153);
    private Color colorHover = new Color(204,204,204);
    private int hSize = 47;
    private int vSize = 30;
    Dimension componentSize = new Dimension(hSize,vSize);
    Font font = new Font("Arial", Font.PLAIN, 14);
    
    public CapsuleButton(){
        this.setContentAreaFilled(false); 
        this.setOpaque(false);
        this.setBorderPainted(false);
        //this.setBackground(new Color(0, 0, 0, 0));
        
        addMouseListener(mouseListener);
	addMouseMotionListener(mouseListener);	
         this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                componentSize = getSize();
                hSize = componentSize.width;
                vSize = componentSize.height;
                //backgroundSet();
                repaint();
                
            }
        });
//         backgroundSet();
//         repaint();
//         if (java.beans.Beans.isDesignTime()) {
//        setBackground(this.getBackground()); // Design-time background color
//        }
    }

   MouseAdapter mouseListener = new MouseAdapter(){

			@Override
			public void mousePressed(MouseEvent me){
				if(contains(me.getX(), me.getY())){
					mousePressed = true;
					repaint();
				}
			}

			@Override
			public void mouseReleased(MouseEvent me){
				mousePressed = false;
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent me){
				mouseOver = false;
				mousePressed = false;
				repaint();
			}

			@Override
			public void mouseMoved(MouseEvent me){
				mouseOver = contains(me.getX(), me.getY());
				repaint();
			}
		};

   
//   @Override
//    protected void paintComponent(Graphics grphcs){
//        super.paintComponent(grphcs);
//        Graphics2D g = (Graphics2D)grphcs;
//         if(mousePressed){
//            this.setColor(colorPressed);
//            g.fill(this);
//        }
//        if(!mousePressed && mouseOver){
//            g.setColor(colorHover);
//            g.fill(e);
//        }
//        if(!mousePressed && !mouseOver){
//            g.setColor(colorDefualt);
//            g.fill(e);
//        }
//    }
//   
   
//    public void colorChange(){
//        if(mousePressed){
//            this.getGraphics().setColor(colorPressed);
//            //g.fill(e);
//            repaint();
//        }
//        if(!mousePressed && mouseOver){
//            this.getGraphics().setColor(colorHover);
//            //g.fill(e);
//            repaint();
//        }
//        if(!mousePressed && !mouseOver){
//            this.getGraphics().setColor(colorDefualt);
//            //g.fill(e);
//            repaint();
//        }
//    }
    
   @Override
    protected void paintComponent(Graphics grphcs){
         super.paintComponent(grphcs);
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        Graphics2D g = (Graphics2D)grphcs;
        g.setRenderingHints(rh);
        g.setColor(colorDefualt);
        
        RoundRectangle2D.Double r = new RoundRectangle2D.Double();
        r.setRoundRect(0,0,hSize,vSize,vSize,vSize);
     
        g.fill(r);
        
   if(mousePressed){
            g.setColor(colorPressed);
            g.fill(r);
        }
        if(!mousePressed && mouseOver){
            g.setColor(colorHover);
            g.fill(r);
        }
        if(!mousePressed && !mouseOver){
            g.setColor(colorDefualt);
            g.fill(r);
        }
     //code for adding icon   
   Icon icon = getIcon();
    if (icon != null) {
        int iconX = (getWidth() - icon.getIconWidth()) / 2;
        int iconY = (getHeight() - icon.getIconHeight()) / 2;
        icon.paintIcon(this, g, iconX, iconY);
    }
    
    //code for adding text
     String text = getText();
    if (text != null && !text.isEmpty()) {
        g.setColor(getForeground()); // Set text color to the button's foreground color
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + textHeight) / 2 - fm.getDescent();
        g.drawString(text, x, y+2);
    }
    
    } 
    
    @BeanProperty(description = "Set the font of the text")
    public void setButtonFont(Font font){
        this.font = font;
        repaint();
    }
    
    @BeanProperty(description = "The background color of the component")
    public void setcolorDefualt(Color colorDefualt){
        this.colorDefualt = colorDefualt;
        repaint();
    }
    
    @BeanProperty(description = "The color of the component when pressed")
    public void setcolorPressed(Color colorPressed){
        this.colorPressed = colorPressed;
        repaint(); 
    }
    
    @BeanProperty(description = "The color of the component when hovered over")
    public void setcolorHover(Color colorHover){
        this.colorHover = colorHover;
        repaint(); 
    }
    
@Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        repaint();
    }
    
    public void backgroundSet(){
        setBackground(this.getBackground());
    }
}
