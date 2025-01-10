package setup;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.testng.annotations.DataProvider;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataSet {
    @DataProvider(name="CSVdata")
    public Object[][] getCSVdata() throws IOException {
        String filepath="./src/test/resources/items.csv";
        CSVParser parser=new CSVParser(new FileReader(filepath), CSVFormat.DEFAULT.withFirstRecordAsHeader());

        List<Object> data=new ArrayList<>();
        for(CSVRecord record:parser){
            String name= record.get("name");
            int n= Integer.parseInt(record.get("n"));
            String tk=record.get("tk");
            String date=record.get("date");
            String monthVal=record.get("monthVal");
            String mark=record.get("mark");


            data.add(new Object[]{name,n,tk,date,monthVal,mark});
        }
        return data.toArray(new Object[0][]);
    }
}
