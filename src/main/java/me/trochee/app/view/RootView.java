package me.trochee.app.view;

import io.dropwizard.views.View;

public class RootView extends View {

    private final String trochee;

    public RootView(String trochee) {
        super("/views/me/trochee/app/view/root.mustache");
        this.trochee = trochee;
    }

    public String getTrochee() {
        return trochee;
    }
}
