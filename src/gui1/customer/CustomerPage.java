package gui1.customer;

import code.behavior.ButtonFactory;
import code.behavior.CustomerModel;
import code.behavior.Table;
import code.customer.Customer;
import code.store.Store;
import gui.HistoryOfCustomerPage;
import gui1.main.MainPage;
import gui1.shopping.ShoppingPage;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

/**
 * @author kamontat
 * @since 21/5/59 - 22:53
 */
public class CustomerPage extends JFrame implements Table, ButtonFactory {
	private Store store = Store.getInstance();

	private JPanel panel;
	private JTextField searchField;
	private JButton addButton;
	private JTable table;
	private JButton mainButton;
	private JButton selectButton;
	private JButton removeButton;
	private JButton historyButton;
	private JButton guestButton;

	private CustomerModel model;

	public CustomerPage() {
		super("Customer Page");
		setContentPane(panel);
		model = new CustomerModel(store.getAllCustomer(), new String[]{"ID", "Name", "LastName", "Gender", "Age", "Class"});

		settingTable();

		add();

		selectButton.addActionListener(e -> {
			int row = table.getSelectedRow();
			if (checkValid(row)) {
				MainPage.shopper = getCustomerAt(row);
				openShoppingPage();
			}
		});

		guestButton.addActionListener(e -> {
			MainPage.shopper = store.getGuest();
			openShoppingPage();
		});

		history();
		remove();

		toMain(this, mainButton);
	}

	private void settingTable() {
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// Disable dragging
		table.getTableHeader().setReorderingAllowed(false);

		table.setModel(model);

		table.getColumnModel().getColumn(0).setMinWidth(50); // id
		table.getColumnModel().getColumn(1).setMinWidth(100); // name
		table.getColumnModel().getColumn(2).setMinWidth(120); // last name
		table.getColumnModel().getColumn(3).setMinWidth(50); // gender
		table.getColumnModel().getColumn(4).setMinWidth(50); // age
		table.getColumnModel().getColumn(5).setMinWidth(75); // class

		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(rowSorter);
		searching(searchField, rowSorter);
	}

	private void add() {
		addButton.addActionListener(e -> {
			AdderCustomerPage adderPage = new AdderCustomerPage();
			adderPage.run(getLocation());
			if (adderPage.getNewCustomer() != null) {
				model.addRow(adderPage.getNewCustomer().getCustomerInfo(6));
			}
			resetSelection(table);
		});
	}

	private void openShoppingPage() {
		ShoppingPage shopping = new ShoppingPage();
		shopping.run(getLocation());
		dispose();
		resetSelection(table);
	}

	private void remove() {
		removeButton.addActionListener(e -> {
			int row = table.getSelectedRow();
			if (checkValid(row)) {
				int customerIndex = store.removeCustomer(getCustomerAt(row), false);
				model.removeRow(customerIndex);
				MainPage.reWriteCustomer();
			}
			resetSelection(table);
		});
	}

	private void history() {
		historyButton.addActionListener(e -> {
			int row = table.getSelectedRow();
			HistoryOfCustomerPage history = new HistoryOfCustomerPage(getCustomerAt(row));
			history.run();
		});
	}

	private Customer getCustomerAt(int row) {
		String id = String.valueOf(table.getValueAt(row, 0));
		return store.searchIDCustomer(id);
	}

	public void run(Point point) {
		setMinimumSize(new Dimension(650, 530));
		pack();
		setLocation(point);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
