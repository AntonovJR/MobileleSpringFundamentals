package com.example.mobilele.web;

import com.example.mobilele.service.BrandService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BrandsControllers {

    private final BrandService brandService;

    public BrandsControllers(BrandService brandService) {
        this.brandService = brandService;
    }


    @GetMapping("brands/all")
    public String allBrands(Model model){
        model.addAttribute("brands", brandService.getAllBrands());
        return "brands";
    }
}
