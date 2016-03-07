package main;

import controllers.TitleViewController;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SkeletalCalculator extends Application implements Runnable {
	
	private static Stage activeStage;
	
	@Override
	public void run() {
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		setStage( primaryStage );
		
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
	
	public void setScene( Scene newScene ) {
		this.getStage().setScene(newScene);
		this.getStage().show();
	}
	
	public Scene getScene() {
		return this.getStage().getScene();
	}
	
	public void setStage( Stage newStage ) {
		SkeletalCalculator.activeStage = newStage;
	}
	
	public Stage getStage() {
		return SkeletalCalculator.activeStage;
	}
	
	public static void main( String[] args ) {
		launch(args);
	}
}
