package sirma_task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SirmaEmployeeMain {

    public static void main(String[] args) throws IOException {
        List<SirmaEmployee> sirmaEmployees = new ArrayList<SirmaEmployee>();
        File file = new File("D:\\Eclipse - Java\\workpace\\SirmaSolutions\\src\\sirma_task\\SirmaEmployees.txt");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
        while (true){
            SirmaEmployee sirmaEmployee = null;
            String line = bufferedReader.readLine();
            if (line == null){
                break;
            }
            String [] row=line.split(",");
            int empID = Integer.parseInt(row[0]);
            int projectID = Integer.parseInt(row[1]);
            String dateTOString = row[3];
            LocalDate dateTo = LocalDate.now();
            LocalDate dateFrom = LocalDate.now();
            if(!row[2].equals("NULL")) {
                dateFrom = LocalDate.parse(row[2]);
            }

            if(!row[3].equals("NULL")) {
                dateTo = LocalDate.parse(row[3]);
            }

            sirmaEmployee = new SirmaEmployee(empID, projectID, dateFrom, dateTo);
            sirmaEmployees.add(sirmaEmployee);
        }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        for(SirmaEmployee employee : sirmaEmployees) {
            System.out.println(employee);
        }

        List<SirmaEmployee> sirmaComparedEmployees = new ArrayList<SirmaEmployee>();
        for (int index = 0; index < sirmaEmployees.size(); index++) {
            int projectID=sirmaEmployees.get(index).getProjectID();
            int employeeID=sirmaEmployees.get(index).getEmpID();
            LocalDate dateFrom = sirmaEmployees.get(index).getDateFrom();
            LocalDate dateTo = sirmaEmployees.get(index).getDateTo();
            List<SirmaEmployee> currentSelections =
                    sirmaEmployees.stream()
                                  .filter((a) -> projectID == a.getProjectID() && employeeID!=a.getEmpID()
                                          && dateFrom.isBefore(a.getDateFrom()) && dateTo.isAfter(a.getDateFrom()))
                                  .collect(Collectors.toCollection(ArrayList::new));

            for (int j = 0; j < currentSelections.size(); j++) {
                long days = 0L;
                if (currentSelections.get(j).getDateTo().isBefore(dateTo)) {
                    days = ChronoUnit.DAYS.between(currentSelections.get(j).getDateFrom(),currentSelections.get(j).getDateTo());
                    System.out.printf("The longest period in days is %d", days);
                }else{
                    days =ChronoUnit.DAYS.between(currentSelections.get(j).getDateFrom(),currentSelections.get(index).getDateTo());
                    System.out.printf("The longest period in days is %d", days);
                }
            }
        }
    }
}
