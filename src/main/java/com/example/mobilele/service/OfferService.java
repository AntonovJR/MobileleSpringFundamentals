package com.example.mobilele.service;


import com.example.mobilele.models.view.OfferSummaryView;

import java.util.List;

public interface OfferService {
    List<OfferSummaryView> getAllOffers();

    OfferSummaryView getOfferById(Long id);
}
