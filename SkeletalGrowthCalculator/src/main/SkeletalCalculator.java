package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SkeletalCalculator extends Application implements Runnable {

	@Override
	public void run() {
		
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		try{
			Scene scene = TitleViewController.getInstance().getScene();
			primaryStage.setScene(scene);
			primaryStage.show();
			
			primaryStage.setOnCloseRequest( new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent e) {
					Platform.exit();
					System.exit(0);
				}
			});
			
		}catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	public static void main( String[] args ) {
		launch(args);
	}
}
