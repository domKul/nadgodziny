package com.pl.nadgodziny.vaadin;

import com.pl.nadgodziny.model.Overtime;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


@Route("vaadin-app")
public class VaadinFinaAllView extends VerticalLayout {

    private final RestTemplate restTemplate;
    @Value("${vaadin.find.all.url}")
    private  String FIND_ALL;


    private final Grid<Overtime> grid = new Grid<>(Overtime.class);

    @Autowired
    public VaadinFinaAllView(RestTemplate restTemplate) {
        setMargin(true);
        setSpacing(false);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setAlignItems(Alignment.CENTER);
        getElement().getStyle().set("position", "relative");

        this.restTemplate = restTemplate;
        Button backToMainButton = new Button("back");
        backToMainButton.addClickListener(event -> {
            UI.getCurrent().navigate(MainView.class);
        });

        Button fetchDataButton = new Button("Pobierz Nadgodziny");
        fetchDataButton.addClickListener(event -> fetchOvertimes());

        grid.setColumns("overtimeDate", "status", "duration");

        fetchDataButton.getElement().getStyle().set("top", "50%");

        add(grid,fetchDataButton,backToMainButton);
    }

    private void fetchOvertimes() {
        Overtime[] overtimes = restTemplate.getForObject(FIND_ALL, Overtime[].class);
        if (overtimes != null && overtimes.length > 0) {
            List<Overtime> overtimeList = Arrays.asList(overtimes);
            grid.setItems(overtimeList);
        } else {
            Notification.show("Lista jest pusta", 3000, Notification.Position.MIDDLE);
        }
    }

}
