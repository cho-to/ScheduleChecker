import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

class IdFrame extends JFrame implements ActionListener{

	private JTextField idTextField;
	private JPanel p1, p2, p3;
	private JLabel detailLabel, idLabel;
	private JButton EnterButton;
	private String id;
	
	IdFrame() {
		setupComp();
		setTitle("Enter ID");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
      	setSize(350, 150);
		setVisible(true);
	}
	
	private void setupComp() {
		setLayout(new BorderLayout());
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		
		
        detailLabel = new JLabel("Enter your id to use program.");
        detailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailLabel.setHorizontalAlignment(JLabel.CENTER);
        
        idLabel = new JLabel("Your ID: ");
        idTextField = new JTextField(10);
        EnterButton = new JButton("Enter");
        EnterButton.addActionListener(this);
        
        p1.add(detailLabel);
        p2.add(idLabel);
        p2.add(idTextField);
        p3.add(EnterButton);
        
       
        add(p1, BorderLayout.NORTH);
        add(p2, BorderLayout.CENTER);
        add(p3, BorderLayout.SOUTH);

	}
	
	public String getId() {
		return id;
	}

	@Override
	public void actionPerformed(ActionEvent e) {        
		if (e.getSource() == EnterButton && idTextField.getText().trim().length() > 0) { 
			id = idTextField.getText();
			SchedulerFrame frameScheduler = new SchedulerFrame(id);
			dispose();
        } 
		
  }
	
}