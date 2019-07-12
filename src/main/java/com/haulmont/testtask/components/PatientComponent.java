package com.haulmont.testtask.components;


import com.haulmont.testtask.data.services.PatientService;
import com.haulmont.testtask.entities.Patient;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;

public class PatientComponent extends Composite implements View {

    private Grid<Patient> patientGrid = new Grid<>(Patient.class);
    private Window window = new Window("addEditWindow");
    Button add = new Button("", VaadinIcons.PLUS);
    Button delete = new Button("", VaadinIcons.TRASH);
    Button edit = new Button("", VaadinIcons.EDIT);
    PatientForm patientForm = new PatientForm();
    ListDataProvider<Patient> provider = DataProvider.ofCollection(PatientService.getPatients());

    public PatientComponent(){

        HorizontalLayout buttonLayout = new HorizontalLayout(add, delete, edit);

        VerticalLayout mainContent = new VerticalLayout(buttonLayout, patientGrid);
        mainContent.setComponentAlignment(buttonLayout, Alignment.MIDDLE_RIGHT);

        VerticalLayout subContent = new VerticalLayout(patientForm);
        subContent.setWidth("350px");
        window.setContent(subContent);

        window.addCloseListener(closeEvent -> patientForm.unbindPatient());

        initButtonListeners();

        mainContent.setSizeFull();
        patientGrid.setSizeFull();

        patientGrid.setDataProvider(provider);
//        patientGrid.setItems(PatientService.getPatients());
        setCompositionRoot(mainContent);

    }

    private void initButtonListeners(){
        add.addClickListener(event -> {
            patientForm.setPatientToForm(new Patient());
            window.center();
            this.getUI().getUI().addWindow(window);
        });

        edit.addClickListener(event -> {
            Patient patient = patientGrid.asSingleSelect().getValue();
            if(patient != null){
                patientForm.setPatientToForm(patient);
                window.center();
                this.getUI().getUI().addWindow(window);
            }
        });

        delete.addClickListener(event -> {
            Patient patient = patientGrid.asSingleSelect().getValue();
            if(patient != null){
                try {
                    PatientService.deletePatient(patient);
                    provider.refreshAll();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

}
