module PharmBinCard {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
	requires javafx.graphics;
	requires java.desktop;
	requires java.sql;
	requires javafx.base;
	requires java.base;
	//requires org.eclipse.sisu.inject;
	

    opens app to javafx.graphics, javafx.fxml, javafx.base;
}

