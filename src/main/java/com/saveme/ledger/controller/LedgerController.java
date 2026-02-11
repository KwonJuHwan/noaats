package com.saveme.ledger.controller;

import com.saveme.ledger.dto.request.ExpenseRequestDto;
import com.saveme.ledger.dto.request.FixedCostRequestDto;
import com.saveme.ledger.dto.response.ExpenseDetailResponseDto;
import com.saveme.ledger.repository.CategoryRepository;
import com.saveme.ledger.service.command.FixedCostCommandService;
import com.saveme.ledger.service.process.ExpenseProcessService;
import com.saveme.ledger.service.query.ExpenseQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class LedgerController {

    private final ExpenseQueryService expenseQueryService;
    private final ExpenseProcessService expenseProcessService;
    private final FixedCostCommandService fixedCostCommandService;
    private final CategoryRepository categoryRepository;

    private static final Long MEMBER_ID = 1L; // 단일 사용자 노아

    /**
     * [메인] 캘린더 대시보드
     */
    @GetMapping
    public String mainDashboard(@RequestParam(required = false) String yearMonth, Model model) {
        String targetMonth = (yearMonth == null) ? YearMonth.now().toString() : yearMonth;

        int daysInMonth = YearMonth.parse(targetMonth).lengthOfMonth();

        model.addAttribute("currentMonth", targetMonth);
        model.addAttribute("prevMonth", YearMonth.parse(targetMonth).minusMonths(1).toString());
        model.addAttribute("nextMonth", YearMonth.parse(targetMonth).plusMonths(1).toString());

        model.addAttribute("daysInMonth", daysInMonth);

        // 일별 요약 맵 (날짜별 총합 등)
        model.addAttribute("expenseMap", expenseQueryService.getMonthlyExpenseMap(MEMBER_ID, targetMonth));
        model.addAttribute("categories", categoryRepository.findAll());

        return "ledger/main";
    }

    /**
     * [1. 고정 지출] 관리 화면
     */
    @GetMapping("/fixed-costs")
    public String fixedCostList(Model model) {
        model.addAttribute("fixedCosts", fixedCostCommandService.getFixedCosts(MEMBER_ID));
        model.addAttribute("categories", categoryRepository.findAll());
        return "ledger/fixed-costs";
    }

    /**
     * [2. 냉장고] 화면 (Placeholder)
     */
    @GetMapping("/fridge")
    public String fridge() {
        return "ledger/fridge";
    }

    /**
     * [3. 통계] 화면
     */
    @GetMapping("/statistics")
    public String statistics() {
        return "ledger/statistics";
    }

    // --- 지출(Expense) CRUD ---

    @PostMapping("/expenses/register")
    public String registerExpense(@ModelAttribute ExpenseRequestDto request) {
        expenseProcessService.registerExpense(MEMBER_ID, request);
        return "redirect:/";
    }

    @PostMapping("/expenses/update/{id}") // 지출 수정 추가
    public String updateExpense(@PathVariable Long id, @ModelAttribute ExpenseRequestDto request) {
        System.out.println("수정할 지출 ID: " + id);
        expenseProcessService.updateExpense(id, request);
        return "redirect:/";
    }

    @PostMapping("/expenses/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseProcessService.deleteExpense(id);
        return "redirect:/";
    }
    /**
     * [AJAX] 캘린더 일별 상세보기 데이터 조회
     */
    @GetMapping("/api/expenses/daily")
    @ResponseBody
    public List<ExpenseDetailResponseDto> getDailyDetail(@RequestParam String date) {
        return expenseQueryService.getDailyExpenses(MEMBER_ID, LocalDate.parse(date));
    }

    // --- 고정 지출(FixedCost) CRUD ---

    @PostMapping("/fixed-costs/register") // 고정 지출 등록 추가
    public String registerFixedCost(@ModelAttribute FixedCostRequestDto request) {
        fixedCostCommandService.registerFixedCost(MEMBER_ID, request);
        return "redirect:/fixed-costs";
    }

    @PostMapping("/fixed-costs/update/{id}") // 고정 지출 수정 추가
    public String updateFixedCost(@PathVariable Long id, @ModelAttribute FixedCostRequestDto request) {
        fixedCostCommandService.updateFixedCost(id, request);
        return "redirect:/fixed-costs";
    }

    @PostMapping("/fixed-costs/delete/{id}") // 고정 지출 삭제 추가
    public String deleteFixedCost(@PathVariable Long id) {
        fixedCostCommandService.deleteFixedCost(id);
        return "redirect:/fixed-costs";
    }
}