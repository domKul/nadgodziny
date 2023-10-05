package com.pl.nadgodziny.vaadin;

import com.pl.nadgodziny.model.Overtime;
import com.pl.nadgodziny.service.OvertimeApiService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("add-overtime")
public class AddOvertimeView extends VerticalLayout {

    private final OvertimeApiService overtimeApiService;
    private final Binder<Overtime> binder;

    private final DatePicker overtimeDate;
    private final ComboBox<String> status;
    private final NumberField hours;
    private final Button saveButton;
    private final Button backButton;

    @Autowired
    public AddOvertimeView(OvertimeApiService overtimeApiService) {
        this.overtimeApiService = overtimeApiService;

        setMargin(true);
        setSpacing(false);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setAlignItems(Alignment.CENTER);

        binder = new Binder<>(Overtime.class);

        overtimeDate = new DatePicker("Data nadgodzin");
        overtimeDate.setMaxWidth("200px");
        status = new ComboBox<>("Rodzaj");
        status.setItems("Nadgodziny", "Zlecenie");
        status.setPlaceholder("Wybierz rodzaj");
        status.setMaxWidth("200px");
        hours = new NumberField("Ilość godzin");
        hours.setMaxWidth("200px");
        saveButton = new Button("Zapisz");
        saveButton.setMaxWidth("200px");
        backButton = new Button("Powrót");
        backButton.setMaxWidth("200px");

        backButton.addClickListener(event -> {
            UI.getCurrent().navigate(MainView.class);
        });
        binder.forField(overtimeDate)
                .bind(Overtime::getOvertimeDate, Overtime::setOvertimeDate);


        FlexLayout flexLayout = new FlexLayout();
        flexLayout.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        flexLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        flexLayout.add(overtimeDate, status, hours, saveButton, backButton);

        FormLayout formLayout = new FormLayout(flexLayout);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        add(formLayout);

        binder.bindInstanceFields(this);


        saveButton.addClickListener(event -> save());
    }

    private void save() {
        try{
            Overtime newOvertime = new Overtime(
                    overtimeDate.getValue(),
                    status.getValue(),
                    hours.getValue().intValue()
            );
            if (!overtimeDate.isEmpty()&& !status.isEmpty() && !hours.isEmpty()) {
                overtimeApiService.addOvertime(newOvertime);
                Notification.show("Nadgodziny zostały zapisane", 3000, Position.MIDDLE);
            }
        }catch (NullPointerException e){
            Notification.show("Proszę wypełnić wszystkie pola poprawnie", 3000, Position.MIDDLE);
        }
    }

}
