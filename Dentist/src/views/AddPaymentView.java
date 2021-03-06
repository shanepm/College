package views;

import controllers.AddPaymentController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Shane on 20/03/2017.
 */
public class AddPaymentView extends View<AddPaymentController> {

    private InvoiceManagementView invoiceManagementView;
    private VBox root;
    private GridPane gridPane;
    private Label paymentDateLabel;
    private TextField paymentDateField;
    private Label paymentCostLabel;
    private TextField paymentCostField;
    private HBox addPaymentBtnHBox;
    private Button addPaymentBtn;

    public AddPaymentView(InvoiceManagementView invoiceManagementView) {
        this(new Stage(), invoiceManagementView);
    }

    public AddPaymentView(Stage view, InvoiceManagementView invoiceManagementView) {
        super(view);
        setInvoiceManagementView(invoiceManagementView);
        assignChildren();
        assignController(new AddPaymentController(this));
    }

    public InvoiceManagementView getInvoiceManagementView() {
        return invoiceManagementView;
    }

    public void setInvoiceManagementView(InvoiceManagementView invoiceManagementView) {
        this.invoiceManagementView = invoiceManagementView;
    }

    @Override
    public VBox getRoot() {
        return root;
    }

    public void setRoot(VBox root) {
        this.root = root;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public Label getPaymentDateLabel() {
        return paymentDateLabel;
    }

    public void setPaymentDateLabel(Label paymentDateLabel) {
        this.paymentDateLabel = paymentDateLabel;
    }

    public TextField getPaymentDateField() {
        return paymentDateField;
    }

    public void setPaymentDateField(TextField paymentDateField) {
        this.paymentDateField = paymentDateField;
    }

    public Label getPaymentCostLabel() {
        return paymentCostLabel;
    }

    public void setPaymentCostLabel(Label paymentCostLabel) {
        this.paymentCostLabel = paymentCostLabel;
    }

    public TextField getPaymentCostField() {
        return paymentCostField;
    }

    public void setPaymentCostField(TextField paymentCostField) {
        this.paymentCostField = paymentCostField;
    }

    public HBox getAddPaymentBtnHBox() {
        return addPaymentBtnHBox;
    }

    public void setAddPaymentBtnHBox(HBox addPaymentBtnHBox) {
        this.addPaymentBtnHBox = addPaymentBtnHBox;
    }

    public Button getAddPaymentBtn() {
        return addPaymentBtn;
    }

    public void setAddPaymentBtn(Button addPaymentBtn) {
        this.addPaymentBtn = addPaymentBtn;
    }

    @Override
    public void assignChildren() {
        setRoot(new VBox());
        getRoot().setPrefWidth(325);
        getRoot().setSpacing(10);
        getRoot().setPadding(new Insets(15));

        setGridPane(new GridPane());
        getGridPane().setHgap(15);
        getGridPane().setVgap(10);
        getRoot().getChildren().add(getGridPane());

        ColumnConstraints columnConstraint1 = new ColumnConstraints();
        ColumnConstraints columnConstraint2 = new ColumnConstraints();
        columnConstraint2.setHgrow(Priority.SOMETIMES);
        columnConstraint2.setPrefWidth(100);
        getGridPane().getColumnConstraints().addAll(columnConstraint1, columnConstraint2);

        RowConstraints rowConstraint = new RowConstraints();
        rowConstraint.setPrefHeight(30);
        rowConstraint.setVgrow(Priority.SOMETIMES);
        getGridPane().getRowConstraints().addAll(rowConstraint, rowConstraint);

        setPaymentDateLabel(new Label());
        getPaymentDateLabel().setText("Payment Date");
        getGridPane().add(getPaymentDateLabel(), 0, 0);

        setPaymentDateField(new TextField());
        getPaymentDateField().setPromptText("dd/MM/yyyy");
        getGridPane().add(getPaymentDateField(), 1, 0);

        setPaymentCostLabel(new Label());
        getPaymentCostLabel().setText("Cost");
        getGridPane().add(getPaymentCostLabel(), 0, 1);

        setPaymentCostField(new TextField());
        getPaymentCostField().setPromptText("Enter a decimal");
        getGridPane().add(getPaymentCostField(), 1, 1);

        setAddPaymentBtnHBox(new HBox());
        getAddPaymentBtnHBox().setAlignment(Pos.TOP_RIGHT);
        getRoot().getChildren().add(getAddPaymentBtnHBox());

        setAddPaymentBtn(new Button());
        getAddPaymentBtn().setText("Add Payment");
        getAddPaymentBtn().setDefaultButton(true);
        getAddPaymentBtn().setDisable(true);
        getAddPaymentBtn().setPrefHeight(31);
        getAddPaymentBtnHBox().getChildren().add(getAddPaymentBtn());

        getWindow().setTitle("Add Payment - Dentistry");
        getWindow().setScene(new Scene(root));
        getWindow().setResizable(false);
        getWindow().initModality(Modality.APPLICATION_MODAL);
        getWindow().sizeToScene();
    }
}
