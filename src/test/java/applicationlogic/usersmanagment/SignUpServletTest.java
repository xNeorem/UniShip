package applicationlogic.usersmanagment;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import storage.DatabaseManager;

class SignUpServletTest extends Mockito {

  private SignUpServlet servlet;
  private MockHttpServletRequest request;
  private MockHttpServletResponse response;

  @AfterAll
  static void cancellaUtente() throws IOException, SQLException {
    PreparedStatement preparedStatement = null;
    Connection connection = null;
    int rs;

    try {
      connection = DatabaseManager.getConnection();
      preparedStatement = connection.prepareStatement("DELETE FROM utente WHERE email = ?");
      preparedStatement.setString(1, "m.rossi@studenti.unisa.it");

      rs = preparedStatement.executeUpdate();

    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    }
  }

  @BeforeEach
  void setUp() throws IOException {
    servlet = new SignUpServlet();
    request = new MockHttpServletRequest();
    response = new MockHttpServletResponse();
  }

  @Test
  public void TC_3_01() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "");
    request.setParameter("password", "passsword");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Email too short\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_02() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email",
        "m.rossim.rossim.rossim.rossim.rossim.rossim.rossim.rossi@studenti.unisa.it");
    request.setParameter("password", "passsword");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Email too long\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_03() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "m.r_ossi@studenti.unisa.it");
    request.setParameter("password", "passsword");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Email not valid\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_04() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "g.gullo@studenti.unisa.it");
    request.setParameter("password", "passsword");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Email already register.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_05() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "passsw");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Password too short.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_06() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "p_assword");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Password invalid.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_07() throws ServletException, IOException {
    //TODO: this one should be verified in javascript
  }

  @Test
  public void TC_1_08() throws ServletException, IOException {
    //TODO: this one should be verified in javascript
  }

  @Test
  public void TC_1_09() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Nome too short.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }


  @Test
  public void TC_1_10() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "MarioMarioMarioMarioMarioMarioMarioMarioMario");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Nome too long.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_11() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mar1o");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Nome invalid.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_12() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Cognome too short.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_13() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "RossiRossiRossiRossiRossiRossiRossi");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Cognome too long.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_14() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "Ross1");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Cognome invalid.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_15() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("codiceFiscale", "MRORSS98A05H703CCC");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Codice Fiscale lenght invalid.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_16() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("codiceFiscale", "MRORSS98A057703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Codice Fiscale invalid.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_17() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "05120051205");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Matricola lenght invalid.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_18() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "o51200512o");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Matricola invalid.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_19() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "19988-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Data di nascita lenght invalid.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_20() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-13-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Data di nascita invalid.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_21() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Cittadinanza too short.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_22() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "1tal1a");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Cittadinanza invalid.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_23() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Residenza too short.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_24() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8/Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Residenza invalid.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_25() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "33358cinque8581");
    servlet.doPost(request, response);
    assertEquals("{\"description\":\"Numero invalid.\",\"status\":\"422\"}",
        response.getContentAsString().trim());
  }

  @Test
  public void TC_1_26() throws ServletException, IOException {
    request.setParameter("action", "signup");
    request.setParameter("nome", "Mario");
    request.setParameter("cognome", "Rossi");
    request.setParameter("email", "m.rossi@studenti.unisa.it");
    request.setParameter("password", "password");
    request.setParameter("codiceFiscale", "MRORSS98A05H703C");
    request.setParameter("matricola", "0512005120");
    request.setParameter("dataDiNascita", "1998-01-05");
    request.setParameter("cittadinanza", "Italia");
    request.setParameter("residenza", "Via Roma 8, Napoli");
    request.setParameter("numero", "3335858581");
    servlet.doPost(request, response);
    assertEquals("{\"redirect\":\"index.jsp\",\"status\":\"302\"}",
        response.getContentAsString().trim());
  }
}