package com.thecomet.monkeypid.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * @author Alex Murray
 */
public class MainWindowController {
    @FXML protected void handleTestButtonAction(ActionEvent event) {
        System.out.println("hello!");
    }
}
