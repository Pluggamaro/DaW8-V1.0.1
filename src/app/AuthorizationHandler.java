package app;

import java.time.LocalDate;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;

public class AuthorizationHandler {

	public static void setupContextMenu(final TableView<User> pendingUsersTableView, final TableView<User> activeUsersTableView) {
		final ContextMenu contextMenu = new ContextMenu();
		final MenuItem authorizeItem = new MenuItem("Authorize");
		contextMenu.getItems().add(authorizeItem);

		pendingUsersTableView.setContextMenu(contextMenu);

		authorizeItem.setOnAction(event -> {
			final User selectedUser = pendingUsersTableView.getSelectionModel().getSelectedItem();
			if (selectedUser != null) {
				showAuthorizationConfirmation(selectedUser, pendingUsersTableView,activeUsersTableView);
			}
		});
	}

	private static void showAuthorizationConfirmation(final User user, final TableView<User> pendingUsersTableView, final TableView<User> activeUsersTableView) {
		final Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
		confirmationAlert.setTitle("Authorization Confirmation");
		confirmationAlert.setHeaderText("Do you want to authorize the " + user.getUsername() +" as " + user.getRole() + "?");

		final Optional<ButtonType> result = confirmationAlert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			/* Update status to "Approved" in the database
             Insert current date into "Date of Action" column
             Move user from Pending Users to Active Users*/
			user.setStatus("Approved");
			user.setDateOfAction(LocalDate.now());
			activeUsersTableView.getItems().add(user);
			pendingUsersTableView.getItems().remove(user);
			activeUsersTableView.refresh();
			pendingUsersTableView.refresh();
		}
	}

	public static void restoreUserContextMenu(final TableView<User> blockedUsersTableView, final TableView<User> activeUsersTableView) {
		final ContextMenu contextMenu = new ContextMenu();
		final MenuItem authorizeItem = new MenuItem("Unblock");
		contextMenu.getItems().add(authorizeItem);

		blockedUsersTableView.setContextMenu(contextMenu);

		authorizeItem.setOnAction(event -> {
			final User selectedUser = blockedUsersTableView.getSelectionModel().getSelectedItem();
			if (selectedUser != null) {
				showUnblockConfirmation(selectedUser, blockedUsersTableView,activeUsersTableView);
			}
		});
	}

	private static void showUnblockConfirmation(final User user, final TableView<User> blockedUsersTableView, final TableView<User> activeUsersTableView) {
		final Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
		confirmationAlert.setTitle("Authorization Confirmation");
		confirmationAlert.setHeaderText("Do you want to unblock/reactivate " + user.getUsername() + "?");

		final Optional<ButtonType> result = confirmationAlert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			/* Update status to "Approved" in the database
            / Insert current date into "Date of Action" column
            / Move user from Pending Users to Active Users */
			user.setStatus("Approved");
			activeUsersTableView.getItems().add(user);
			blockedUsersTableView.getItems().remove(user);
			activeUsersTableView.refresh();
			blockedUsersTableView.refresh();
		}
	}


}


