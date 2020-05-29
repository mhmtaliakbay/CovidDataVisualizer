package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.net.MalformedURLException;


public class Controller implements Initializable {
    ObservableList  selectedItems;
    List<TableData> listTableData;
    //Line Chart
    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private LineChart<String, Integer> lineChart;

    //TableView
    @FXML
    private TextField txtUrl;

    @FXML
    private TableView myTable;

    //ListView
    @FXML
    private ListView<String> listView;


    @FXML
    private void onGetCountryClicked(){

        LineChart();
    }

    @FXML
    private void onGetUrlClicked() throws Exception {
        TableView(new URL(txtUrl.getText()));
        LineChart();
    }


    private void LineChart() {
        selectedItems = listView.getSelectionModel().getSelectedItems();
//        for(Object o : selectedItems){
//            System.out.println("o = " + o + " (" + o.getClass() + ")");
//        }

        lineChart.getData().clear();
        XYChart.Series<String,Integer> series;
        for(Object o : selectedItems){

            series = new XYChart.Series<String,Integer>();
            series.getData().add(new XYChart.Data<String,Integer>(o.toString(),
                    100));
            series.setName(o.toString());
            lineChart.getData().addAll(series);
        }




    }



    private void TableView(URL url) {
        myTable.getItems().clear();
        myTable.getColumns().clear();

         listTableData = null;
        try {
            DataAnalyzer dataAnalyzer = new DataAnalyzer(url);
            listTableData = dataAnalyzer.countryTableData();


        } catch (Exception e) {
            e.printStackTrace();
        }
        ObservableList<TableData> olistTableData = FXCollections.observableList(listTableData);
        TableColumn country = new TableColumn("Country");
        TableColumn totalCases = new TableColumn("Total Cases");
        TableColumn newCases = new TableColumn("New Cases");
        TableColumn totalDeath = new TableColumn("Total Death");
        TableColumn newDeath = new TableColumn("New Death");
        TableColumn population = new TableColumn("Population");
        TableColumn mortality = new TableColumn("Mortality");
        TableColumn attackRate = new TableColumn("Attack Rate");
        myTable.getColumns().addAll(country, totalCases, newCases, totalDeath, newDeath, population, mortality, attackRate);

        country.setCellValueFactory(new PropertyValueFactory<TableData, String>("country"));
        totalCases.setCellValueFactory(new PropertyValueFactory<TableData, Integer>("totalCases"));
        newCases.setCellValueFactory(new PropertyValueFactory<TableData, Integer>("newCase"));
        totalDeath.setCellValueFactory(new PropertyValueFactory<TableData, Integer>("totalDeath"));
        newDeath.setCellValueFactory(new PropertyValueFactory<TableData, Integer>("newDeath"));
        population.setCellValueFactory(new PropertyValueFactory<TableData, Integer>("population"));
        mortality.setCellValueFactory(new PropertyValueFactory<TableData, Double>("mortality"));
        attackRate.setCellValueFactory(new PropertyValueFactory<TableData, Double>("attackRate"));

        myTable.setItems(olistTableData);

        //ListView
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ObservableList<String> data = FXCollections.observableArrayList();
        for (TableData list:listTableData) {
            data.add(list.getCountry());
        }

        listView.setItems(data);



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

}