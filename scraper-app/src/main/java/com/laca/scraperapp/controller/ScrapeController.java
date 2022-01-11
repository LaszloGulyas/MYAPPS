package com.laca.scraperapp.controller;

import com.laca.scraperapp.service.InstagramScrapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScrapeController {

    @Autowired
    private InstagramScrapeService instagramScrapeService;

    @GetMapping("/instagram/{profileId}")
    public ResponseEntity scrapeInstagram(@PathVariable String profileId) {
        int nrOfImages = instagramScrapeService.scrape(profileId);
        return new ResponseEntity(
                "Scraping was successful!\n"
                        + "Number of downloaded images: "
                        + nrOfImages, HttpStatus.OK);
    }
}
