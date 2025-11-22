package app;

import data.MySQLPollutionDAO;
import model.PollutionReportModel;

public class Main {
    public static void main(String[] args) {
        System.out.println("========= STARTING APP =========");
        System.out.println("--- Countries with highest air and water pollution ---");
        MySQLPollutionDAO sqlAction = new MySQLPollutionDAO();
        for(PollutionReportModel rep: sqlAction.getMaxPollutionCountries()){
            System.out.println(rep);
        }

        System.out.println("\n--- Countries with lowest air and water pollution ---");
        for(PollutionReportModel rep: sqlAction.getMinPollutionCountries()){
            System.out.println(rep);
        }

        System.out.println("========= END OF THE APP =========");
    }
}
