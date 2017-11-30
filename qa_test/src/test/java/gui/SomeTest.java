package gui;

import com.sun.org.glassfish.gmbal.Description;
import gui.forms.Note;
import gui.helpers.NotesHelper;
import gui.helpers.ProjectHelper;
import org.junit.*;
import gui.forms.User;
import gui.helpers.DriverHelper;
import gui.helpers.AuthHelper;

import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by a.oreshnikova on 28.11.2017.
 */

public class SomeTest {

    private static DriverHelper driver;
    private static AuthHelper auth;
    private User user = new User("piter_ao12@mail.ru", "1560256967Hi");

    @BeforeClass
    public static void start() {
        driver = new DriverHelper();
        auth = new AuthHelper();
        driver.init();
    }

    @Description("Тест 1: залониться под пользователем в SEMrush")
    @Test
    public void loginOnSite_Test() {
        driver.openStartPage();
        auth.auth(user);
        assertThat(auth.isAuthSuccess()).as("Возникла ошибка при авторизации!").isTrue();
        assertThat(url()).endsWith("/dashboard/");
    }

    @Description("Тест 2: создайте новую заметку на /users/notes")
    @Test
    public void createNote_Test() {
        NotesHelper note = new NotesHelper();
        Note newNote = new Note("Заметка", "Текст заметки");
        driver.openNotesPage();
        auth.auth(user);
        note.newNote(newNote);
        assertThat(note.getNoteInfo()).isEqualToComparingFieldByField(newNote);
    }

    //Условие: У пользователя нет не домена, ни проектов
    @Description("Тест 3: создайте новый проект с /dashboard страницы")
    @Test
    public void createProject_Test() {
        String domain = "test";
        String name = "project";
        ProjectHelper project = new ProjectHelper();
        driver.openDashboardPage();
        auth.auth(user);
        project.createProject(domain, name);
        assertThat(url()).startsWith("https://ru.semrush.com/projects/");
        assertThat(project.getTitle()).isEqualTo("Dashboard: newproject.test");
        project.deleteProject(name, domain);
    }

    @AfterClass
    public static void stop() {
        driver.quit();
    }
}
