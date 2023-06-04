package modele;

import javafx.animation.Transition;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.util.Duration;
import vue.FightGP;

public class Timer extends BorderPane{

    private Arc BackgroundCircle;
    private Arc LoadingCircle;
    private Transition LoadingTransition;
    private Group LoadingGroup;
    private FightGP fight;
    private int time;

    public Timer(FightGP fgp, int time) {
    	this.fight = fgp;
    	this.time = time;
        getChildren().add(getLoadingGroup());
        LoadingTransition();
    }

    public Arc getBackgroundCircle() {
        if (BackgroundCircle == null) {
            BackgroundCircle = new Arc(10, 10, 25, 25, 0, 360);
            BackgroundCircle.setStroke(Color.BLACK);
            BackgroundCircle.setStrokeWidth(4);
            BackgroundCircle.setFill(Color.LIGHTGRAY);
        }
        return BackgroundCircle;
    }

    public Arc getLoadingCircle() {
        if (LoadingCircle == null) {
            BackgroundCircle = getBackgroundCircle();
            LoadingCircle = new Arc(10, 10, 22.5, 22.5, 0, 360);
            LoadingCircle.setType(ArcType.ROUND);
            LoadingCircle.setFill(Color.web("#B051B8").brighter().brighter());
        }
        return LoadingCircle;
    }

    public Group getLoadingGroup() {
        if (LoadingGroup == null) {
            LoadingGroup = new Group();
            LoadingGroup.getChildren().add(getBackgroundCircle());
            LoadingGroup.getChildren().add(getLoadingCircle());
        }
        return LoadingGroup;
    }
    
    public Transition LoadingTransition() {
        LoadingTransition = new Transition() 
        {
        	{
        		setCycleDuration(Duration.seconds(time));
        	}

            @Override
            protected void interpolate(double frac) {
                getLoadingCircle().setLength(360.0 * frac);
                if(frac == 1) {fight.getBtnSubmit().fire();}
            }
        };
        LoadingTransition.play();
        return LoadingTransition;
    }
}
