package com.pl.nadgodziny.vaadin;

import com.pl.nadgodziny.service.OvertimeApiService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("main")
public class MainView extends VerticalLayout {
    private VaadinFinaAllView vaadinFinaAllView;
    private AddOvertimeView addOvertimeView;
    private  Button findAllButton ;
    private Button addOvertimeButton;
    private OvertimeApiService overtimeApiService;


    public MainView() {

        setMargin(true);
        setSpacing(false);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setAlignItems(Alignment.CENTER);
        getElement().getStyle().set("position", "relative");
        vaadinFinaAllView = new VaadinFinaAllView(null);
        addOvertimeView = new AddOvertimeView(overtimeApiService);



        findAllButton = new Button("Pokaż wszystkie nadgodziny");
        findAllButton.addClickListener(event -> {
            UI.getCurrent().navigate(VaadinFinaAllView.class);
        });

        addOvertimeButton = new Button("Dodaj nadgodziny");
        addOvertimeButton.addClickListener(event -> {
            UI.getCurrent().navigate(AddOvertimeView.class);
        });

        findAllButton.getElement().getStyle().set("top", "50%");



        add(addOvertimeButton,findAllButton);
    }
}
