import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.UserRepository;
import com.educandoweb.course.services.UserService;
import com.educandoweb.course.services.exceptions.DataBaseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.function.BooleanSupplier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserInsertTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @Test
    @DisplayName("Deve inserir usuario com sucesso")
    public void insereUsuario(){

        User user = new User(1L, "teste", "teste", "teste", "teste");
        when(repository.findById(user.getId())).thenReturn(null);

        service.insert(user);

        verify(repository).findById(user.getId());
        verify(repository).save(user);
        assertTrue((BooleanSupplier) service.insert(user));
    }

    @Test
    @DisplayName("Nao deve inserir usuario por erro de objeto nulo")
    public void naoInsereUsuario(){

        User user = null;

        DataBaseException exception = assertThrows(DataBaseException.class, () -> service.insert(user));

        assertThat(exception.getMessage()).isEqualTo("Objeto nulo");
    }

    @Test
    @DisplayName("Nao deve inserir por erro de cliente existente")
    public void naoInsereUsuarioExistente(){
        User user = new User(1L, "teste", "teste", "teste", "teste");
        when(repository.findById(1L)).thenReturn(Optional.of(user));

        DataBaseException exception = assertThrows(DataBaseException.class, () -> service.insert(user));

        assertThat(exception.getMessage()).isEqualTo("Usuario ja existe");


    }







}
