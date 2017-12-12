package photoelectriceffectsimulator.view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * Kontener obsługujący wyświetlaną animacje na ekranie 
 * 
 * @author Jakub Sikora
 */
public class CathodePanel extends JPanel {
    /** Przechowuje informację o aktualnej wartości natężenia światła */
    private double lightIntensity;
    
    /** Przechowuje informację o aktualnej wartości długości światła*/
    private double waveLength;
    
    /** Przechowuje informację o aktualnej wartości przyłożonego napięcia */
    private double voltage;
    
    /**
     * Konstruktor panelu grafiki z fotokomórką
     * @param dimension rozmiar panelu  
     */
    CathodePanel(Dimension dimension){
        lightIntensity = 0;
        waveLength = 500;
        
        super.setMinimumSize(dimension);
        super.setPreferredSize(dimension);
        super.setMaximumSize(dimension);
    }
    
    /**
     * Nadpisana metoda paintComponent tak aby nna ekranie znajdowała się fotokatoda
     * @param g obiekt grafiki
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D graph2d = (Graphics2D) g;
        graph2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                 RenderingHints.VALUE_ANTIALIAS_ON);
        
        drawPhotodevice(graph2d);
        drawLight(graph2d, evaluateColor(), evaluateIntensity());
        drawVoltageSigns(graph2d, voltage);
    }
    
    /**
     * Funkcja rysuje na ekranie fotoprzyrząd
     * @param graph2d obiekt grafiki komponentu który ma narysować przyrząd
     */
    private void drawPhotodevice(Graphics2D graph2d){
        BasicStroke previousStroke = (BasicStroke)graph2d.getStroke();
        graph2d.setStroke(new BasicStroke(10));
        
        //katoda
        Shape cathode = new Line2D.Float(130,40,130,430);
        graph2d.draw(cathode);

        //anoda 
        Shape anode = new Line2D.Float(370,40,370,430);
        graph2d.draw(anode);

        //kabel katody 
        Shape cathodeWire = new Line2D.Float(15,230,130,230);
        graph2d.draw(cathodeWire);

        //kabel anody
        Shape anodeWire = new Line2D.Float(370,230,480,230);
        graph2d.draw(anodeWire);
        
        graph2d.setStroke(previousStroke);
    }
    
    /**
     * Funkcja rysuje snop światła o odpowiednim kolorze i jasności
     * @param graph2d obiekt grafiki który rysuje komponent
     * @param color kolor snopu światła
     * @param intensity jasność snopu światła
     */
    private void drawLight(Graphics2D graph2d, Color color, float intensity){
        BasicStroke previousStroke = (BasicStroke)graph2d.getStroke();
        graph2d.setStroke(new BasicStroke(0));
        int xPoints[] = {135,135,270};
        int yPoints[] = {37,435,5};
        Polygon lightBeam = new Polygon(xPoints, yPoints, 3);
        graph2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, intensity));
        graph2d.draw(lightBeam);
        graph2d.setColor(color);
        graph2d.fill(lightBeam);
        graph2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0F));
        graph2d.setStroke(previousStroke);
    }
    
    private void drawVoltageSigns(Graphics2D graph2d, double voltage){
        if(voltage == 0) return;
        
        //napięcie dodatnie
        if(voltage > 0){
            BasicStroke previousStroke = (BasicStroke)graph2d.getStroke();
            graph2d.setStroke(new BasicStroke(8));
            Shape minus = new Line2D.Float(10,90,30,90);
            graph2d.draw(minus);
            
            
            graph2d.setStroke(previousStroke);
        }
    }
    
    /**
     * Funkcja obsługuje zmianę jasności przez użytkownika
     * @param newIntensity nowa jasność
     */
    public void setLightIntensity(int newIntensity){
        lightIntensity = newIntensity;
        this.repaint();
    }
    
    /**
     * Funkcja obsługuje zmianę długości fali
     * @param newWaveLength nowa długość fali
     */
    public void setLightColor(int newWaveLength){
        waveLength = newWaveLength;
        this.repaint();
    }
    
    public void setVoltageSign(double voltage){
        this.voltage = voltage;
        this.repaint();
    }
    
    /**
     * Funkcja zwraca odpowiednią wartość jasności
     * @return odpowiednio przeskalowana jasność 
     */
    private float evaluateIntensity(){
        if(isBetween(350,781))
            return 0.65F*((float)lightIntensity)/100;
        else 
            return 0.65F*0.6F*((float)lightIntensity)/100;
    }

    /**
     * Zwróć kolor w rgb na podstawie długości fali padającej
     * @return kolor w rgb
     */
    private Color evaluateColor(){   
        double rgb[] = new double[3];
        double Gamma = 0.80;
        double iMax = 255;
        
        if(isBetween(340, 440)){
            rgb[0] = (440 - waveLength)/60;
            rgb[1] = 0.0;
            rgb[2] = 1.0;
        }
        
        if(isBetween(440,490)){
            rgb[0] = 0.0;
            rgb[1] = (waveLength - 440)/50; 
            rgb[2] = 1.0;
        }
        
        if(isBetween(490,510)){
            rgb[0] = 0.0; 
            rgb[1] = 1.0;
            rgb[2] = (510 - waveLength)/20; 
        }
        
        if(isBetween(510,580)){
            rgb[0] = (waveLength - 510)/70;
            rgb[1] = 1.0;
            rgb[2] = 0.0;
        }
        
        if(isBetween(580,645)){
            rgb[0] = 1.0;
            rgb[1] = (645 - waveLength)/65;
            rgb[2] = 0.0;
        }
        
        if(isBetween(645,781)){
            rgb[0] = 1.0; 
            rgb[1] = 0.0;
            rgb[2] = 0.0;
        }
        
        if(waveLength > 781){
            rgb[0] = 0.5;
            rgb[1] = 0.0;
            rgb[2] = 0;
        }
        
        double factor;
        
        factor = 0.2;
        if(isBetween(380,420)){
            factor = 0.3 + 0.7*(waveLength - 380)/60;
        }
        if(isBetween(420,701)){
            factor = 1.0;
        }
        if(isBetween(701,781)){
            factor = 0.3 + 0.7*(780 - waveLength)/80;
        }
        if(!isBetween(380,781)){
            factor = 0.1;
        }
        
        int RGB[] = new int[3];  
        for(int i = 0; i < 3; i++){
            RGB[i] = (rgb[i]==0) ? 0 :(int)Math.round(iMax*Math.pow(rgb[i]*factor, Gamma));
        }
        
        return new Color(RGB[0], RGB[1], RGB[2]);
    }
    
    /**
     * Sprawdza czy długośc fali znajduje się pomiędzy dwoma wartościami
     * @param a
     * @param b
     * @return 
     */
    private boolean isBetween(int a, int b){
        if(waveLength >= a && waveLength < b) return true;
        else return false;
    }
}
