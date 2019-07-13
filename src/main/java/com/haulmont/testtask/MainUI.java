package com.haulmont.testtask;

import com.haulmont.testtask.components.DoctorComponent;
import com.haulmont.testtask.components.PatientComponent;
import com.haulmont.testtask.components.PrescriptionComponent;
import com.haulmont.testtask.data.HibernateUtil;
import com.haulmont.testtask.data.services.DoctorService;
import com.haulmont.testtask.data.services.PatientService;
import com.haulmont.testtask.data.services.PrescriptionService;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.entities.Prescription;
import com.haulmont.testtask.entities.Priority;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.PushStateNavigation;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.navigator.Navigator;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.vaadin.inputmask.InputMask;
import org.vaadin.inputmask.client.Alias;
import org.vaadin.inputmask.client.Casing;
import org.vaadin.inputmask.client.Definition;
import org.vaadin.inputmask.client.PreValidator;


import java.sql.*;
import java.util.List;

import static com.sun.javafx.fxml.expression.Expression.add;

@PushStateNavigation
@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

//
//    private Grid<Customer> grid = new Grid<>(Customer.class);
//    private TextField filterText = new TextField();
//    private CustomerForm form = new CustomerForm(this);

    @Override
    protected void init(VaadinRequest vaadinRequest) {

//        final VerticalLayout layout = new VerticalLayout();
//        layout.setMargin(false);
//        layout.setSpacing(false);
//        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
//
//        final HorizontalLayout container = new HorizontalLayout();
//
//        final FormLayout column1 = new FormLayout();
//        column1.setWidth(500, Unit.PIXELS);
//        final FormLayout column2 = new FormLayout();
//        column2.setWidth(500, Unit.PIXELS);
//
//        container.addComponents(column1, column2);
//        layout.addComponent(container);
//
//        /*
//         * Date
//         */
//        DateField dateField = new DateField("Date:");
//        dateField.setDateFormat("MM/dd/yyyy");
//        InputMask.addTo(dateField, "99/99/9999");
//        column1.addComponent(dateField);
//
//        /*
//         * Time
//         */
//        TextField timeTextField = new TextField("Time:");
//        InputMask.addTo(timeTextField, "99:99:99");
//        column1.addComponent(timeTextField);
//
//        /*
//         * Placeholder
//         */
//        TextField examplePlaceholderTextField = new TextField("Placeholder:");
//        InputMask examplePlaceholderInputMask = new InputMask("99:99:99");
//        examplePlaceholderInputMask.setPlaceholder("*");
//        examplePlaceholderInputMask.extend(examplePlaceholderTextField);
//        column1.addComponent(examplePlaceholderTextField);
//
//        /*
//         * Regex mask
//         */
//        TextField regexMaskTextField = new TextField("Regex mask:");
//        InputMask regexInputMask = new InputMask("\\d*");
//        regexInputMask.setRegexMask(true);
//        regexInputMask.extend(regexMaskTextField);
//        column1.addComponent(regexMaskTextField);
//
//        /*
//         * Mask repeat with greedy false
//         */
//        TextField greedyTextField = new TextField("Greedy(false):");
//        InputMask greedyInputMask = new InputMask("9");
//        greedyInputMask.setRepeat(10);
//        greedyInputMask.setGreedy(false);
//        greedyInputMask.extend(greedyTextField);
//        column1.addComponent(greedyTextField);
//
//        /*
//         * Auto unmask
//         */
//        TextField autoUnmaskTextField = new TextField("Autounmask(true):");
//        InputMask autoUnmaskInputMask = new InputMask("(999) 999-9999");
//        autoUnmaskInputMask.setAutoUnmask(true);
//        autoUnmaskInputMask.extend(autoUnmaskTextField);
//        column1.addComponent(autoUnmaskTextField);
//
//        Button getValueButton = new Button("Get Unmasked Value");
//        getValueButton.addClickListener(event -> {
//            Notification n = new Notification("Unmasked value: " + autoUnmaskTextField.getValue());
//            n.setDelayMsec(3000);
//            n.show(getPage());
//        });
//        column1.addComponent(getValueButton);
//
//        /*
//         * Clear mask on lost focus
//         */
//        TextField clearOnLostTextField = new TextField("Clear on lost focus(false):");
//        InputMask clearOnLostInputMask = new InputMask("99:99:99");
//        clearOnLostInputMask.setClearMaskOnLostFocus(false);
//        clearOnLostInputMask.extend(clearOnLostTextField);
//        column1.addComponent(clearOnLostTextField);
//
//        /*
//         * Insert mode false
//         */
//        TextField insertModeTextField = new TextField("Insert mode(false):");
//        InputMask insertModeInputMask = new InputMask("99:99:99");
//        insertModeInputMask.setInsertMode(false);
//        insertModeInputMask.extend(insertModeTextField);
//        column1.addComponent(insertModeTextField);
//
//        /*
//         * clear on incomplete
//         */
//        TextField clearIncompleteTextField = new TextField("Clear incomplete(true):");
//        InputMask clearIncompleteInputMask = new InputMask("99:99:99");
//        clearIncompleteInputMask.setClearIncomplete(true);
//        clearIncompleteInputMask.extend(clearIncompleteTextField);
//        column1.addComponent(clearIncompleteTextField);
//
//        /*
//         * Currency Alias
//         */
//        TextField currencyTextField = new TextField("Currency:");
//        InputMask currencyInputMask = new InputMask(Alias.CURRENCY);
//        currencyInputMask.setPrefix("R$ ");
//        currencyInputMask.setGroupSeparator(".");
//        currencyInputMask.setRadixPoint(",");
//        currencyInputMask.extend(currencyTextField);
//        column2.addComponent(currencyTextField);
//
//        /*
//         * Date Extension
//         */
//        TextField dateTextField = new TextField("Date extension:");
//        InputMask dateInputMask = new InputMask(Alias.DATE);
//        dateInputMask.setPlaceholder("__/__/____");
//        dateInputMask.extend(dateTextField);
//        column2.addComponent(dateTextField);
//
//        /*
//         * Mask Definitions
//         */
//        TextField maskDefinitionTextField = new TextField("Mask Definition(basic Year):");
//        InputMask definitionInputMask = new InputMask("Y");
//        Definition yearDefinition = new Definition("Y", "(19|20)\\d{2}");
//        yearDefinition.setCardinality(4);
//        yearDefinition.addPreValidator(new PreValidator("[12]", 1));
//        yearDefinition.addPreValidator(new PreValidator("(19|20)", 2));
//        yearDefinition.addPreValidator(new PreValidator("(19|20)\\d", 3));
//        definitionInputMask.addDefinition(yearDefinition);
//        definitionInputMask.extend(maskDefinitionTextField);
//        column2.addComponent(maskDefinitionTextField);
//
//        /*
//         * Numeric input
//         */
//        TextField numericInputTextField = new TextField("Numeric Input:");
//        InputMask numericInputMask = new InputMask("€ 999.999.999,99");
//        numericInputMask.setNumericInput(true);
//        numericInputMask.extend(numericInputTextField);
//        column2.addComponent(numericInputTextField);
//
//        /*
//         * Upper Casing
//         */
//        TextArea upperCasingTextField = new TextArea("Upper Casing:");
//        InputMask upperCasingInputMask = new InputMask("*{*}");
//        upperCasingInputMask.setCasing(Casing.UPPER);
//        upperCasingInputMask.extend(upperCasingTextField);
//        column2.addComponent(upperCasingTextField);
//
//        /*
//         * Phone Number
//         */
//        TextField phoneNumberTextField = new TextField("Phone Number:");
//        InputMask.addTo(phoneNumberTextField, "(999) 999-9999");
//        column2.addComponent(phoneNumberTextField);
//        phoneNumberTextField.setValue("1234567890");
//
//        /*
//         * Ip Alias
//         */
//        TextField ipTextField = new TextField("IP Address:");
//        InputMask ipInputMask = new InputMask(Alias.IP);
//        ipInputMask.extend(ipTextField);
//        column2.addComponent(ipTextField);
//
//        /*
//         * Mask on Combobox
//         */
//        ComboBox<String> combo = new ComboBox<>("Mask on Combobox:");
//        combo.setItems("1-444", "1-667", "2-232", "4-433", "4-431", "1-424", "1-627", "2-332", "4-733", "2-437", "4-124", "2-127", "4-832", "1-933", "4-491");
//        InputMask comboInputMask = new InputMask("9-999");
//        comboInputMask.setJitMasking(true);
//        comboInputMask.extend(combo);
//        column2.addComponent(combo);
//
//        /*
//         * Callbacks
//         */
//        TextField callbacksTextField = new TextField("Callbacks:");
//        InputMask callbacksInputMask = new InputMask("(999) 999-9999");
//        callbacksInputMask.addOnCompleteListener(() -> {
//            Notification n = new Notification("Inputmask complete");
//            n.setDelayMsec(3000);
//            n.show(getPage());
//        });
//        callbacksInputMask.addOnIncompleteListener(() -> {
//            Notification n = new Notification("Inputmask incomplete");
//            n.setDelayMsec(3000);
//            n.show(getPage());
//        });
//        callbacksInputMask.extend(callbacksTextField);
//        column2.addComponent(callbacksTextField);
//        setContent(layout);


        HorizontalLayout mainLayout = new HorizontalLayout();
        TabSheet tabSheet = new TabSheet();
        tabSheet.addTab(new PatientComponent(), "patient");
        tabSheet.addTab(new DoctorComponent(), "doctor");
        tabSheet.addTab(new PrescriptionComponent(), "prescription");
        mainLayout.addComponent(tabSheet);
//        test();



//        Label title = new Label("Menu");
//        title.addStyleName(ValoTheme.MENU_TITLE);
//
//        Button view1 = new Button("View 1", e -> getNavigator().navigateTo("view1"));
//        view1.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);
//        Button view2 = new Button("View 2", e -> getNavigator().navigateTo("view2"));
//        view2.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);
//
//        CssLayout menu = new CssLayout(title, view1, view2);
//        menu.addStyleName(ValoTheme.MENU_ROOT);
//        menu.setWidth(null);
//
//
//        VerticalLayout viewContainer = new VerticalLayout();
//
//        HorizontalLayout mainLayout = new HorizontalLayout(menu, viewContainer);
////        VerticalLayout mainLayout = new VerticalLayout(viewContainer);
//        mainLayout.setExpandRatio(viewContainer, 1.0f);
////        viewContainer.setHeight("100%");
//        viewContainer.setWidth("100%");


        mainLayout.setSizeFull();
        setContent(mainLayout);



//        Navigator navigator = new Navigator(this, viewContainer);
//        navigator.addView("", DefaultView.class);
//        navigator.addView("view1", View1.class);
//        navigator.addView("view2", View2.class);
    }


    public void test() {
        Doctor doctor = new Doctor();
        doctor.setFirstName("asdasdas");
        doctor.setSecondName("qweqweqwe");
        DoctorService.addDoctor(doctor);
//        System.out.println(PatientService.getPatients());
//        Patient patient =  PatientService.getPatientById(2).get(0);
//        System.out.println(patient);
//        patient.setPhoneNumber(patient.getPhoneNumber() + "228");
//        PatientService.updatePatient(patient);
//        patient = PatientService.getPatientById(2).get(0);
//        System.out.println(patient);
//        System.out.println(PatientService.getPatientByFullName("IVANOV","IVAN","IVANOVICH"));
//        Prescription prescription = PrescriptionService.getPrescriptionById(0).get(0);
//        System.out.println(prescription);
//        Patient first = PatientService.getPatientById(0).get(0);
//        PrescriptionService.deletePrescription(prescription);
//        List<Prescription> prescriptionList = PrescriptionService.getPrescriptions();
//        System.out.println(prescriptionList.size());
//        PatientService.deletePatient(first);
//        prescription = PrescriptionService.getPrescriptionById(0).get(0);
//        System.out.println(prescription);
//        System.out.println(PrescriptionService.getPrescriptionById(0));

    }



    private void hibernate(){

    }


    private void printTable() {

    }

}