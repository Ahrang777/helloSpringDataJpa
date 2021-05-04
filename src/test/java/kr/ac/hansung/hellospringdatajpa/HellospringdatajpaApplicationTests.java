package kr.ac.hansung.hellospringdatajpa;

import kr.ac.hansung.hellospringdatajpa.entity.Product;
import kr.ac.hansung.hellospringdatajpa.repository.ProductRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Sql({"/schema.sql", "/data.sql"}) // to create DB tables and init sample DB data
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class) // to run tests in order
class HellospringdatajpaApplicationTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Order(1)
    public void findProductById() {
        Optional<Product> product = productRepository.findById(1L);
        assertNotNull(product.get());
    }

    @Test
    @Order(2)
    public void createProduct() {
        Product product = new Product("OLED TV", "LG전자", "korea", 300.0);
        Product savedProduct = productRepository.save(product);

        Product newProduct = productRepository.findById(savedProduct.getId()).get();
        assertEquals("OLED TV", newProduct.getName());
        assertEquals("LG전자", newProduct.getBrand());
    }

    @Test
    @Order(3)
    public void findByName() {
        Product product = productRepository.findByName("Galaxy S21");
        assertEquals("Galaxy S21", product.getName());
    }

    @Test
    @Order(4)
    public void findAllProducts() {
        List<Product> products = productRepository.findAll();
        assertNotNull(products);
    }

    @Test
    @Order(5)
    public void testsearchByName() {
        List<Product> productList= productRepository.searchByName("Air");

        System.out.println(" ====testsearchByName: Air======");
        for (Product product : productList) {
            System.out.println("-->" + product.toString() );
        }
    }

    @Test
    @Order(6)
    public void testFindByNameContainingWithPaginAndSort() {
        Pageable paging = PageRequest.of(0,3, Sort.Direction.DESC, "id");
        Page<Product> pageInfo=
                productRepository.findByNameContaining("Galaxy", paging);

        System.out.println(" ====testFindByNameContainingWithPaginAndSort: Galaxy====");

        System.out.println("Page size: "    + pageInfo.getSize() );
        System.out.println("Total Pages: " + pageInfo.getTotalPages() );
        System.out.println("Total Count: " + pageInfo.getTotalElements() );

        List<Product> productList=pageInfo.getContent();

        for(Product product: productList) {
            System.out.println("-->" + product.toString() );
        }
    }

    /*@Test
    public void testFindByNameContaining() {

        Pageable paging = PageRequest.of(0, 3);
        List<Product> productList =
                productRepository.findByNameContaining("MacBook", paging);

        System.out.println("====testFindByNameContaining: Macbook=====");
        for (Product product : productList) {
            System.out.println("-->" + product.toString() );
        }

    }*/
    /*@Test
    public void testFindByNameContainingWithSort( ) {

        Pageable paging = PageRequest.of(0, 3, Sort.Direction.DESC, "id");
        List<Product> productList =
                productRepository.findByNameContaining("Galaxy", paging);

        System.out.println("===testFindByNameContainingWithSort: Galaxy====");
        for (Product product : productList) {
            System.out.println("-->" + product.toString() );
        }
    }*/


}