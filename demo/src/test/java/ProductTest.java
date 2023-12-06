import com.educandoweb.course.entities.Product;
import com.educandoweb.course.repositories.ProductRepository;
import com.educandoweb.course.services.ProductService;
import com.educandoweb.course.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("deve retornar produto com sucesso")
    public void retornaProduto(){

        //Arrange
        Product produto = new Product(1L, "Teste", "Teste", 200.0, "teste");
        when(repository.findById(produto.getId())).thenReturn(Optional.of(produto));

        //Action
        Product produtoRetornado = productService.findById(produto.getId());

        //Assertions
        verify(repository).findById(produto.getId());
        assertEquals(produto, produtoRetornado);
    }

    @Test
    @DisplayName("nao deve retornar produto por erro de not found")
    public void naoRetornaProduto(){
        //Arrange
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        //Action
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> productService.findById(id));

        //Assertions
        assertThat(exception.getMessage()).isEqualTo("Resource not found. Id" + id);

    }



}
