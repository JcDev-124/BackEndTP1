import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.UserRepository;
import com.educandoweb.course.services.UserService;
import com.educandoweb.course.services.exceptions.DataBaseException;
import com.educandoweb.course.services.exceptions.ResourceNotFoundException;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDeleteTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @Test
    @DisplayName("Deve deletar usuário com sucesso")
    public void deletaUsuarioComSucesso() {
        User user = new User(1L, "teste", "teste", "teste", "teste");

        when(repository.findById(user.getId())).thenReturn(Optional.of(user));

        service.delete(user.getId());

        verify(repository).deleteById(user.getId());
        assertTrue(service.delete(user.getId()));
    }
    @Test
    @DisplayName("Não deve deletar usuário por usuário não encontrado")
    public void naoDeletaUsuarioNaoEncontrado() {
        Long userId = 1L;

        when(repository.findById(userId)).thenReturn(null);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> service.delete(userId));

        assertThat(exception.getMessage()).isEqualTo("Resource not found. Id" + userId);

    }

    @Test
    @DisplayName("Não deve deletar usuário por erro de violação de integridade")
    public void naoDeletaUsuarioPorViolacaoDeIntegridade() {
        Long userId = 1L;

        // Configurando o comportamento do mock para simular uma exceção de violação de integridade
        doThrow(DataIntegrityViolationException.class).when(repository).deleteById(userId);

        // Ação e verificação
        DataBaseException exception = assertThrows(DataBaseException.class, () -> service.delete(userId));

        // Verificação da mensagem da exceção
        assertThat(exception.getMessage()).isEqualTo("Erro de violação de integridade ao excluir usuário");
    }

    @Test
    @DisplayName("Não deve deletar usuário por erro de ID nulo")
    public void naoDeletaUsuarioPorIdNulo() {
        // Ação e verificação
        assertThrows(ResourceNotFoundException.class, () -> service.delete(null));
    }
}
