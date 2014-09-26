package me.trochee.app.view;

import io.dropwizard.views.View;

public class TrocheesView extends View {

    private final String trochee;

    public TrocheesView(String trochee) {
        super("/views/me/trochee/app/view/TrocheesView.mustache");
        this.trochee = trochee;
    }

    public String getTrochee() {
        return trochee;
    }
}
