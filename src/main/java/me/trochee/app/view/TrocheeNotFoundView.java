package me.trochee.app.view;

import io.dropwizard.views.View;

public class TrocheeNotFoundView extends View{

    private final String notFound;

    public TrocheeNotFoundView(String notFound) {
        super("/views/me/trochee/app/view/TrocheeNotFoundView.mustache");
        this.notFound = notFound;
    }

    public String getNotFound() {
        return notFound;
    }
}
