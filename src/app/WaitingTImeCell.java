package app;

import javafx.scene.control.TableCell;

public class WaitingTImeCell extends TableCell<BinCard, Integer> {

	
    @Override
    protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);

        // Clear existing styles
        //this.setStyle("");

        
        
        // Check if the item is not empty and within the specified range
        if (!empty && item != null) {
            if (item >= 1 && item < 5) {
                this.setStyle("-fx-background-color: orange;");
            } else if (item >= 5) {
                this.setStyle("-fx-background-color: red;");
            }
        }
    }
}

