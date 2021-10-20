package com.example.mobilele.service.impl;

import com.example.mobilele.models.entity.Offer;
import com.example.mobilele.models.view.OfferDetailView;
import com.example.mobilele.repository.OfferRepository;
import com.example.mobilele.service.OfferService;
import com.example.mobilele.models.view.OfferSummaryView;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;

    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<OfferSummaryView> getAllOffers() {

        return offerRepository.findAll().stream().map(this::map).collect(Collectors.toList());

    }

    @Override
    public OfferDetailView getOfferById(Long id) {
        Offer byId = offerRepository.getById(id);
        return mapDetail(byId);
    }

    private OfferDetailView mapDetail(Offer offer) {
        OfferDetailView offerDetailView = this.modelMapper.map(offer,OfferDetailView.class);

        offerDetailView.setId(offer.getId());
        offerDetailView.setModel(offer.getModel().getName());
        offerDetailView.setBrand(offer.getModel().getBrand().getName());
        offerDetailView.setEngine(offer.getEngine().toString());
        offerDetailView.setTransmission(offer.getTransmission().toString());
        offerDetailView.setSeller(offer.getSeller().getFirstName() + " " + offer.getSeller().getLastName());


        return offerDetailView;
    }

    public OfferSummaryView map(Offer offer) {
        OfferSummaryView summaryView = this.modelMapper.map(offer, OfferSummaryView.class);

        summaryView.setModel(offer.getModel().getName());
        summaryView.setBrand(offer.getModel().getBrand().getName());
        summaryView.setEngine(offer.getEngine().toString());
        summaryView.setTransmission(offer.getTransmission().toString());

        return summaryView;
    }
}
