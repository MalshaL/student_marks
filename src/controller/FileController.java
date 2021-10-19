package controller;

import model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileController implements Controller {
    // create a single instance for the class
    public static final FileController fileController = new FileController();

    // constant values for delimiters for writing csv data
    public static final String CELL_SEPARATOR = ",";
    public static final String ROW_SEPARATOR = System.getProperty("line.separator");

    // file names where data is stored
    private static final String USER_FILE = "data/user.csv";
    private static final String UNIT_FILE = "data/unit.csv";
    private static final String TEACHING_UNIT_FILE = "data/teaching_unit.csv";
    private static final String ENROLLED_UNIT_FILE = "data/enrolled_unit.csv";
    private static final String LECTURER_FILE = "data/lecturer.csv";
    private static final String STUDENT_FILE = "data/student.csv";
    private static final String COURSE_FILE = "data/course.csv";
    private static final String SEMESTER_FILE = "data/semester.csv";

    private FileController() {
        // private constructor for singleton
    }

    // return the single instance of the class (Singleton)
    public static FileController getInstance() {
        return fileController;
    }

    public ResponseObject handle() {
        return null;
    }

    public void writeUserData(List<User> userList, boolean append) throws IOException {
        String dataStream = "";
        if (!append) {
            dataStream = getUserHeaders() + ROW_SEPARATOR;
        }
        dataStream = dataStream + userList.stream()
                .map(User::getCsvRow)
                .collect(Collectors.joining(ROW_SEPARATOR));
        writeData(dataStream, USER_FILE, append);
    }

    public void writePersonData(List<Person> personList, boolean append, User.UserLevel personType) throws IOException {
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

    public void writeUnitData(List<Unit> unitList, boolean append) throws IOException {
        String dataStream = "";
        if (!append) {
            dataStream = getUnitHeaders() + ROW_SEPARATOR;
        }
        dataStream = dataStream + unitList.stream()
                        .map(Unit::getCsvRow)
                        .collect(Collectors.joining(ROW_SEPARATOR));
        writeData(dataStream, UNIT_FILE, append);
    }

    public void writeTeachingUnitData(List<TeachingUnit> unitList, boolean append) throws IOException {
        String dataStream = "";
        if (!append) {
            dataStream = getTeachingUnitHeaders() + ROW_SEPARATOR;
        }
        dataStream = dataStream + unitList.stream()
                .map(TeachingUnit::getCsvRow)
                .collect(Collectors.joining(ROW_SEPARATOR));
        writeData(dataStream, TEACHING_UNIT_FILE, append);
    }

    public void writeEnrolledUnitData(List<EnrolledUnit> unitList, boolean append) throws IOException {
        String dataStream = "";
        if (!append) {
            dataStream = getEnrolledUnitHeaders() + ROW_SEPARATOR;
        }
        dataStream = dataStream + unitList.stream()
                .map(EnrolledUnit::getCsvRow)
                .collect(Collectors.joining(ROW_SEPARATOR));
        writeData(dataStream, ENROLLED_UNIT_FILE, append);
    }

    public void writeCourseData(List<Course> courseList, boolean append) throws IOException {
        String dataStream = "";
        if (!append) {
            dataStream = getCourseHeaders() + ROW_SEPARATOR;
        }
        dataStream = dataStream + courseList.stream()
                .map(Course::getCsvRow)
                .collect(Collectors.joining(ROW_SEPARATOR));
        writeData(dataStream, COURSE_FILE, append);
    }

    public void writeSemesterData(List<Semester> semesterList, boolean append) throws IOException {
        String dataStream = "";
        if (!append) {
            dataStream = getSemesterHeaders() + ROW_SEPARATOR;
        }
        dataStream = dataStream + semesterList.stream()
                .map(Semester::getCsvRow)
                .collect(Collectors.joining(ROW_SEPARATOR));
        writeData(dataStream, SEMESTER_FILE, append);
    }

    private void writeData(String dataStream, String fileName, boolean append) throws IOException {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(fileName), append));
            bufferedWriter.write(dataStream);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public List<User> readUserData() throws IOException {
        List<User> userList = new ArrayList<>();
        List<String[]> itemList = readData(USER_FILE);
        for (String[] items: itemList) {
            userList.add(new User(items[0], items[1], items[2], User.UserLevel.valueOf(items[3])));
        }
        return userList;
    }

    public List<Person> readPersonData(User.UserLevel personType) throws IOException {
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

    public List<Unit> readUnitData() throws IOException {
        List<Unit> unitList = new ArrayList<>();
        List<String[]> itemList = readData(UNIT_FILE);
        for (String[] items: itemList) {
            unitList.add(new Unit(items[0], items[1], Integer.parseInt(items[2])));
        }
        return unitList;
    }

    public List<TeachingUnit> readTeachingUnitData() throws IOException {
        List<TeachingUnit> teachingUnits = new ArrayList<>();
        List<String[]> itemList = readData(TEACHING_UNIT_FILE);
        for (String[] items: itemList) {
            teachingUnits.add(new TeachingUnit(items[0], items[1], items[2], items[3]));
        }
        return teachingUnits;
    }

    public List<EnrolledUnit> readEnrolledUnitData() throws IOException {
        List<EnrolledUnit> enrolledUnits = new ArrayList<>();
        List<String[]> itemList = readData(ENROLLED_UNIT_FILE);
        for (String[] items: itemList) {
            enrolledUnits.add(new EnrolledUnit(items[0], items[1], items[2], items[3],
                    Double.parseDouble(items[4]), Grade.valueOf(items[5])));
        }
        return enrolledUnits;
    }

    public List<Course> readCourseData() throws IOException {
        List<Course> courseList = new ArrayList<>();
        List<String[]> itemList = readData(COURSE_FILE);
        for (String[] items: itemList) {
            courseList.add(new Course(items[0], items[1]));
        }
        return courseList;
    }

    public List<Semester> readSemesterData() throws IOException {
        List<Semester> semesterList = new ArrayList<>();
        List<String[]> itemList = readData(SEMESTER_FILE);
        for (String[] items: itemList) {
            semesterList.add(new Semester(items[0], items[1], Boolean.parseBoolean(items[2])));
        }
        return semesterList;
    }

    private List<String[]> readData(String fileName) throws IOException {
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
            throw new IOException();
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
                "Unit Name" + FileController.CELL_SEPARATOR +
                "Credits";
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

    private String getCourseHeaders() {
        return "Course Id" + FileController.CELL_SEPARATOR +
                "Course Name";
    }

    private String getSemesterHeaders() {
        return "Semester Id" + FileController.CELL_SEPARATOR +
                "Semester Name" + FileController.CELL_SEPARATOR +
                "Current";
    }
}
