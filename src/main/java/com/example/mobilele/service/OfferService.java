package com.example.mobilele.service;


import com.example.mobilele.models.view.OfferDetailView;
import com.example.mobilele.models.view.OfferSummaryView;

import java.util.List;

public interface OfferService {
    List<OfferSummaryView> getAllOffers();

    OfferDetailView getOfferById(Long id);
}
