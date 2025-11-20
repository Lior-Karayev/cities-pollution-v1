package app;

import data.MySQLPollutionDAO;
import model.PollutionReportModel;

public class Main {
    public static void main(String[] args) {
        MySQLPollutionDAO sqlAction = new MySQLPollutionDAO();
        for(PollutionReportModel rep: sqlAction.getMaxPollutionCountry()){
            System.out.println(rep);
        }
    }
}
