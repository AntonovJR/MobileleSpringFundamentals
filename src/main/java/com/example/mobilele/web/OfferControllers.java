package com.example.mobilele.web;

import com.example.mobilele.service.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OfferControllers {
    private final OfferService offerService;

    public OfferControllers(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("offers/all")
    public String allOffers(Model model){
        model.addAttribute("offers", offerService.getAllOffers());
        return "offers";
    }

    @GetMapping("offers/add")
    public String addOffer(){
        return "offer-add";
    }

    @GetMapping("/offers/{id}/details")
    public String showOffer(@PathVariable Long id, Model model){
        model.addAttribute("offerDetail", offerService.getOfferById(id));
        return "details";
    }
}
