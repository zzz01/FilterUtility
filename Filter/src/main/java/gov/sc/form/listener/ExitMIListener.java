package gov.sc.form.listener;

import gov.sc.form.Form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitMIListener implements ActionListener {

	private Form form;

	public ExitMIListener(Form form) {
		this.form = form;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		form.jFrame.dispose();
	}
}
