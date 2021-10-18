package controller;

import model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileController implements Controller {

    public static final FileController fileController = new FileController();

    public static final String CELL_SEPARATOR = ",";
    public static final String ROW_SEPARATOR = System.getProperty("line.separator");

    private static final String USER_FILE = "data/user.csv";
    private static final String UNIT_FILE = "data/unit.csv";
    private static final String TEACHING_UNIT_FILE = "data/teaching_unit.csv";
    private static final String ENROLLED_UNIT_FILE = "data/enrolled_unit.csv";
    private static final String GRADE_FILE = "data/grade.csv";
    private static final String LECTURER_FILE = "data/lecturer.csv";
    private static final String STUDENT_FILE = "data/student.csv";
    private static final String COURSE_FILE = "data/course.csv";
    private static final String SEMESTER_FILE = "data/semester.csv";

    private FileController() {
        // private constructor for singleton
    }

    public static FileController getInstance() {
        return fileController;
    }

    public ResponseObject handle() {
        return null;
    }

    public void writeUserData(List<User> userList, boolean append) {
        String dataStream = "";
        if (!append) {
            dataStream = getUserHeaders() + ROW_SEPARATOR;
        }
        dataStream = dataStream + userList.stream()
                .map(User::getCsvRow)
                .collect(Collectors.joining(ROW_SEPARATOR));
        writeData(dataStream, USER_FILE, append);
    }

    public void writePersonData(List<Person> personList, boolean append, User.UserLevel personType) {
        String dataStream = "";
        String fileName = "";
        // check the person type to set the file name
        if (personType.equals(User.UserLevel.LECTURER)) {
            fileName = LECTURER_FILE;
        } else if (personType.equals(User.UserLevel.STUDENT)) {
            fileName = STUDENT_FILE;
        }

        // set headers if writing into a new file
        if (!append) {
            dataStream = getPersonHeaders() + ROW_SEPARATOR;
        }
        // join rows of data separated by new line character
        dataStream = dataStream + personList.stream()
                .map(Person::getCsvRow)
                .collect(Collectors.joining(ROW_SEPARATOR));
        // write data into the file
        writeData(dataStream, fileName, append);
    }

    public void writeUnitData(List<Unit> unitList, boolean append) {
        String dataStream = "";
        if (!append) {
            dataStream = getUnitHeaders() + ROW_SEPARATOR;
        }
        dataStream = dataStream + unitList.stream()
                        .map(Unit::getCsvRow)
                        .collect(Collectors.joining(ROW_SEPARATOR));
        System.out.println(dataStream);
        writeData(dataStream, UNIT_FILE, append);
    }

    public void writeTeachingUnitData(List<TeachingUnit> unitList, boolean append) {
        String dataStream = "";
        if (!append) {
            dataStream = getTeachingUnitHeaders() + ROW_SEPARATOR;
        }
        dataStream = dataStream + unitList.stream()
                .map(TeachingUnit::getCsvRow)
                .collect(Collectors.joining(ROW_SEPARATOR));
        writeData(dataStream, TEACHING_UNIT_FILE, append);
    }

    public void writeEnrolledUnitData(List<EnrolledUnit> unitList, boolean append) {
        String dataStream = "";
        if (!append) {
            dataStream = getEnrolledUnitHeaders() + ROW_SEPARATOR;
        }
        dataStream = dataStream + unitList.stream()
                .map(EnrolledUnit::getCsvRow)
                .collect(Collectors.joining(ROW_SEPARATOR));
        writeData(dataStream, ENROLLED_UNIT_FILE, append);
    }

    private void writeData(String dataStream, String fileName, boolean append) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(fileName), append));
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

    public List<User> readUserData() {
        List<User> userList = new ArrayList<>();
        List<String[]> itemList = readData(USER_FILE);
        for (String[] items: itemList) {
            userList.add(new User(items[0], items[1], items[2], User.UserLevel.valueOf(items[3])));
        }
        return userList;
    }

    public List<Person> readPersonData(User.UserLevel personType) {
        List<Person> personList = new ArrayList<>();

        // read data and create objects according to user type
        String fileName = "";
        if(personType.equals(User.UserLevel.LECTURER)) {
            List<String[]> itemList = readData(LECTURER_FILE);
            for (String[] items: itemList) {
                personList.add(new Lecturer(items[0], items[1], items[2]));
            }
        } else if (personType.equals(User.UserLevel.STUDENT)) {
            List<String[]> itemList = readData(STUDENT_FILE);
            for (String[] items: itemList) {
                personList.add(new Lecturer(items[0], items[1], items[2]));
            }
        }
        return personList;
    }

    public List<Unit> readUnitData() {
        List<Unit> unitList = new ArrayList<>();
        List<String[]> itemList = readData(UNIT_FILE);
        for (String[] items: itemList) {
            unitList.add(new Unit(items[0], items[1]));
        }
        return unitList;
    }

    public List<TeachingUnit> readTeachingUnitData() {
        List<TeachingUnit> teachingUnits = new ArrayList<>();
        List<String[]> itemList = readData(TEACHING_UNIT_FILE);
        for (String[] items: itemList) {
            teachingUnits.add(new TeachingUnit(items[0], items[1], items[2], items[3]));
        }
        return teachingUnits;
    }

    public List<EnrolledUnit> readEnrolledUnitData() {
        List<EnrolledUnit> enrolledUnits = new ArrayList<>();
        List<String[]> itemList = readData(ENROLLED_UNIT_FILE);
        for (String[] items: itemList) {
            // set grade to null in case it is empty in the file
            // grade is empty when the student has not yet completed the module
            Grade grade = null;
            if (!items[5].equals("")) {
                grade = Grade.valueOf(items[5]);
            }
            enrolledUnits.add(new EnrolledUnit(items[0], items[1], items[2], items[3],
                    Double.parseDouble(items[4]), grade));
        }
        return enrolledUnits;
    }

    private List<String[]> readData(String fileName) {
        List<String[]> itemList = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileName)));
            String currentLine;
            boolean isHeaderRow = true;
            while((currentLine=bufferedReader.readLine())!=null) {
                String[] items = currentLine.split(CELL_SEPARATOR);
                if (isHeaderRow) {
                    // skip the first line with headers
                    isHeaderRow = false;
                } else {
                    itemList.add(items);
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            //TODO: handle error
            System.out.println("Error in reading file");
            e.printStackTrace();
        }
        return itemList;
    }

    private String getUserHeaders() {
        return "User Id" + FileController.CELL_SEPARATOR +
                "Username" + FileController.CELL_SEPARATOR +
                "Password" + FileController.CELL_SEPARATOR +
                "User Level";
    }

    private String getPersonHeaders() {
        return "Id" + FileController.CELL_SEPARATOR +
                "First name" + FileController.CELL_SEPARATOR +
                "Last name";
    }

    private String getUnitHeaders() {
        return "Unit Id" + FileController.CELL_SEPARATOR +
                "Unit Name";
    }

    private String getTeachingUnitHeaders() {
        return "Unit Id" + FileController.CELL_SEPARATOR +
                "Course Id" + FileController.CELL_SEPARATOR +
                "Semester Id" + FileController.CELL_SEPARATOR +
                "Lecturer Id";
    }

    private String getEnrolledUnitHeaders() {
        return "Unit Id" + FileController.CELL_SEPARATOR +
                "Student Id" + FileController.CELL_SEPARATOR +
                "Course Id" + FileController.CELL_SEPARATOR +
                "Semester Id" + FileController.CELL_SEPARATOR +
                "Mark" + FileController.CELL_SEPARATOR +
                "Grade";
    }
}
