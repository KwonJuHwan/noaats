package com.saveme.ledger.controller;


import com.saveme.ledger.dto.request.FixedCostRequestDto;
import com.saveme.ledger.service.command.FixedCostCommandService;
import com.saveme.ledger.service.query.CategoryQueryService;
import com.saveme.ledger.service.query.FixedCostQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fixed-costs")
@RequiredArgsConstructor
public class FixedCostController {

    private final FixedCostCommandService fixedCostCommandService;
    private final FixedCostQueryService fixedCostQueryService;
    private final CategoryQueryService categoryQueryService;

    private static final Long MEMBER_ID = 1L;

    @GetMapping
    public String fixedCostList(Model model) {
        model.addAttribute("fixedCosts", fixedCostQueryService.getFixedCosts(MEMBER_ID));

        model.addAttribute("rootCategories", categoryQueryService.getRootCategories());
        return "ledger/fixed-costs";
    }

    @PostMapping("/register")
    public String registerFixedCost(@ModelAttribute FixedCostRequestDto request) {
        fixedCostCommandService.registerFixedCost(MEMBER_ID, request);
        return "redirect:/fixed-costs";
    }

    @PostMapping("/update/{id}")
    public String updateFixedCost(@PathVariable Long id, @ModelAttribute FixedCostRequestDto request) {
        fixedCostCommandService.updateFixedCost(id, request);
        return "redirect:/fixed-costs";
    }

    @PostMapping("/delete/{id}")
    public String deleteFixedCost(@PathVariable Long id) {
        fixedCostCommandService.deleteFixedCost(id);
        return "redirect:/fixed-costs";
    }
}
