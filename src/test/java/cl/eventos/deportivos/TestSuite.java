package cl.eventos.deportivos;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    UsuarioControllerTest.class,
    LoginControllerTest.class
})
public class TestSuite {

}
