package com.saveme.ledger.service.process;

import com.saveme.consumption.domain.Inventory;
import com.saveme.consumption.repository.InventoryRepository;
import com.saveme.consumption.service.InventoryCommandService;
import com.saveme.global.error.ErrorCode;
import com.saveme.global.error.exception.BusinessException;
import com.saveme.ledger.domain.Category;
import com.saveme.ledger.domain.Expense;
import com.saveme.ledger.dto.request.ExpenseRequestDto;
import com.saveme.ledger.repository.CategoryRepository;
import com.saveme.ledger.repository.ExpenseRepository;
import com.saveme.member.domain.Member;
import com.saveme.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ExpenseProcessService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final InventoryCommandService inventoryCommandService;

    public Long registerExpense(Long memberId, ExpenseRequestDto request) {

        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        Category category = categoryRepository.findById(request.getCategoryId())
            .orElseGet(() -> categoryRepository.findByNameAndParentIsNull("기타")
                .orElseThrow(() -> new BusinessException(ErrorCode.DEFAULT_CATEGORY_ERROR)));

        Expense expense = Expense.builder()
            .member(member)
            .category(category)
            .amount(request.getAmount())
            .spentAt(request.getSpentAt())
            .memo(request.getMemo())
            .isFixed(false)
            .build();

        expenseRepository.save(expense);

        if (category.isGroceryContext()) {
            request.setExpiryDate();
            inventoryCommandService.createInventory(member, request.getIngredientName(),request.getExpiryDate(),expense);
        }

        return expense.getId();
    }

    public void updateExpense(Long expenseId, ExpenseRequestDto request) {
        Expense expense = expenseRepository.findById(expenseId)
            .orElseThrow(() -> new BusinessException(ErrorCode.EXPENSE_NOT_FOUND));

        Category category = categoryRepository.findById(request.getCategoryId())
            .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));

        expense.modifyExpense(request.getAmount(), request.getMemo(), category);
    }

    public void deleteExpense(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
            .orElseThrow(() -> new BusinessException(ErrorCode.EXPENSE_NOT_FOUND));
        expenseRepository.delete(expense);
    }
}