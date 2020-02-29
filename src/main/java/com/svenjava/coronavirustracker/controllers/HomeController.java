package com.svenjava.coronavirustracker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.svenjava.coronavirustracker.models.LocationStats;
import com.svenjava.coronavirustracker.services.CoronaVirusDataService;

@Controller
public class HomeController {
	@Autowired
	CoronaVirusDataService coronaVirus;
	
	@GetMapping("/")
	public String home(Model model) {
		List<LocationStats> allStats = coronaVirus.getAllStats();
		int totalReportedCases = allStats.stream()
			.mapToInt(stat -> stat.getLatestTotalCases()).sum();
		int totalChangesSinceYesterday = allStats.stream()
				.mapToInt(stat -> stat.getDiffFromPreviousDay()).sum();
		model.addAttribute("locationStats", allStats);
		model.addAttribute("totalReportedCases", totalReportedCases);
		model.addAttribute("totalChangesSinceYesterday", totalChangesSinceYesterday);
		
		return "home";
	}
}
