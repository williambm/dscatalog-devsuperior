package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository productRepository;

    private long existingId;
    private long nomExistingId;

    @BeforeEach
    void setUp() throws Exception{
        existingId = 1L;
        nomExistingId = 1200L;

        Mockito.doNothing().when(productRepository).deleteById(existingId);

        Mockito.doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(nomExistingId);
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists(){
        Assertions.assertDoesNotThrow(()->{
            service.delete(existingId);
        });

        Mockito.verify(productRepository,Mockito.times(1)).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdNomExists(){
        Assertions.assertThrows(ResourceNotFoundException.class,()->{
            service.delete(nomExistingId);
        });

        Mockito.verify(productRepository,Mockito.times(1)).deleteById(nomExistingId);
    }
}
