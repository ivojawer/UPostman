package ui;

import domain.Request;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.FontUIResource;

public class UriInput extends javax.swing.JTextField implements RequestObserver {
    Boolean listening;
    PanelManager panelManager;
    DocumentListener documentListener;

    public UriInput(PanelManager panelManager) {
        super(30);
        this.panelManager = panelManager;
        this.setFont(new FontUIResource("Arial", FontUIResource.PLAIN, 15));
        this.listening = true;
        documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                reactToChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                reactToChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                reactToChange();
            }
        };
        this.getDocument().addDocumentListener(
            documentListener
        );
    }

    private void reactToChange(){
        listening = false;
        panelManager.setURI(getText());
        listening = true;
    }

    @Override
    public void newRequest(Request newRequest) {
        if(listening){
            this.getDocument().removeDocumentListener(documentListener);
            this.setText(newRequest.getURI());
            this.getDocument().addDocumentListener(documentListener);
        }
    }
}
