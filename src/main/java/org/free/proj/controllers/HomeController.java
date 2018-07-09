package org.free.proj.controllers;

import org.free.proj.entity.Product;
import org.free.proj.entity.Shopping;
import org.free.proj.entity.User;
import org.free.proj.objects.Message;
import org.free.proj.service.ProductService;
import org.free.proj.service.ShoppingService;
import org.free.proj.service.UserService;
import org.free.proj.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;



@Controller
public class HomeController {



    @Autowired
    private StorageService storageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ShoppingService shoppingService;

    @RequestMapping("/")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getAuthorities().toString().indexOf("ADMIN")<= 0){
            String name = auth.getName();
            User user = userService.findByEmail(name);
            ArrayList<Shopping> shopping = shoppingService.findByUser(user);
            double total = round(shopping.stream().mapToDouble(e -> e.getProduct().getPrice()).sum(), 2);

            model.addAttribute("shopping", shopping);
            model.addAttribute("total", total);

            model.addAttribute("product", new Product());
            model.addAttribute("shop", new Shopping());

            return "index";

        }else{
            ArrayList<User> users = userService.All();
            model.addAttribute("users", users);
            return "index";
        }


    }

    @RequestMapping(value = "/info/{email}", method = RequestMethod.GET)
    public String info(@PathVariable String email, Model model) {
        User user = userService.findByEmail(email);
        if (user == null){
            return "redirect:/";
        }else{
            ArrayList<Shopping> shopping = shoppingService.findByUser(user);
            double total = round(shopping.stream().mapToDouble(e -> e.getProduct().getPrice()).sum(), 2);

            model.addAttribute("user", user);
            model.addAttribute("shopping", shopping);
            model.addAttribute("total", total);

            return "info/info";
        }

    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }


    @RequestMapping(value = "/delete-product/{id}", method = RequestMethod.GET)
    public String deleteProduct(@PathVariable int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userService.findByEmail(name);
        Product product = productService.findById(id);
        Shopping shopping = shoppingService.findByUserAndProduct(user,product);
        shoppingService.delete(shopping);
        return "redirect:/";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @RequestMapping(value = "/add-expense", method = RequestMethod.POST)
    public String addExpense(@RequestParam("file") MultipartFile file,@Valid Product product, @Valid Shopping shopping,
                             BindingResult bindingResult) {
       if (bindingResult.hasErrors()){
           return "redirect:/";
       }else{
           Authentication auth = SecurityContextHolder.getContext().getAuthentication();
           String name = auth.getName();
           User user = userService.findByEmail(name);
           shopping.setUser(user);
           shopping.setProduct(product);
           productService.save(product);
           storageService.store(file);
           shoppingService.save(shopping);
           return "redirect:/";
       }

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerValidator(@Valid User user, BindingResult bindingResult, Model model,
                                    RedirectAttributes redirectAttributes) {
        User user1 = userService.findByEmail(user.getEmail());
        if(user1 != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "Hay un usuario registrado ya con ese email!");
        }

        if(bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("message", new Message("alert alert-danger",
                    "Error",
                    "Comprueba el correo y los datos introducidos"));
            return "auth/register";
        } else {
            userService.save(user);
            model.addAttribute("success", "El usuario se ha creado correctamente");
            return "auth/register";
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
