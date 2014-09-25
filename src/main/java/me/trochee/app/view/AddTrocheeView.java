package me.trochee.app.view;

import com.google.gson.Gson;
import io.dropwizard.views.View;

import java.util.List;

public class AddTrocheeView  extends View{

    private final List<String> animatedTrochees;

    public AddTrocheeView(List<String> animatedTrochees) {
        super("/views/me/trochee/app/view/AddTrocheeView.mustache");
        this.animatedTrochees = animatedTrochees;
    }

    public String getAnimatedTrocheesJson(){
        return new Gson().toJson(animatedTrochees);
    }
}
