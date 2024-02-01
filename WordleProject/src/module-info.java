module WordleProject {
	exports src.model;
	exports src.view;
	exports src.tests;

	requires java.desktop;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.media;
	requires org.hamcrest.core;
	requires org.junit.jupiter.api;
	
	requires javafx.fxml;
}