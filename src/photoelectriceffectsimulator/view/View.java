package photoelectriceffectsimulator.view;

import photoelectriceffectsimulator.controller.AbstractView;
import photoelectriceffectsimulator.controller.ControllerViewCommunicator;
import photoelectriceffectsimulator.utilities.*;



/**
 * Obiekt widoku zajmuje się obsługą graficznego interfejsu użytkownika (GUI)
 * czyli pobieraniem od użytkownika danych,
 * oraz prezentacją obliczeń na ekranie
 * @author Jakub Sikora
 */
public class View implements AbstractView{
    /** Referencja do aktywnego kontrolera*/
    private ControllerViewCommunicator controller;
    /** Referencja do głównego okna aplikacji*/
    private MainFrame mainFrame;
    
    /**
     * Zwraca referencje do aktywnego kontrolera
     * @return referencja do aktywnego kontrolera 
     */
    public ControllerViewCommunicator getController(){
        return controller; 
    }
    
    /**
     * Ustaw referencje do obiektu kontrolera
     * @param controller referencja do obiektu kontrolera
     */
    public void setController(ControllerViewCommunicator controller){
        this.controller = controller;
    }
    
    /**
     * Inicjalizuje obiekt {@link MainFrame}
     * @throws NullPointerException rzuca gdy nie ma kontrolera
     */
    public void initializeMainFrame() throws NullPointerException {
        if(controller == null) throw new NullPointerException();
        mainFrame = new MainFrame(controller);
        mainFrame.setVisible(true);
    }
    
    /**
     * Ustaw typ metalu
     * @param newType typ metalu
     */
    @Override
    public void setElementType(String newType){
        mainFrame.getInfoPanel().changeElement(MetalType.getMetalInfo(MetalType.convertFromString(newType)));
    }
    
     /**
     * Ustaw pracę wyjścia
     * @param exitEnergy praca wyjścia
     */
    @Override
    public void setExitEnergy(double exitEnergy){
        mainFrame.getOutcomePanel().setExitEnergyDisplay(exitEnergy);
        mainFrame.getCathodePanel().setExitEnergy(exitEnergy);
    }
    
    /**
     * Ustaw prąd przyrządu
     * @param current prąd przyrządu 
     */
    @Override
    public void setOutcomeCurrent(ExpNumber current){
        mainFrame.getOutcomePanel().setCurrentDisplay(current);
        mainFrame.getCathodePanel().setCurrent(current);
    }
    
    /**
     * Ustaw energię fotonu
     * @param photonEnergy energia fotonu
     */
    @Override
    public void setPhotonEnergy(double photonEnergy){
        mainFrame.getOutcomePanel().setPhotonEnergyDisplay(photonEnergy);
        mainFrame.getCathodePanel().setPhotonEnergy(photonEnergy);
    }
    
    /**
     * Funkcja sprawdza czy wartość index znajduje się między wartościami a (łącznie)
     * i b (rozłącznie)
     * @param a dolne ograniczenie przedziału (łączne)
     * @param b górne ograniczenie przedziału (rozłączne)
     * @param index wartość sprawdzana
     * @return true gdy index znajduje się między a i b, w przeciwnym przypadku false
     */
    public static boolean isBetween(int a, int b, int index){
        if(index >= a && index < b) return true;
        else return false;
    }
    
}

