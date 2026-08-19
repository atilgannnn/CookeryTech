package com.cookerytech.service;

import com.cookerytech.domain.Currency;
import com.cookerytech.dto.CurrencyDTO;
import com.cookerytech.exception.ResourceNotFoundException;
import com.cookerytech.exception.message.ErrorMessage;
import com.cookerytech.mapper.CurrencyMapper;
import com.cookerytech.repository.CurrencyRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CurrencyService {

    private static final Logger log = LoggerFactory.getLogger(CurrencyService.class);

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;

    public CurrencyService(CurrencyRepository currencyRepository, CurrencyMapper currencyMapper) {
        this.currencyRepository = currencyRepository;
        this.currencyMapper = currencyMapper;
    }

    @Scheduled(fixedRate = 60000)
    public void updateCurrencies() {
        try {
            fetchAndUpdateExchangeRates();
        } catch (Exception e) {
            String errorMessage = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
            log.warn("Döviz kurları güncellenemedi: {}", errorMessage);
        }
    }

    private void fetchAndUpdateExchangeRates() throws Exception {
        Document doc = Jsoup.connect("https://www.tcmb.gov.tr/kurlar/today.xml")
                .timeout(5000)
                .get();

        Elements currencies = doc.select("Currency");

        Currency currencyUSD = getCurrency("USD");
        Currency currencyTRY = getCurrency("TRY");
        Currency currencyEUR = getCurrency("EUR");

        Double usdPrice = null;

        for (Element element : currencies) {
            String kod = element.attr("Kod");

            if ("USD".equals(kod)) {
                String forexBuying = element.select("ForexBuying").text();
                if (!forexBuying.isEmpty()) {
                    usdPrice = Double.valueOf(forexBuying);
                    currencyUSD.setCode("USD");
                    currencyUSD.setSymbol("$");
                    currencyUSD.setUpdateAt(LocalDateTime.now());
                    currencyUSD.setValue(1.0);

                    currencyTRY.setCode("TRY");
                    currencyTRY.setSymbol("₺");
                    currencyTRY.setUpdateAt(LocalDateTime.now());
                    currencyTRY.setValue(usdPrice);

                    currencyRepository.save(currencyUSD);
                    currencyRepository.save(currencyTRY);
                }
            } else if ("EUR".equals(kod)) {
                String forexSelling = element.select("ForexSelling").text();
                if (!forexSelling.isEmpty()) {
                    Double eurPrice = Double.valueOf(forexSelling);
                    currencyEUR.setCode("EUR");
                    currencyEUR.setSymbol("€");
                    currencyEUR.setUpdateAt(LocalDateTime.now());

                    if (usdPrice != null && usdPrice > 0) {
                        currencyEUR.setValue(usdPrice / eurPrice);
                    } else {
                        currencyEUR.setValue(1.0);
                    }
                    currencyRepository.save(currencyEUR);
                }
            }
        }
    }

    public Page<CurrencyDTO> getCurrenciesPage(Pageable pageable) {
        Pageable safePageable = (pageable != null) ? pageable : PageRequest.of(0, 10);

        try {
            Page<Currency> currencyPage = currencyRepository.findAll(safePageable);
            return currencyMapper.currencyPageToCurrencyDTOPage(currencyPage);
        } catch (Exception e) {
            String errorMessage = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
            log.error("Döviz verileri getirilirken hata oluştu: {}", errorMessage);
            return Page.empty();
        }
    }

    public Page<CurrencyDTO> getCurrenciesPages(Pageable pageable) {
        return getCurrenciesPage(pageable);
    }

    public Currency getCurrency(String code) {
        return currencyRepository.findByCode(code).orElseGet(Currency::new);
    }

    public Currency getCurrencyById(Long id) {
        return currencyRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION, id))
        );
    }
}