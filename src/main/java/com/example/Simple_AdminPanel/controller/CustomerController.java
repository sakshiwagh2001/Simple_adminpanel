package com.example.Simple_AdminPanel.controller;

import com.example.Simple_AdminPanel.entities.Customer;
import com.example.Simple_AdminPanel.entities.Product;
import com.example.Simple_AdminPanel.entities.Purchase;
import com.example.Simple_AdminPanel.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class CustomerController {

    @Autowired
    private AdminService adminService;
    @GetMapping("/dashbord")
    public String index(Model model) {
        List<Purchase> purchases = adminService.getAllPurchases();
        System.out.println("Passing Purchases to Model: " + purchases);
        model.addAttribute("purchases", purchases);
        return "dashbord"; // This should match the name of your Thymeleaf template file (without .html)
    }


    @GetMapping("/addProduct")
    public  String addProduct()
    {
        return  "addProduct";
    }


    @PostMapping("/addProduct")
    public String saveProduct(Product product){
        adminService.saveProduct(product);
        return "redirect:/viewProduct";
    }

    @GetMapping("/viewProduct")
    public String viewProduct(Model model) {
        List<Product> products = adminService.getAllProducts();
        model.addAttribute("products", products);
        return "viewProduct"; // This should match the name of your Thymeleaf view template
    }

    @GetMapping("/editProduct/{id}")
    public String showEditProductForm(@PathVariable("id") long id, Model model) {
        Product product = adminService.getProductById(id);
        model.addAttribute("product", product);
        return "editProduct";
    }

    @PostMapping("/editProduct")
    public String editProduct(Product product, RedirectAttributes redirectAttributes) {
        adminService.saveProduct(product);
        redirectAttributes.addFlashAttribute("success", "Product is successfully edited.");
        return "redirect:/viewProduct";
    }

    @RequestMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        adminService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Product is deleted.");
        return "redirect:/viewProduct";
    }



    @GetMapping("/addCustomer")
    public  String customer()
    {
        return  "addCustomer";
    }



    @PostMapping("/addCustomer")
    public String saveCustomer(Customer customer){
        adminService.saveCustomer(customer);
        return "redirect:/viewCustomer";
    }


    @GetMapping("/viewCustomer")
    public  String viewCustomer(Model model)
    {
        model.addAttribute("customers",adminService.getAllCustomer());
        return  "viewCustomer";
    }
    @GetMapping("/billing")
    public  String billing()
    {
        return  "billing";
    }

    @PostMapping("/billing")
    public String saveBilling(@RequestParam String customerName, @RequestParam String productName, @RequestParam Double price, RedirectAttributes redirectAttributes) {
        Optional<Customer> customerOpt = adminService.findCustomerByName(customerName);
        Optional<Product> productOpt = adminService.findProductByName(productName);

        System.out.println("Customer exists: " + customerOpt.isPresent());
        System.out.println("Product exists: " + productOpt.isPresent());

        if (!customerOpt.isPresent() && !productOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Customer and Product not available");
            return "redirect:/billing";
        } else if (!customerOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Customer not available");
            return "redirect:/billing";
        } else if (!productOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Product not available");
            return "redirect:/billing";
        }

        Purchase purchase = new Purchase();
        purchase.setCustomerName(customerName);
        purchase.setProductName(productName);
        purchase.setPrice(price);

        adminService.savePurchase(purchase);
        return "redirect:/dashbord";
    }
}
