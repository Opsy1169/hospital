package com.haulmont.testtask.components;


import com.haulmont.testtask.data.services.PrescriptionService;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.entities.Prescription;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;

public class PrescriptionComponent extends Composite implements View {

    private Grid<Prescription> prescriptionGrid = new Grid<>(Prescription.class);
    private Window subWindow = new Window("addEditWindow");
    private Button add = new Button("", VaadinIcons.PLUS);
    private Button delete = new Button("", VaadinIcons.TRASH);
    private Button edit = new Button("", VaadinIcons.EDIT);

    PrescriptionForm prescriptionForm = new PrescriptionForm();

    public PrescriptionComponent(){

        HorizontalLayout buttonLayout = new HorizontalLayout(add, delete, edit);

        VerticalLayout subContent = new VerticalLayout();
        subWindow.setContent(subContent);

        subContent.addComponent(prescriptionForm);
        subContent.setWidth("350px");
        subWindow.center();

        subWindow.addCloseListener(event -> prescriptionForm.unbindPrescription());

        initButtonListeners();


        VerticalLayout mainContent = new VerticalLayout(buttonLayout, prescriptionGrid);
        mainContent.setComponentAlignment(buttonLayout, Alignment.MIDDLE_RIGHT);
        ListDataProvider<Prescription> provider = DataProvider.ofCollection(PrescriptionService.getPrescriptions());
        prescriptionGrid.setDataProvider(provider);
        mainContent.setSizeFull();
        prescriptionGrid.setSizeFull();
        setCompositionRoot(mainContent);

    }

    private void initButtonListeners(){
        add.addClickListener(buttonClickEvent -> {
            prescriptionForm.setPrescriptionToForm(new Prescription());
            subWindow.center();
            this.getUI().getUI().addWindow(subWindow);
        });
        edit.addClickListener(buttonClickEvent -> {
            Prescription prescription = prescriptionGrid.asSingleSelect().getValue();
            if( prescription != null) {
                prescriptionForm.setPrescriptionToForm(prescription);
                subWindow.center();
                this.getUI().getUI().addWindow(subWindow);
            }
        });
    }
}