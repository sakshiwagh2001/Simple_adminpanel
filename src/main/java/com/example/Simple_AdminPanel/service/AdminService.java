package com.example.Simple_AdminPanel.service;

import com.example.Simple_AdminPanel.entities.Customer;
import com.example.Simple_AdminPanel.entities.Product;
import com.example.Simple_AdminPanel.entities.Purchase;
import com.example.Simple_AdminPanel.repository.CustomerRepository;
import com.example.Simple_AdminPanel.repository.ProductRepository;
import com.example.Simple_AdminPanel.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    public void  saveProduct(Product product){
        productRepository.save(product);
    }

    public List<Product> getAllProducts(){
        return  productRepository.findAll();
    }


    public Product getProductById(long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void deleteById(long id) {
        productRepository.deleteById(id);
    }


    public void saveCustomer(Customer customer){
             customerRepository.save(customer);
    }

    public List<Customer> getAllCustomer() {
        return  customerRepository.findAll();
    }


    public Optional<Customer> findCustomerByName(String name) {
        return customerRepository.findByName(name);
    }

    public Optional<Product> findProductByName(String name) {
        return productRepository.findByName(name);
    }

    public  void savePurchase(Purchase purchase){
        System.out.println("Saving Purchase: " + purchase);
        purchaseRepository.save(purchase);    }

    public List<Purchase> getAllPurchases(){
        List<Purchase> purchases = purchaseRepository.findAll();
        System.out.println("Retrieved Purchases: " + purchases);
        return purchases;    }


}
