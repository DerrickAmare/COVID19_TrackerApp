package in.tube.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import in.tube.service.CoronaService;

@Controller
public class MainController {

	@Autowired
	private CoronaService service;

	@GetMapping("/")
	public String renderDetails(Model model) {
		LocalDate ldt=LocalDate.now();
		
		
		Integer totalReportedCases=service.getModel().stream().mapToInt(stat->stat.getLatestCount()).sum();
		model.addAttribute("result",service.getModel());
		model.addAttribute("totalReportedCases",totalReportedCases);
		model.addAttribute("date",ldt);
				
		return "home";
	}
	

}
