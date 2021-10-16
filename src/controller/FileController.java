package controller;

import model.ResponseObject;
import model.Unit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class FileController implements Controller {

    public static final FileController fileController = new FileController();

    public static final String CELL_SEPARATOR = ",";
    public static final String ROW_SEPARATOR = System.getProperty("line.separator");

    private static final String UNIT_FILE = "data/unit.csv";

    private FileController() {
        // private constructor for singleton
    }

    public static FileController getInstance() {
        return fileController;
    }

    public ResponseObject handle() {
        return null;
    }

    public static void saveUnitData(List<Unit> unitList) {
        String dataStream = getUnitDataHeaders();
        dataStream = dataStream + ROW_SEPARATOR + unitList.stream()
                        .map(Unit::getCsvRow)
                        .collect(Collectors.joining(ROW_SEPARATOR));
        File csvFile = new File(UNIT_FILE);
        System.out.println(csvFile.getAbsolutePath());

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(csvFile));
            bufferedWriter.write(dataStream);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            //TODO: handle error
            System.out.println("Error in writing file");
            e.printStackTrace();
        }
    }

    private static String getUnitDataHeaders() {
        return "Unit Id" + FileController.CELL_SEPARATOR +
                "Unit Name";
    }
}
