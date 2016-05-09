package gov.sc.form.listener;

import gov.sc.form.Form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpenMIListener implements ActionListener {

	private Form form;

	/**
	 * @param form
	 */
	public OpenMIListener(Form form) {
		this.form = form;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		new ScanButListnener(form).actionPerformed(e);
	}

}
