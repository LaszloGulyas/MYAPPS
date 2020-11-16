import java.awt.*;
import java.awt.event.*;

public class UserInterface implements ActionListener {

    //1st step
    public UserInterface() {

        Frame f = new Frame("WOW login");
        Button b = new Button("EXIT");

        f.setSize(200,100);
        f.setLocation(800,300);
        f.setVisible(true);

        b.setBounds(50,50,60,30);
        b.addActionListener(this);

        f.add(b);
        //f.setState(Frame.ICONIFIED);
    }

    //3rd step
    public void actionPerformed(ActionEvent e){
        System.exit(0);
    }
}