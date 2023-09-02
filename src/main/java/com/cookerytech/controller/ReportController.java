package com.cookerytech.controller;

import com.cookerytech.dto.response.DashboardResponse;
import com.cookerytech.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')  or hasRole('PRODUCT_MANAGER') or hasRole('SALES_SPECIALIST') or hasRole('SALES_MANAGER')")
    public ResponseEntity<DashboardResponse>  getIstatistics(){
      DashboardResponse dashboardResponse=  reportService.getIstatistics();
      return ResponseEntity.ok(dashboardResponse);
    }




}
