package controller;

import entity.Lecturer;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.LecturerModel;

@ManagedBean(name = "LecturerBean")
@SessionScoped
public class LecturerBean implements Serializable {
    private Lecturer lecturer;
    private final LecturerModel model = new LecturerModel();
    private int studentID;
    private int lessonID;
    private int midtermNote;
    private int finalNote;
    private String student_name = "", student_lesson_ids = "", student_lesson_names = "";
    
    public LecturerBean() {
        this.lecturer = (Lecturer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
    }
    
    public void setUserSession() {
        this.lecturer = (Lecturer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public Lecturer getLecturerInfo() {
        return this.model.getLecturerByID(this.lecturer.getUserId());
    }
    
    public List<List<String>> getStudents() {
        return this.model.getStudents(this.lecturer.getUserId());
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudent(List<String> student) {
        this.studentID = Integer.parseInt(student.get(0));
        this.student_name = student.get(1);
        this.student_lesson_ids = student.get(4);
        this.student_lesson_names = student.get(5);
        
        this.lessonID = Integer.parseInt(Arrays.asList(student_lesson_ids.split(",")).get(0));
        this.catchLessonID();
    }

    public int getLessonID() {
        return lessonID;
    }

    public void setLessonID(int lessonID) {
        this.lessonID = lessonID;
    }

    public int getMidtermNote() {
        return midtermNote;
    }

    public void setMidtermNote(int midtermNote) {
        this.midtermNote = midtermNote;
    }

    public int getFinalNote() {
        return finalNote;
    }

    public void setFinalNote(int finalNote) {
        this.finalNote = finalNote;
    }

    public Map<String, String> getStudent_lessons() {
        Map<String, String> map = new HashMap<>();
        
        if(student_lesson_ids.length() != 0) {
            List<String> ids = Arrays.asList(student_lesson_ids.split(","));
            List<String> names = Arrays.asList(student_lesson_names.split(","));
            for(int i = 0; i < ids.size(); i++) map.put(ids.get(i), names.get(i));
        }
        
        return map;
    }

    public String getStudent_name() {
        return student_name;
    }
    
    public void catchLessonID() {
        List<Integer> notes = this.model.getLessonNotes(studentID, lessonID);
        this.midtermNote = notes.get(0);
        this.finalNote = notes.get(1);
    }
    
    public void saveStudentNote() {
        boolean response = this.model.saveStudentNote(this.studentID, this.lessonID, this.midtermNote, this.finalNote);
        
        if(response) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash()
                    .put("success", "Öğrenci notu güncellendi!");
        }
        else {
            FacesContext.getCurrentInstance().getExternalContext().getFlash()
                    .put("error", "Bir hata ile karşılaşıldı!");
        }
    }
    
    public List<List<String>> getLessons() {
        return this.model.getLessons(this.lecturer.getUserId());
    }
}
