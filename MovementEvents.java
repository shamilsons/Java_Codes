package part2;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MovementEvents extends Application {
  private static final int      KEYBOARD_MOVEMENT_DELTA = 5;
  private static final Duration TRANSLATE_DURATION = Duration.millis(2000);

  public static void main(String[] args) { launch(args); }
  @Override public void start(Stage stage) throws Exception {
    final Circle circle = createCircle();
    final Group group = new Group(createInstructions(), circle);
    final TranslateTransition transition = createTranslateTransition(circle);
    
    final Scene scene = new Scene(group, 600, 400, Color.LIGHTGREY);
    moveCircleOnKeyPress(scene, circle);
    moveCircleOnMousePress(scene, circle, transition);
    
    stage.setTitle("Controling object with keys"); 
    stage.setScene(scene);
    stage.show();
  }

  private Label createInstructions() {
    Label instructions = new Label(
      "1. Use the arrow keys to move the circle in small increments\n" +
      "2. Click the mouse to move the circle to a given location\n" +
      "3. Ctrl + Click the mouse to slowly translate the circle to a given location"      
    );
    instructions.setTextFill(Color.BLUE);
    return instructions;
  }

  private Circle createCircle() {
    final Circle circle = new Circle(200, 150, 50, Color.BROWN);
    circle.setOpacity(0.6);
    return circle;
  }

  private TranslateTransition createTranslateTransition(final Circle circle) {
    final TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, circle);
    transition.setOnFinished(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent t) {
    	
        circle.setCenterX(circle.getTranslateX() + circle.getCenterX());
        circle.setCenterY(circle.getTranslateY() + circle.getCenterY());
        
        System.out.println("circle.getTranslateX() : "+circle.getTranslateX()+ " circle.getTranslateY() : "+circle.getTranslateY());
        System.out.println("circle.getCenterX() : "+circle.getCenterX()+ " circle.getCenterY() : "+circle.getCenterY());
      
        circle.setTranslateX(0);
        circle.setTranslateY(0);
       }
    });
    return transition;
  }

  private void moveCircleOnKeyPress(Scene scene, final Circle circle) {
    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override public void handle(KeyEvent event) {
        switch (event.getCode()) {
          case UP:    circle.setCenterY(circle.getCenterY() - KEYBOARD_MOVEMENT_DELTA); break;
          case RIGHT: circle.setCenterX(circle.getCenterX() + KEYBOARD_MOVEMENT_DELTA); break;
          case DOWN:  circle.setCenterY(circle.getCenterY() + KEYBOARD_MOVEMENT_DELTA); break;
          case LEFT:  circle.setCenterX(circle.getCenterX() - KEYBOARD_MOVEMENT_DELTA); break;
        }
      }
    });
  }

  private void moveCircleOnMousePress(Scene scene, final Circle circle, final TranslateTransition transition) {
    scene.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent event) {
        if (!event.isControlDown()) {
          circle.setCenterX(event.getSceneX());
          circle.setCenterY(event.getSceneY());
          System.out.println("event.getSceneX() : "+event.getSceneX()+ " event.getSceneY() : "+event.getSceneY());
        } else {
          transition.setToX(event.getSceneX() - circle.getCenterX());
          transition.setToY(event.getSceneY() - circle.getCenterY());
          transition.playFromStart();
        }  
      }
    });
  }
}
