package gui;

import com.sun.org.glassfish.gmbal.Description;
import gui.forms.NoteForm;
import gui.helpers.NotesHelper;
import gui.helpers.ProjectHelper;
import org.junit.*;
import gui.forms.LoginForm;
import gui.helpers.DriverHelper;
import gui.helpers.LoginHelper;

import static com.codeborne.selenide.WebDriverRunner.url;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by a.oreshnikova on 28.11.2017.
 */
public class SomeTest {

    private static DriverHelper driver;
    private static LoginHelper login;
    private static NotesHelper note;
    private static ProjectHelper project;

    @BeforeClass
    public static void start() {
        driver = new DriverHelper();
        login = new LoginHelper();
        note = new NotesHelper();
        project = new ProjectHelper();
    }

    @Before
    public void goToStartPage() {
        driver.init();
        driver.goToPage();
    }

    @Description("Тест 1: залониться под пользователем в SEMrush")
    @Test
    public void loginOnSite_Test() {
        login.login(new LoginForm("piter_ao12@mail.ru", "1560256967Hi"));
        assertThat(login.isAuthSuccess()).as("Возникла ошибка при авторизации!").isTrue();
        assertThat(url()).endsWith("/dashboard/");
    }

    @Description("Тест 2: создайте новую заметку на /users/notes")
    @Test
    public void createNote_Test() {
        NoteForm newNote = new NoteForm("Заметка", "Текст заметки");
        login.login(new LoginForm("piter_ao12@mail.ru", "1560256967Hi"));
        assertThat(login.isAuthSuccess()).as("Возникла ошибка при авторизации!").isTrue();
        note.clickMenuNotes();
        assertThat(url()).endsWith("/notes/");
        note.newNote(newNote);
        assertThat(note.getNoteInfo()).isEqualToComparingFieldByField(newNote);
    }

    //Условие: У пользователя нет не домена, ни проектов
    @Description("Тест 3: создайте новый проект с /dashboard страницы")
    @Test
    public void createProject_Test() {
        login.login(new LoginForm("piter_ao12@mail.ru", "1560256967Hi"));
        assertThat(login.isAuthSuccess()).as("Возникла ошибка при авторизации!").isTrue();
        project.createProject("test", "newProject");
        assertThat(url()).startsWith("https://ru.semrush.com/projects/");
        assertThat(project.getTitle()).isEqualTo("Dashboard: newproject.test");
    }

    @After
    public void clear() {
        driver.clearCookies();
        driver.quit();
    }
}
