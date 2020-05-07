package model;

public class Course extends Table {
    @Entity(type = "INTEGER", size = 32, primary = true)
    int id;

    @Entity(type="VARCHAR", size=50, isnull = false)
    String firstname;

    @ForeignKey(table = "Teacher", attribute = "id")
    @Entity(type = "INTEGER", size = 32)
    int teacherFk;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Teacher getTeacher() throws Exception {
        return (Teacher)Table.get(Teacher.class, teacherFk);
    }

    public void setTeacherFk(int teacherFk) {
        this.teacherFk = teacherFk;
    }
}
