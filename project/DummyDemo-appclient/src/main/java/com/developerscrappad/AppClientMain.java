package com.developerscrappad;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AppClientMain extends JFrame {

    public AppClientMain() {
        init();
    }

    protected void init() {
        //Layout
        JPanel panel = new JPanel();
        panel.add( new JLabel("This is an Enterprise Application Client") );

        //JFrame
        setTitle( "App Client" );
        setSize( 300, 200 );
        setLocationRelativeTo( null );
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        add( panel );
    }

    public static void main( String[] args ) {
        AppClientMain appClient = new AppClientMain();
        appClient.setVisible( true );
    }
}
